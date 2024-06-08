package com.fastcampus.aptner.post.opinion.repository;

import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.global.handler.exception.CustomAPIException;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
import com.fastcampus.aptner.post.opinion.domain.QComment;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Arrays;
import java.util.List;

import static com.fastcampus.aptner.global.error.CommonErrorCode.INVALID_PARAMETER;
import static com.fastcampus.aptner.post.announcement.domain.QAnnouncement.announcement;
import static com.fastcampus.aptner.post.communication.domain.QCommunication.communication;
import static com.fastcampus.aptner.post.complaint.domain.QComplaint.complaint;
import static com.fastcampus.aptner.post.opinion.domain.CommentType.REPLY;
import static com.fastcampus.aptner.post.opinion.domain.QComment.comment;

public class CommentRepositoryDslImpl extends QuerydslRepositorySupport implements CommentRepositoryDsl {

    @Autowired
    private JPAQueryFactory queryFactory;

    public CommentRepositoryDslImpl() {
        super(Comment.class);
    }

    @Override
    public List<Comment> getComments(Long postId, CommentType commentType) {
        JPAQuery<Comment> query = queryFactory.selectFrom(comment)
                .leftJoin(comment.announcementId, announcement)
                .leftJoin(comment.communicationId, communication)
                .leftJoin(comment.complaintId, complaint)
                .groupBy(comment.commentId)
                .where(setWithoutParent(), setTargetBoard(postId, commentType))
                .orderBy(sortByDay());
        return query.fetch();
    }

    @Override
    public Page<Comment> getMyComments(Long memberId, Pageable pageable,CommentType commentType) {

        try {
            QComment qParentComment = new QComment("parentComment");
            JPAQuery<Comment> query = queryFactory.selectFrom(comment)
                    .groupBy(comment.commentId)
                    .leftJoin(comment.parentId, qParentComment)
                    .where(myComment(memberId),setTargetBoardForMyComment(commentType))
                    .orderBy(sortByDay());

            List<Comment> informationList = this.getQuerydsl().applyPagination(pageable, query).fetch();
            return new PageImpl<>(informationList, pageable, query.fetchCount());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private BooleanExpression setWithoutParent() {
        return comment.parentId.isNull();
    }

    private BooleanExpression setTargetBoard(Long postId, CommentType commentType) {
        if (commentType == null || commentType == REPLY) {
            throw new RestAPIException(INVALID_PARAMETER);
        }
        try {
            switch (commentType) {
                case ANNOUNCEMENT -> {
                    return comment.announcementId.announcementId.eq(postId);
                }
                case COMPLAINT -> {
                    return comment.complaintId.complaintId.eq(postId);
                }
                case COMMUNICATION -> {
                    return comment.communicationId.communicationId.eq(postId);
                }
            }
        } catch (Exception e) {
            throw new RestAPIException(INVALID_PARAMETER);
        }
        return null;
    }

    private BooleanExpression setTargetBoardForMyComment(CommentType commentType){
        if (commentType == REPLY){
            throw new RestAPIException(INVALID_PARAMETER);
        }
        if (commentType == null){
            return  null;
        }
        try {
            switch (commentType){
                case ANNOUNCEMENT -> {
                    return comment.announcementId.isNotNull().or(getBoardChildren(commentType));
                }
                case COMPLAINT -> {
                    return comment.complaintId.isNotNull().or(getBoardChildren(commentType));
                }
                case COMMUNICATION -> {
                    return comment.communicationId.isNotNull().or(getBoardChildren(commentType));
                }
            }
        }catch (Exception e) {
            throw new CustomAPIException(e.getMessage());
        }
        return null;
    }

    // todo 무한 댓글인 경우 에 대해 해결필요.
//    private BooleanExpression getBoardChildren(CommentType commentType){
//        QComment qComment = comment;
//        switch (commentType){
//            case ANNOUNCEMENT -> {
//                while (qComment.parentId!=null){
//                    qComment = qComment.parentId;
//                }
//                return qComment.announcementId.isNotNull();
//            }
//            case COMPLAINT -> {
//                while (qComment.parentId!=null){
//                    qComment = qComment.parentId;
//                }
//                return qComment.complaintId.isNotNull();
//            }
//            case COMMUNICATION -> {
//                while (qComment.parentId!=null){
//                    qComment = qComment.parentId;
//                }
//                return qComment.communicationId.isNotNull();
//            }
//        }
//        return null;
//    }

    private BooleanExpression getBoardChildren(CommentType commentType){
        switch (commentType){
            case ANNOUNCEMENT -> {
                return comment.parentId.announcementId.isNotNull();
            }
            case COMPLAINT -> {
                return comment.parentId.complaintId.isNotNull();
            }
            case COMMUNICATION -> {

                return comment.parentId.communicationId.isNotNull();
            }
        }
        return null;
    }
    private OrderSpecifier<?> sortByDay() {
        return new OrderSpecifier<>(Order.ASC, comment.createdAt);
    }

    private BooleanExpression myComment(Long memberId) {
        return comment.memberId.memberId.eq(memberId);
    }
}
