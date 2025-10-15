package com.dipak.controller;

import com.dipak.entity.Book;
import com.dipak.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<String>saveBook(@RequestBody Book book){
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<?>searchBookByTitle(@PathVariable String title){
        Optional<Book> book=bookService.searchBookByName(title.trim());
        if(book.isPresent()){
            return ResponseEntity.ok(book);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with title "+title);
        }
    }

    @GetMapping("/allBooks")
    public ResponseEntity<List<Book>>findAllBooks(){
        return new ResponseEntity<>(bookService.getAllBooks(),HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook/{name}")
    public ResponseEntity<String>deleteBookByName(@PathVariable String name){
        return new ResponseEntity<>(bookService.deleteBookByName(name),HttpStatus.OK);
    }


    @PutMapping("/updateBook/{name}")
    public ResponseEntity<String>updateBookByName(@PathVariable String name,@RequestBody Book book){
        return new ResponseEntity<>(bookService.updateBookByName(name,book),HttpStatus.OK);
    }


}
