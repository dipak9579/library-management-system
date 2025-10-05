package com.dipak.service;

import com.dipak.entity.Book;
import com.dipak.entity.Category;
import com.dipak.repository.BookRepository;
import com.dipak.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public String addBook(Book book){
        String bookName=bookRepository.findByBookNameIgnoreCase(book.getBookName());
        if(bookName!=null){
            return "Book name already exists";
        }

        Long categoryId=book.getCategory().getCategory_id();
        Category existCategory=categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Category not found"));
        book.setCategory(existCategory);
        bookRepository.save(book);
        return "Book added successfully";
    }


}
