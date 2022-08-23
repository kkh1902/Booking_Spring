package com.example.springCrud.domain.entity;

import lombok.*;

import javax.persistence.*;


// DB 테이블과 매칭되는 객체(Entity)를 정의
// JPA에서는 Entity를 통해 데이터를 조작함
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends TimeEntity{

    @Id @GeneratedValue
    private  Long id;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public Board(Long id, String title, String content, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
