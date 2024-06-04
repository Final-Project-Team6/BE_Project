package com.fastcampus.aptner.post.information.repository;

import com.fastcampus.aptner.post.common.enumType.OrderBy;
import com.fastcampus.aptner.post.common.enumType.OrderType;
import com.fastcampus.aptner.post.common.enumType.PostStatus;
import com.fastcampus.aptner.post.common.enumType.SearchType;
import com.fastcampus.aptner.post.information.domain.Information;
import com.fastcampus.aptner.post.information.domain.InformationType;
import com.fastcampus.aptner.post.information.dto.InformationDTO;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.fastcampus.aptner.post.information.domain.QInformation.information;
import static com.fastcampus.aptner.post.information.domain.QInformationCategory.informationCategory;

public class InformationRepositoryDslImpl extends QuerydslRepositorySupport implements InformationRepositoryDsl {
    @Autowired
    private JPAQueryFactory queryFactory;
    public InformationRepositoryDslImpl(){super(Information.class);}


    @Override
    public Page<Information> searchInformation(InformationDTO.InformationSearchReqDTO reqDTO) {
        JPAQuery<Information> query = queryFactory.selectFrom(information)
                .leftJoin(information.informationCategoryId,informationCategory)
                .groupBy(information.informationId)
                .where(chooseApartment(reqDTO.getApartmentId()),
                        searchByKeyword(reqDTO.getKeyword(),reqDTO.getSearchType()),
                        targetType(reqDTO.getInformationType()),
                        targetCategory(reqDTO.getCategoryId()),
                        isPublished()
                        )
                .orderBy(sort(reqDTO.getOrderType(),reqDTO.getOrderBy()));
        List<Information> informationList = this.getQuerydsl().applyPagination(reqDTO.getPageable(),query).fetch();
        return new PageImpl<>(informationList,reqDTO.getPageable(),query.fetchCount());
    }

    private BooleanExpression chooseApartment(Long apartmentId){
        return informationCategory.apartmentId.apartmentId.eq(apartmentId);
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
        return information.contents.contains(keyword);
    }
    private BooleanExpression titleContainsKeyword(String keyword){
        return information.title.contains(keyword);
    }

    private OrderSpecifier<?> sort(OrderType orderType, OrderBy orderBy){
        if (orderType == null){
            orderType = OrderType.DATE;
        }
        switch (orderType){
            case DATE ->{
                if (orderBy==OrderBy.ASC){
                    return new OrderSpecifier<>(Order.ASC, information.createdAt);
                }else {
                    return new OrderSpecifier<>(Order.DESC, information.createdAt);
                }
            }
            case VIEW -> {
                if (orderBy==OrderBy.ASC){
                    return new OrderSpecifier<>(Order.ASC, information.view);
                }else {
                    return new OrderSpecifier<>(Order.DESC, information.view);
                }
            }
        }
        return null;
    }

    private BooleanExpression targetCategory(Long categoryId){
        if (categoryId==null){
            return null;
        }
        return information.informationCategoryId.informationCategoryId.eq(categoryId);
    }
    private BooleanExpression targetType(InformationType type){
        if (type == null){
            return null;
        }
        return information.informationCategoryId.type.eq(type);
    }

    private BooleanExpression isPublished() {return information.status.eq(PostStatus.PUBLISHED);}
}
