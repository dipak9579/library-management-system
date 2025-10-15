package com.dipak.entity;

import jakarta.persistence.*;

@Entity
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineId;

    private Double amount;
    private boolean paid;

    @OneToOne
    private BorrowRecord borrowRecord;
}
