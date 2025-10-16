package com.dipak.controller;

import com.dipak.DTO.BorrowRequest;
import com.dipak.entity.Book;
import com.dipak.entity.BorrowRecord;
import com.dipak.service.BookService;
import com.dipak.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserBookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowRecordService borrowRecordService;

    @GetMapping("/searchBook/{name}")
    public ResponseEntity<?>searchBookByName(@PathVariable String name){
        Optional<Book> book=bookService.searchBookByName(name);

        if(book.isEmpty()){
            return new ResponseEntity<>("Book not found",HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(book,HttpStatus.OK);
        }
    }

    @PostMapping("/borrowBook")
    public ResponseEntity<?>borrowBook(@RequestBody BorrowRequest borrowRequest){
        try {
            BorrowRecord record=borrowRecordService.borrowBook(borrowRequest.getId(),borrowRequest.getName());
            return ResponseEntity.ok(record);
        }
        catch (Exception e){
          return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/returnBook")
    public ResponseEntity<String>returnBook(@RequestBody BorrowRequest borrowRequest){
        return ResponseEntity.ok(borrowRecordService.returnBook(borrowRequest.getId(),borrowRequest.getName()));
    }

}
