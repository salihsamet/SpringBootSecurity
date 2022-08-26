package com.clinked.articleservice.unitTest.service;

import com.clinked.articleservice.enums.Content;
import com.clinked.articleservice.models.Article;
import com.clinked.articleservice.models.Statistics;
import com.clinked.articleservice.repository.ArticleRepository;
import com.clinked.articleservice.service.ArticleServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {
    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleServiceImp articleServiceImp;

    private Article article;

    @BeforeEach
    void setArticle(){
        article = new Article("author", "title", Content.ART, LocalDate.now());
    }

    @Test
    void createArticle(){
        when(articleRepository.save(any(Article.class))).thenReturn(article);
        Article createdArticle = articleServiceImp.createArticle(new Article());
        assertEquals(article.getAuthor(), createdArticle.getAuthor());
        assertEquals(article.getTitle(), createdArticle.getTitle());
        assertEquals(article.getContent(), createdArticle.getContent());
        assertEquals(article.getPublishDate(), createdArticle.getPublishDate());
    }

    @Test
    void getStatistics() {
        when(articleRepository.getStatistics(any(LocalDate.class))).thenReturn(new ArrayList<Statistics>());
        List<Statistics> statistics = articleServiceImp.getStatistics();
        assertEquals(new ArrayList<Statistics>(), statistics);
    }
}