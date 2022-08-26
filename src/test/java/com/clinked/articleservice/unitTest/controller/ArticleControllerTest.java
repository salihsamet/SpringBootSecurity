package com.clinked.articleservice.unitTest.controller;

import com.clinked.articleservice.enums.Content;
import com.clinked.articleservice.models.Article;
import com.clinked.articleservice.models.Statistics;
import com.clinked.articleservice.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {
    @MockBean
    ArticleService articleService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"USER"})
    void getArticlesTest() throws Exception {
        Article article = new Article("author", "title", Content.ART, LocalDate.now());
        List<Article> articles = Arrays.asList(article);

        Mockito.when(articleService.getArticles(0,10, "id", true)).thenReturn(articles);

        mockMvc.perform(get("/api/articles/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").exists())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").isNotEmpty())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].author").value(article.getAuthor()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value(article.getTitle()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value(article.getContent().getValue()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].publishDate").value(article.getPublishDate().toString()));
    }

    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"USER"})
    void getArticlesForNoArticleTest() throws Exception {
        Article article = new Article("author", "title", Content.ART, LocalDate.now());
        List<Article> articles = Arrays.asList(article);

        Mockito.when(articleService.getArticles(0,10, "id", true)).thenReturn(new ArrayList<Article>());

        mockMvc.perform(get("/api/articles/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").doesNotExist());
    }

    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"USER"})
    void createArticlesTest() throws Exception {
        Article article = new Article("author", "title", Content.ART, LocalDate.now());
        List<Article> articles = Arrays.asList(article);

        Mockito.when(articleService.createArticle(any(Article.class))).thenReturn(article);

        mockMvc.perform(post("/api/articles/create")
                        .content(Util.asJsonString(article))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"USER"})
    void createArticlesWithNullAuthorTest() throws Exception {
        Article article = new Article(null, "title", Content.ART, LocalDate.now());
        List<Article> articles = Arrays.asList(article);

        Mockito.when(articleService.createArticle(any(Article.class))).thenReturn(article);

        mockMvc.perform(post("/api/articles/create")
                        .content(Util.asJsonString(article))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError());
    }
    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"USER"})
    void createArticlesWithNullTitleTest() throws Exception {
        Article article = new Article("author", null, Content.ART, LocalDate.now());
        List<Article> articles = Arrays.asList(article);

        Mockito.when(articleService.createArticle(any(Article.class))).thenReturn(article);

        mockMvc.perform(post("/api/articles/create")
                        .content(Util.asJsonString(article))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"USER"})
    void createArticlesWithExceedTitleTest() throws Exception {
        Article article = new Article("author",
                "exceedexceedexceedexceedexceedexceedexceedexceedexceedexceed" +
                        "exceedexceedexceedexceedexceedexceedexceedexceedexceedexceed", Content.ART, LocalDate.now());
        List<Article> articles = Arrays.asList(article);

        Mockito.when(articleService.createArticle(any(Article.class))).thenReturn(article);

        mockMvc.perform(post("/api/articles/create")
                        .content(Util.asJsonString(article))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"USER"})
    void createArticlesWithNullContentTest() throws Exception {
        Article article = new Article("author", "title", null, LocalDate.now());
        List<Article> articles = Arrays.asList(article);

        Mockito.when(articleService.createArticle(any(Article.class))).thenReturn(article);

        mockMvc.perform(post("/api/articles/create")
                        .content(Util.asJsonString(article))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError());
    }


    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"USER"})
    void createArticlesWithNullPublishDateTest() throws Exception {
        Article article = new Article("author", "title", Content.ART, null);
        List<Article> articles = Arrays.asList(article);

        Mockito.when(articleService.createArticle(any(Article.class))).thenReturn(article);

        mockMvc.perform(post("/api/articles/create")
                        .content(Util.asJsonString(article))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError());
    }


    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"ADMIN"})
    void getStatisticTest() throws Exception {
        List<Statistics> statistics = new ArrayList<>();
        Mockito.when(articleService.getStatistics()).thenReturn(statistics);

        mockMvc.perform(get("/api/articles/admin/statistics")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").doesNotExist());
    }

    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"USER"})
    void getStatisticForUnauthorizedTest() throws Exception {
        List<Statistics> statistics = new ArrayList<>();
        Mockito.when(articleService.getStatistics()).thenReturn(statistics);

        mockMvc.perform(get("/api/articles/admin/statistics")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized());
    }


}
