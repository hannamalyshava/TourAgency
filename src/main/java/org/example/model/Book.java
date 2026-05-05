package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(nullable = false, length = 100)
    private String author;
    
    @Column(length = 20)
    private String isbn;
    
    @Column(name = "publication_year")
    private Integer publicationYear;
    
    @Column(length = 50)
    private String genre;
    
    private Double price;
    
    @Column(name = "pages_count")
    private Integer pagesCount;
}