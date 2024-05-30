package com.fastcampus.aptner.post.communication.repository;

import com.fastcampus.aptner.jwt.util.JWTMemberInfoDTO;
import com.fastcampus.aptner.post.common.enumType.OrderBy;
import com.fastcampus.aptner.post.common.enumType.OrderType;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.common.enumType.SearchType;
import com.fastcampus.aptner.post.communication.domain.Communication;
import com.fastcampus.aptner.post.communication.domain.CommunicationType;
import com.fastcampus.aptner.post.communication.dto.CommunicationDTO;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.fastcampus.aptner.post.communication.domain.QCommunication.communication;
import static com.fastcampus.aptner.post.communication.domain.QCommunicationCategory.communicationCategory;
import static com.fastcampus.aptner.post.opinion.domain.QComment.comment;
import static com.fastcampus.aptner.post.opinion.domain.QVote.vote;

public class CommunicationRepositoryDslImpl extends QuerydslRepositorySupport implements CommunicationRepositoryDsl {
    @Autowired
    private JPAQueryFactory queryFactory;
    public CommunicationRepositoryDslImpl(){super(Communication.class);}

    @Override
    public Page<Communication> searchCommunication(CommunicationDTO.CommunicationSearchReqDTO reqDTO,JWTMemberInfoDTO memberToken) {
        JPAQuery<Communication> query = queryFactory.selectFrom(communication)
                .leftJoin(communication.communicationCategoryId,communicationCategory)
                .leftJoin(communication.commentList,comment)
                .leftJoin(communication.voteList,vote)
                .groupBy(communication.communicationId)
                .where(chooseApartment(reqDTO.getApartmentId()),
                        searchByKeyword(reqDTO.getKeyword(),reqDTO.getSearchType()),
                        targetCategory(reqDTO.getCategoryId()),
                        targetType(reqDTO.getCommunicationType()),
                        isPublished(),
                        aboutSecretCommunicaton(memberToken)
                        )
                .orderBy(sort(reqDTO.getOrderType(),reqDTO.getOrderBy()));
        List<Communication> communicationList = this.getQuerydsl().applyPagination(reqDTO.getPageable(),query).fetch();
        return new PageImpl<>(communicationList,reqDTO.getPageable(),query.fetchCount());
    }

    private BooleanExpression chooseApartment(Long apartmentId){
        return communicationCategory.apartmentId.apartmentId.eq(apartmentId);
    }


    private BooleanExpression searchByKeyword(String keyword, SearchType searchType){
        if (keyword == null){
            return Expressions.asBoolean(true).isTrue();
        }
        switch (searchType){
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
        return Expressions.asBoolean(true).isTrue();
    }

    private BooleanExpression contentsContainsKeyword(String keyword){
        return communication.contents.contains(keyword);
    }
    private BooleanExpression titleContainsKeyword(String keyword){
        return communication.title.contains(keyword);
    }


    private OrderSpecifier<?> sort(OrderType orderType, OrderBy orderBy){
        if (orderType == null){
            orderType = OrderType.DATE;
        }
        switch (orderType){
            case DATE ->{
                if (orderBy==OrderBy.ASC){
                    return new OrderSpecifier<>(Order.ASC, communication.createdAt);
                }else {
                    return new OrderSpecifier<>(Order.DESC, communication.createdAt);
                }
            }
            case VIEW -> {
                if (orderBy==OrderBy.ASC){
                    return new OrderSpecifier<>(Order.ASC, communication.view);
                }else {
                    return new OrderSpecifier<>(Order.DESC, communication.view);
                }
            }
            case VOTE -> {
                JPQLQuery query = JPAExpressions
                        .select(vote.count())
                        .from(vote)
                        .where(vote.communicationId.eq(communication).and(vote.opinion.eq(true)));
                if (orderBy==OrderBy.ASC){
                    return new OrderSpecifier<>(Order.ASC, query);
                }else {
                    return new OrderSpecifier<>(Order.DESC, query);
                }
            }
            case COMMENT -> {
                JPQLQuery query = JPAExpressions
                        .select(comment.count())
                        .from(comment)
                        .where(comment.communicationId.eq(communication));
                if (orderBy==OrderBy.ASC){
                    return new OrderSpecifier<>(Order.ASC,query);
                }else {
                    return new OrderSpecifier<>(Order.DESC,query);
                }
            }
        }
        return null;
    }

    private BooleanExpression targetCategory(Long categoryId){
        if (categoryId==null){
            return null;
        }
        return communication.communicationCategoryId.communicationCategoryId.eq(categoryId);
    }

    private BooleanExpression targetType(CommunicationType type){
        if (type == null){
            return null;
        }
        return communication.communicationCategoryId.type.eq(type);
    }

    private BooleanExpression isPublished(){
        return communication.status.eq(PostStatus.PUBLISHED);
    }

    private BooleanExpression notSecretCommunicaton() {
        return communication.secret.eq(false);
    }
    private BooleanExpression aboutSecretCommunicaton(JWTMemberInfoDTO memberToken) {
        if (memberToken == null) {
            return notSecretCommunicaton();
        }
        return communication.memberId.memberId.eq(memberToken.getMemberId()).or(notSecretCommunicaton());
    }
}
