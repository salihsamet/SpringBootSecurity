package com.clinked.articleservice.models;

import java.time.LocalDate;

public interface Statistics {
    LocalDate getPublishDate();
    Integer getArticleCount();
}
