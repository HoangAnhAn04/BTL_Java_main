package com.rbai.btl.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "author", nullable = false, length = 255)
    private String author;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "cover_url", nullable = false)
    private String coverUrl;

    @Column(name = "cover_color", nullable = false, length = 7)
    private String coverColor;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;

    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;

    @Column(name = "video_url", nullable = false)
    private String videoUrl;

    @Column(name = "summary", nullable = false)
    private String summary;

}