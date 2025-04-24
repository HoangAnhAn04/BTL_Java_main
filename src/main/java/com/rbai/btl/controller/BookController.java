// package com.rbai.btl.controller;

// import com.rbai.btl.dto.request.BookRequest;
// import com.rbai.btl.dto.response.ApiResponse;
// import com.rbai.btl.dto.response.BookResponse;
// import com.rbai.btl.service.BookService;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/v1/books")
// public class BookController {

//     private final BookService bookService;

//     public BookController(BookService bookService) {
//         this.bookService = bookService;
//     }

//     @PostMapping("/add")
//     public ResponseEntity<ApiResponse<BookResponse>> addBook(@RequestBody BookRequest request) {
//         return ResponseEntity.ok(bookService.addBook(request));
//     }

//     @PutMapping("/update/{id}")
//     public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable Long id, @RequestBody BookRequest request) {
//         return ResponseEntity.ok(bookService.updateBook(id, request));
//     }

//     @DeleteMapping("/delete/{id}")
//     public ResponseEntity<ApiResponse<String>> deleteBook(@PathVariable Long id) {
//         return ResponseEntity.ok(bookService.deleteBook(id));
//     }

//     @GetMapping("/getAll")
//     public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBooks() {
//         return ResponseEntity.ok(bookService.getAllBooks());
//     }

//     @GetMapping("/get/{id}")
//     public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable Long id) {
//         return ResponseEntity.ok(bookService.getBookById(id));
//     }
// }

package com.rbai.btl.controller;

import com.rbai.btl.dto.request.BookRequest;
import com.rbai.btl.dto.response.ApiResponse;
import com.rbai.btl.dto.response.BookResponse;
import com.rbai.btl.service.BookService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<BookResponse>> addBook(@RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.addBook(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable String id, @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBook(@PathVariable String id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}
