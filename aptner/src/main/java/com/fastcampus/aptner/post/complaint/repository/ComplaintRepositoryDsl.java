package com.fastcampus.aptner.post.complaint.repository;

import com.fastcampus.aptner.post.complaint.domain.Complaint;
import com.fastcampus.aptner.post.complaint.dto.ComplaintDTO;
import com.fastcampus.aptner.post.temp.dto.MemberTempDTO;
import org.springframework.data.domain.Page;

public interface ComplaintRepositoryDsl {

    Page<Complaint> searchComplaint(ComplaintDTO.ComplaintSearchReqDTO reqDTO, MemberTempDTO.MemberAuthDTO memberToken);
}
