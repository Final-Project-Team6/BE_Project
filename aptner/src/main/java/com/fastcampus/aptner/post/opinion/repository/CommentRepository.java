package com.fastcampus.aptner.post.opinion.repository;

import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>,CommentRepositoryDsl{

}
