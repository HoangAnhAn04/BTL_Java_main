// package com.rbai.btl.repository;

// import com.rbai.btl.entity.Book;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.util.List;

// @Repository
// public interface BookRepository extends JpaRepository<Book, Long> {
//     List<Book> findByTitleContainingIgnoreCase(String title);
//     List<Book> findByAuthorContainingIgnoreCase(String author);
//     List<Book> findByCategoryContainingIgnoreCase(String category);
// }

package com.rbai.btl.repository;

import com.rbai.btl.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByGenreContainingIgnoreCase(String genre); // Nếu bạn muốn tìm theo thể loại
}
