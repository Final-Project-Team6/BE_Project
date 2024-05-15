package com.fastcampus.aptner.post.complaint.repository;

import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static com.fastcampus.aptner.post.complaint.domain.QComplaint.complaint;
import static com.fastcampus.aptner.post.complaint.domain.QComplaintCategory.complaintCategory;

public class ComplaintRepositoryDslImpl extends QuerydslRepositorySupport implements ComplaintRepositoryDsl{

    @Autowired
    private JPAQueryFactory queryFactory;

    public ComplaintRepositoryDslImpl(){super(Complaint.class);}

}
