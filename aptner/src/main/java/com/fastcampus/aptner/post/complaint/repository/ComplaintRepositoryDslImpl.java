package com.fastcampus.aptner.post.complaint.repository;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.enumType.OrderBy;
import com.fastcampus.aptner.post.common.enumType.OrderType;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.common.enumType.SearchType;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.domain.ComplaintType;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.fastcampus.aptner.member.domain.QMember.member;
import static com.fastcampus.aptner.post.complaint.domain.QComplaint.complaint;
import static com.fastcampus.aptner.post.complaint.domain.QComplaintCategory.complaintCategory;
import static com.fastcampus.aptner.post.opinion.domain.QComment.comment;
import static com.fastcampus.aptner.post.opinion.domain.QVote.vote;

public class ComplaintRepositoryDslImpl extends QuerydslRepositorySupport implements ComplaintRepositoryDsl {

    @Autowired
    private JPAQueryFactory queryFactory;

    public ComplaintRepositoryDslImpl() {
        super(Complaint.class);
    }


    @Override
    public Page<Complaint> searchComplaint(ComplaintDTO.ComplaintSearchReqDTO reqDTO, JWTMemberInfoDTO memberToken) {
        JPAQuery<Complaint> query = queryFactory.selectFrom(complaint)
                .leftJoin(complaint.complaintCategoryId, complaintCategory)
                .leftJoin(complaint.memberId, member)
                .leftJoin(complaint.commentList, comment)
                .leftJoin(complaint.voteList, vote)
                .groupBy(complaint.complaintId)
                .where(searchByKeyword(reqDTO.getKeyword(),
                                reqDTO.getSearchType()),
                        chooseApartment(reqDTO.getApartmentId()),
                        targetCategory(reqDTO.getCategoryId()),
                        targetType(reqDTO.getComplaintType()),
                        aboutSecretComplaint(memberToken),
                        onlyMyComplaint(memberToken, reqDTO.getMyComplaint()),
                        isPublished()
                )
                .orderBy(sort(reqDTO.getOrderType(), reqDTO.getOrderBy()));
        List<Complaint> complaintList = this.getQuerydsl().applyPagination(reqDTO.getPageable(), query).fetch();
        return new PageImpl<>(complaintList, reqDTO.getPageable(), query.fetchCount());
    }

    private BooleanExpression searchByKeyword(String keyword, SearchType searchType) {
        if (keyword == null) {
            return null;
        }
        switch (searchType) {
            case TITLE -> {
                return titleContainsKeyword(keyword);
            }
            case CONTENTS -> {
                return contentsContainsKeyword(keyword);
            }
            case TITLE_CONTENTS -> {
                return titleContainsKeyword(keyword).or(contentsContainsKeyword(keyword));
            }
        }
        return null;
    }

    private BooleanExpression chooseApartment(Long apartmentId) {
        return complaintCategory.apartmentId.apartmentId.eq(apartmentId);
    }

    private BooleanExpression contentsContainsKeyword(String keyword) {
        return complaint.contents.contains(keyword);
    }

    private BooleanExpression titleContainsKeyword(String keyword) {
        return complaint.title.contains(keyword);
    }

    private OrderSpecifier<?> sort(OrderType orderType, OrderBy orderBy) {
        if (orderType == null) {
            orderType = OrderType.DATE;
        }
        switch (orderType) {
            case DATE -> {
                if (orderBy == OrderBy.ASC) {
                    return new OrderSpecifier<>(Order.ASC, complaint.createdAt);
                } else {
                    return new OrderSpecifier<>(Order.DESC, complaint.createdAt);
                }
            }
            case VIEW -> {
                if (orderBy == OrderBy.ASC) {
                    return new OrderSpecifier<>(Order.ASC, complaint.view);
                } else {
                    return new OrderSpecifier<>(Order.DESC, complaint.view);
                }
            }
            case VOTE -> {
                JPQLQuery query = JPAExpressions
                        .select(vote.count())
                        .from(vote)
                        .where(vote.complaintId.eq(complaint).and(vote.opinion.eq(true)));
                if (orderBy == OrderBy.ASC) {
                    return new OrderSpecifier<>(Order.ASC, query);
                } else {
                    return new OrderSpecifier<>(Order.DESC, query);
                }
            }
            case COMMENT -> {
                JPQLQuery query = JPAExpressions
                        .select(comment.count())
                        .from(comment)
                        .where(comment.complaintId.eq(complaint));
                if (orderBy == OrderBy.ASC) {
                    return new OrderSpecifier<>(Order.ASC, query);
                } else {
                    return new OrderSpecifier<>(Order.DESC, query);
                }
            }
        }
        return null;
    }

    private BooleanExpression targetCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return complaint.complaintCategoryId.complaintCategoryId.eq(categoryId);
    }

    private BooleanExpression targetType(ComplaintType complaintType) {
        if (complaintType == null) {
            return null;
        }
        return complaint.complaintCategoryId.type.eq(complaintType);
    }

    private BooleanExpression notSecretComplaint() {
        return complaint.secret.eq(false);
    }

    private BooleanExpression aboutSecretComplaint(JWTMemberInfoDTO memberToken) {
        if (memberToken == null) {
            return notSecretComplaint();
        }
        return complaint.memberId.memberId.eq(memberToken.getMemberId()).or(notSecretComplaint());
    }

    private BooleanExpression onlyMyComplaint(JWTMemberInfoDTO memberToken, Boolean myComplaint) {
        if (memberToken == null) {
            return null;
        }
        if (myComplaint == null) {
            return null;
        }
        if (myComplaint) {
            return complaint.memberId.memberId.eq(memberToken.getMemberId());
        } else return null;
    }

    private BooleanExpression isPublished() {
        return complaint.status.eq(PostStatus.PUBLISHED);
    }
}
