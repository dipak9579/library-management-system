package com.dipak.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String bookName;

    private String authorName;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    @Column(unique = true)
    private String isbn;

    private Integer totalCopies;

    private Integer availableCopies;
}
