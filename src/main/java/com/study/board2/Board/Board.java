package com.study.board2.Board;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Board {

    @Id //PRIRMARY KEY
    @GeneratedValue(strategy = GenerationType.IDENTITY) //maria db
    private Integer id;
    private String title;
    private String content;
    private String filename;
    private String filepath;
}
