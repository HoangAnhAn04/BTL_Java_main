package com.rbai.btl.service;

import com.rbai.btl.dto.request.BookRequest;
import com.rbai.btl.dto.response.ApiResponse;
import com.rbai.btl.dto.response.BookResponse;
import com.rbai.btl.entity.Book;
import com.rbai.btl.enums.StatusCode;
import com.rbai.btl.exception.CustomException;
import com.rbai.btl.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ApiResponse<BookResponse> addBook(BookRequest request) {
        Book book = bookRepository.save(Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .genre(request.getGenre())
                .rating(request.getRating())
                .coverUrl(request.getCoverUrl())
                .coverColor(request.getCoverColor())
                .description(request.getDescription())
                .totalCopies(request.getTotalCopies())
                .availableCopies(request.getAvailableCopies())
                .videoUrl(request.getVideoUrl())
                .summary(request.getSummary())
                .build()
        );

        return ApiResponse.success(StatusCode.SUCCESS, toResponse(book));
    }

    public ApiResponse<BookResponse> updateBook(String id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setRating(request.getRating());
        book.setCoverUrl(request.getCoverUrl());
        book.setCoverColor(request.getCoverColor());
        book.setDescription(request.getDescription());
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(request.getAvailableCopies());
        book.setVideoUrl(request.getVideoUrl());
        book.setSummary(request.getSummary());

        bookRepository.save(book);
        return ApiResponse.success(StatusCode.SUCCESS, toResponse(book));
    }

    public ApiResponse<String> deleteBook(String id) {
        if (!bookRepository.existsById(id)) {
            throw new CustomException(StatusCode.NOT_FOUND);
        }

        bookRepository.deleteById(id);
        return ApiResponse.success(StatusCode.SUCCESS, "Deleted successfully");
    }

    public ApiResponse<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(StatusCode.SUCCESS, books);
    }

    public ApiResponse<BookResponse> getBookById(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new CustomException(StatusCode.NOT_FOUND));
        return ApiResponse.success(StatusCode.SUCCESS, toResponse(book));
    }

    private BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .rating(book.getRating())
                .coverUrl(book.getCoverUrl())
                .coverColor(book.getCoverColor())
                .description(book.getDescription())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .videoUrl(book.getVideoUrl())
                .summary(book.getSummary())
                .build();
    }
}
