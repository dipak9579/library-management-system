package com.dipak.service;

import com.dipak.entity.Book;
import com.dipak.entity.Category;
import com.dipak.repository.BookRepository;
import com.dipak.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public String addBook(Book book){
        if(book==null||book.getBookName()==null){
            return "Invalid book data";
        }

        Book existingBook=bookRepository.findByBookNameIgnoreCase(book.getBookName());
       if(existingBook!=null){
           return "Book already exists";
       }

        Long categoryId=book.getCategory().getCategory_id();
        Category existCategory=categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Category not found"));
        book.setCategory(existCategory);
        bookRepository.save(book);
        return "Book added successfully";
    }

    public Optional<Book> searchBookByName(String name){
       return Optional.ofNullable(bookRepository.findByBookNameIgnoreCase(name));
    }

    public List<Book>getAllBooks(){
        return bookRepository.findAll();
    }

    public String deleteBookByName(String name){
        Book book=bookRepository.findByBookNameIgnoreCase(name);
        if(book!=null){
            bookRepository.delete(book);
            return name+" book deleted from library record";
        }
        else{
            return name+" book not found in library record";
        }
    }

    public String updateBookByName(String name,Book updateBookData){
        Book book=bookRepository.findByBookNameIgnoreCase(name);

        if(book==null){
            return "Book not found with name: "+name;
        }

        if(updateBookData.getCategory()!=null&&updateBookData.getCategory().getCategory_id()!=null){
            Category existingCategory=categoryRepository.findById(updateBookData.getCategory().getCategory_id())
                    .orElseThrow(()->new RuntimeException("Category not found"));

            book.setCategory(existingCategory);
        }
        book.setAuthorName(updateBookData.getAuthorName());

        book.setAvailableCopies(updateBookData.getAvailableCopies());
        book.setTotalCopies(updateBookData.getTotalCopies());
        bookRepository.save(book);

        return "Book updated successfully";
    }

}
