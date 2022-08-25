package com.clinked.articleservice.unitTest.controller;

import com.clinked.articleservice.contoller.ArticleController;
import com.clinked.articleservice.enums.Content;
import com.clinked.articleservice.enums.Role;
import com.clinked.articleservice.models.Article;
import com.clinked.articleservice.models.Statistics;
import com.clinked.articleservice.models.User;
import com.clinked.articleservice.service.ArticleService;
import com.clinked.articleservice.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
     public void getArticlesTest() throws Exception {
        Article article = new Article("author", "title", Content.ART, LocalDate.now());
        List<Article> articles = Arrays.asList(article);

        Mockito.when(articleService.getArticles(0,10, "id", true)).thenReturn(articles);

        mockMvc.perform(get("/api/articles/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").exists())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author").value(article.getAuthor()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value(article.getTitle()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].content").value(article.getContent().getValue()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].publishDate").value(article.getPublishDate().toString()));
    }

    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"USER"})
    public void getArticlesForNoArticleTest() throws Exception {
        Article article = new Article("author", "title", Content.ART, LocalDate.now());
        List<Article> articles = Arrays.asList(article);

        Mockito.when(articleService.getArticles(0,10, "id", true)).thenReturn(new ArrayList<Article>());

        mockMvc.perform(get("/api/articles/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").doesNotExist());
    }

    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"USER"})
    public void createArticlesTest() throws Exception {
        Article article = new Article("author", "title", Content.ART, LocalDate.now());
        List<Article> articles = Arrays.asList(article);

        Mockito.when(articleService.createArticle(article)).thenReturn(article);

        mockMvc.perform(post("/api/articles/create")
                        .content(asJsonString(article))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.author").exists());
    }

    private String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(username = "test_user", password = "test_password", roles = {"USER"})
    public void getStatisticTest() throws Exception {
        Article article = new Article("author", "title", Content.ART, LocalDate.now());
        List<Statistics> a = new ArrayList<>();
        Mockito.when(articleService.getStatistics()).thenReturn(null);

        mockMvc.perform(get("/api/articles//admin/statistics")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author").value(article.getAuthor()));
    }




}
