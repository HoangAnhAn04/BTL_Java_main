package com.rbai.btl.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    private String title;
    private String author;
    private String genre;
    private Integer rating;
    private String coverUrl;
    private String coverColor;
    private String description;
    private Integer totalCopies;
    private Integer availableCopies;
    private String videoUrl;
    private String summary;
}
