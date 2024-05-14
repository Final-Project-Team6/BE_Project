package com.fastcampus.aptner.post.opinion.repository;

import com.fastcampus.aptner.member.domain.Member;
import com.fastcampus.aptner.post.announcement.domain.Announcement;
import com.fastcampus.aptner.post.communication.domain.Communication;
import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.opinion.domain.Comment;
import com.fastcampus.aptner.post.opinion.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findByAnnouncementIdAndMemberId(Announcement announcement, Member member);
    Optional<Vote> findByComplaintIdAndMemberId(Complaint complaint,Member member);
    Optional<Vote> findByCommunicationIdAndMemberId(Communication communication, Member member);
    Optional<Vote> findByCommentIdAndMemberId(Comment comment,Member member);


    Boolean existsByAnnouncementIdAndMemberId(Announcement announcement,Member member);
    Boolean existsByComplaintIdAndMemberId(Complaint complaint, Member member);
    Boolean existsByCommunicationIdAndMemberId(Communication communication, Member member);
    Boolean existsByCommentIdAndMemberId(Comment comment,Member member);
}
