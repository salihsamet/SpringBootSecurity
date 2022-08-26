package com.clinked.articleservice.unitTest.repository;

import com.clinked.articleservice.enums.Content;
import com.clinked.articleservice.models.Article;
import com.clinked.articleservice.models.Statistics;
import com.clinked.articleservice.repository.ArticleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    private Article article;

    @BeforeEach
    void setArticle(){
        article = new Article("author", "title", Content.ART, LocalDate.now());
    }

    @AfterEach
    void clearDb(){
        articleRepository.deleteAllInBatch();
    }

    @Test
    void saveArticle() {
        Article createdArticle = articleRepository.save(article);
        assertEquals(article.getAuthor(), createdArticle.getAuthor());
        assertEquals(article.getTitle(), createdArticle.getTitle());
        assertEquals(article.getContent(), createdArticle.getContent());
        assertEquals(article.getPublishDate(), createdArticle.getPublishDate());

    }

    @Test
    void getArticle() {
        articleRepository.save(article);
        Pageable paging = PageRequest.of(0, 1, Sort.by("id"));
        Page<Article> pagedResult = articleRepository.findAll(paging);
        List<Article> articles = pagedResult.getContent();
        assertEquals(article.getAuthor(), articles.get(0).getAuthor());
        assertEquals(article.getTitle(), articles.get(0).getTitle());
        assertEquals(article.getContent(), articles.get(0).getContent());
        assertEquals(article.getPublishDate(), articles.get(0).getPublishDate());

    }

    @Test
    void getStatistics() {
        articleRepository.save(article);
        articleRepository.save(new Article("author2", "title2", Content.MUSIC, LocalDate.now()));
        articleRepository.save(new Article("author3", "title3", Content.MUSIC, LocalDate.now().minusDays(3)));
        List<Statistics> statistics = articleRepository.getStatistics(LocalDate.now().minusDays(7));
        Map<LocalDate, Statistics> statisticsMap =
                statistics.stream().collect(Collectors.toMap(Statistics::getPublishDate, item -> item));
        assertEquals(1, statisticsMap.get(LocalDate.now().minusDays(3)).getArticleCount());
        assertEquals(2, statisticsMap.get(LocalDate.now()).getArticleCount());
        assertEquals(LocalDate.now(), statistics.get(1).getPublishDate());

    }

    @Test
    void getArticleNotExistInDb() {
        Pageable paging = PageRequest.of(0, 1, Sort.by("id"));
        Page<Article> pagedResult = articleRepository.findAll(paging);
        List<Article> articles = pagedResult.getContent();
        assertTrue(articles.size() == 0);

    }

    @Test
    void getStatisticNotExistInDbs() {
        List<Statistics> statistics = articleRepository.getStatistics(LocalDate.now().minusDays(7));
        assertTrue(statistics.size() == 0);

    }
}
