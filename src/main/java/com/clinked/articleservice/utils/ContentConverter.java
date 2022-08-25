package com.clinked.articleservice.utils;

import com.clinked.articleservice.enums.Content;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ContentConverter implements AttributeConverter<Content, String> {

    @Override
    public String convertToDatabaseColumn(Content category) {
        return category.getValue();
    }

    @Override
    public Content convertToEntityAttribute(String value) {
        return Stream.of(Content.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
