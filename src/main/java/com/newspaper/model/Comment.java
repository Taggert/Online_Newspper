package com.newspaper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {

    private Integer id;
    private String body;
    private Date createdOn;
    private User createdBy;

}