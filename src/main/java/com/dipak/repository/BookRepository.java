package com.dipak.repository;

import com.dipak.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book,Long> {
//    @Query("SELECT b.bookName FROM Book b WHERE LOWER(b.bookName) = LOWER(:name)")
//    String findByBookNameIgnoreCase(@Param("name") String name);

    Book findByBookNameIgnoreCase(String name);

}
