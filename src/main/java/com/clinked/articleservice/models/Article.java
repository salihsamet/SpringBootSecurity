package com.clinked.articleservice.models;

import com.clinked.articleservice.enums.Content;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "article")
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @NotBlank
    @Size(max = 100)
    @Column(name="title", nullable = false, length = 100)
    private String title;

    @NotBlank
    @Column(name="author", nullable = false)
    private String author;

    @NotNull
    @Column(name="content", nullable = false)
    private Content content;

    @NotNull
    @Column(name="publish_date", columnDefinition = "DATE", nullable = false)
    private LocalDate publishDate;

    public Article(String author, String title, Content content, LocalDate publishDate) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
    }

    public String toString(){
        return "title:" + title + ", author: " + author + ", content" + content.getValue() + ", publishDate:" + publishDate;
    }
}
