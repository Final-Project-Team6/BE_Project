package com.fastcampus.aptner.post.opinion.repository;

import com.fastcampus.aptner.global.error.RestAPIException;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.CommentType;
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
    public Page<Comment> getMyComments(Long memberId, Pageable pageable) {
        JPAQuery<Comment> query = queryFactory.selectFrom(comment)
                .groupBy(comment.commentId)
                .where(myComment(memberId))
                .orderBy(sortByDay());
        List<Comment> informationList = this.getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<>(informationList, pageable, query.fetchCount());
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
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new RestAPIException(INVALID_PARAMETER);
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
