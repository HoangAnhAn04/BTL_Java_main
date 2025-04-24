// package com.rbai.btl.dto.response;

// import lombok.*;

// @Getter
// @Setter
// @Builder
// @AllArgsConstructor
// @NoArgsConstructor
// public class BookResponse {
//     private String id;
//     private String title;
//     private String author;
//     private String publisher;
//     private String category;
//     private int year;
//     private int quantity;
//     private String isbn;
//     private String description;
// }

package com.rbai.btl.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Long id;
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
