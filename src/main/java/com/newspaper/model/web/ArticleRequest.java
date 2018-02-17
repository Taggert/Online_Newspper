package com.newspaper.model.web;

import com.newspaper.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleRequest {

    private String title;
    private String body;
    private List<Category> categories;

}