package com.fastcampus.aptner.post.announcement.repository;

import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.announcement.domain.AnnouncementType;
import com.fastcampus.aptner.post.announcement.dto.AnnouncementDTO;
import com.fastcampus.aptner.post.common.enumType.OrderBy;
import com.fastcampus.aptner.post.common.enumType.OrderType;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.common.enumType.SearchType;
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

import static com.fastcampus.aptner.post.announcement.domain.QAnnouncement.announcement;
import static com.fastcampus.aptner.post.announcement.domain.QAnnouncementCategory.announcementCategory;
import static com.fastcampus.aptner.post.opinion.domain.QComment.comment;
import static com.fastcampus.aptner.post.opinion.domain.QVote.vote;

public class AnnouncementRepositoryDslImpl extends QuerydslRepositorySupport implements AnnouncementRepositoryDsl {
    @Autowired
    private JPAQueryFactory queryFactory;

    public AnnouncementRepositoryDslImpl() {
        super(Announcement.class);
    }

    @Override
    public Page<Announcement> searchAnnouncement(AnnouncementDTO.AnnouncementSearchReqDTO reqDTO) {
        JPAQuery<Announcement> query = queryFactory.selectFrom(announcement)
                .leftJoin(announcement.announcementCategoryId, announcementCategory)
                .leftJoin(announcement.commentList, comment)
                .leftJoin(announcement.voteList, vote)
                .groupBy(announcement.announcementId)
                .where(chooseApartment(reqDTO.getApartmentId()),
                        searchByKeyword(reqDTO.getKeyword(), reqDTO.getSearchType()),
                        targetType(reqDTO.getAnnouncementType()),
                        targetCategory(reqDTO.getCategoryId()),
                        importantAnnouncement(reqDTO.getImportant()),
                        isPublished()
                )
                .orderBy(sort(reqDTO));
        List<Announcement> announcementList = this.getQuerydsl().applyPagination(reqDTO.getPageable(), query).fetch();
        return new PageImpl<>(announcementList, reqDTO.getPageable(), query.fetchCount());
    }

    private BooleanExpression chooseApartment(Long apartmentId) {
        return announcementCategory.apartmentId.apartmentId.eq(apartmentId);
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

    private OrderSpecifier<?> sort(AnnouncementDTO.AnnouncementSearchReqDTO reqDTO) {
        if (reqDTO.getImportant() != null) {
            if (reqDTO.getImportant()) {
                return new OrderSpecifier<>(Order.DESC, announcement.important);
            }
        }

        OrderType orderType = reqDTO.getOrderType();
        OrderBy orderBy = reqDTO.getOrderBy();
        if (orderType == null) {
            orderType = OrderType.DATE;
        }
        switch (orderType) {
            case DATE -> {
                if (orderBy == OrderBy.ASC) {
                    return new OrderSpecifier<>(Order.ASC, announcement.createdAt);
                } else {
                    return new OrderSpecifier<>(Order.DESC, announcement.createdAt);
                }
            }
            case VIEW -> {
                if (orderBy == OrderBy.ASC) {
                    return new OrderSpecifier<>(Order.ASC, announcement.view);
                } else {
                    return new OrderSpecifier<>(Order.DESC, announcement.view);
                }
            }
            case VOTE -> {
                JPQLQuery query = JPAExpressions
                        .select(vote.count())
                        .from(vote)
                        .where(vote.announcementId.eq(announcement).and(vote.opinion.eq(true)));
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
                        .where(comment.announcementId.eq(announcement));
                if (orderBy == OrderBy.ASC) {
                    return new OrderSpecifier<>(Order.ASC, query);
                } else {
                    return new OrderSpecifier<>(Order.DESC, query);
                }
            }
        }
        return null;
    }


    private BooleanExpression contentsContainsKeyword(String keyword) {
        return announcement.contents.contains(keyword);
    }

    private BooleanExpression titleContainsKeyword(String keyword) {
        return announcement.title.contains(keyword);
    }

    private BooleanExpression targetCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return announcement.announcementCategoryId.announcementCategoryId.eq(categoryId);
    }

    private BooleanExpression targetType(AnnouncementType announcementType) {
        if (announcementType == null) {
            return null;
        }
        return announcement.announcementCategoryId.type.eq(announcementType);
    }

    private BooleanExpression importantAnnouncement(Boolean important) {
        if (important == null) {
            return null;
        }
        if (important) {
            return announcement.important.gt(1);
        } else {
            return announcement.important.eq(0);
        }
    }

    private BooleanExpression isPublished() {
        return announcement.status.eq(PostStatus.PUBLISHED);
    }


}
