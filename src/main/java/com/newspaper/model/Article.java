package com.newspaper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article {

    private Integer id;
    private String title;
    private String body;
    private Date createdOn;
    private User createdBy;
    private List<Category> categories;
    private List<Comment> comments;

}