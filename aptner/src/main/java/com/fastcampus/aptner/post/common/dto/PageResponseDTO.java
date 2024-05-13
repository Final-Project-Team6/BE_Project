package com.fastcampus.aptner.post.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageResponseDTO {
    private List<?> content;
    private int totalPages;
    private long totalElements;
    private int pageNumber;
    private int size;

    public PageResponseDTO(Page<?> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.pageNumber = page.getNumber() + 1;
        this.size = page.getSize();
    }
}
