package com.dipak.repository;

import com.dipak.entity.BorrowRecord;
import com.dipak.entity.BorrowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord,Long> {

    Optional<BorrowRecord>findByUserIdAndBookBookNameIgnoreCaseAndBorrowStatus
            (Long userId, String bookName, BorrowStatus borrowStatus);
}
