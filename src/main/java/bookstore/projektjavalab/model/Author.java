// src/main/java/bookstore/projektjavalab/model/Author.java
package bookstore.projektjavalab.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String biography;
}
