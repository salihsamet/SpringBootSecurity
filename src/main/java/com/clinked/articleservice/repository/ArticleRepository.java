package com.clinked.articleservice.repository;

import com.clinked.articleservice.models.Article;
import com.clinked.articleservice.models.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "SELECT publish_date as publishDate, COUNT(id) as articleCount " +
                   "FROM article " +
                   "WHERE publish_date >= :daysAgo " +
                   "GROUP BY publish_date", nativeQuery = true)
    List<Statistics> getStatistics(@Param("daysAgo") LocalDate daysAgo);
}
