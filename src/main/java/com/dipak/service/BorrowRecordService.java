package com.dipak.service;

import com.dipak.entity.*;
import com.dipak.repository.BookRepository;
import com.dipak.repository.BorrowRecordRepository;
import com.dipak.repository.FineRepository;
import com.dipak.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;

@Service
public class BorrowRecordService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private FineRepository fineRepository;


    public BorrowRecord borrowBook(Long id,String name){

        //find user
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));

        //find book
        Book book=bookRepository.findByBookNameIgnoreCase(name);
        if(book==null){
             throw new RuntimeException("Book not found");
        }

        //check availability
        if(book.getAvailableCopies()<=0){
            throw new RuntimeException("Book copies not available");
        }

        //create Borrow record
        BorrowRecord record=new BorrowRecord();
        record.setUser(user);
        record.setBook(book);
        record.setBorrowDate(new Date());

        //set expected after 7 days

        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,7);
        record.setReturnDate(cal.getTime());
        record.setBorrowStatus(BorrowStatus.BORROWED);

        //decrease available copies
        book.setAvailableCopies(book.getAvailableCopies()-1);
        bookRepository.save(book);

        //save record
        return borrowRecordRepository.save(record);

    }

    public String returnBook(Long id,String name){

        //find borrow record and status
        BorrowRecord record=borrowRecordRepository.findByUserIdAndBookBookNameIgnoreCaseAndBorrowStatus(id,name,BorrowStatus.BORROWED)
                .orElseThrow(()->new RuntimeException("No borrow record found for book and user"));

        //update borrow record
        record.setActualReturnDate(new Date());
        record.setBorrowStatus(BorrowStatus.RETURNED);

        //update book copies
        Book book=record.getBook();
        book.setAvailableCopies(book.getAvailableCopies()+1);

        bookRepository.save(book);

        //save updated record
        borrowRecordRepository.save(record);

        //check book has late fine or not
        Date returnDate=record.getReturnDate();
        Date actualDate=record.getActualReturnDate();

        if(actualDate.after(returnDate)){
            long daysLate= ChronoUnit.DAYS.between((Temporal) returnDate, (Temporal) actualDate);
            double fineAmount=daysLate*10.0;

            Fine fine=new Fine();
            fine.setAmount(fineAmount);
            fine.setPaid(false);
            fine.setBorrowRecord(record);
            fineRepository.save(fine);

            return "Book returned late by " + daysLate + " days. Fine = ₹" + fineAmount;

        }


        //return message of return book
        return "Book "+name+" returned successfully by user id "+id;

    }
}
