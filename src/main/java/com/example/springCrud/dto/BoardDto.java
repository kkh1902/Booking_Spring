package com.example.springCrud.dto;

import com.example.springCrud.domain.entity.Board;
import lombok.*;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;

// controller와 service간에 주고 받을 객체를 정의하며, 최종적으로는 view에 뿌려줄 객체
// Entity와 속성이 같을 수 있으나, 여러 service를 거쳐야 하는 경우 dto의 몸집은 더커짐
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private  String content;
    private LocalDateTime createdDate;
    private  LocalDateTime modifiedDate;

    public Board toEntity() {
        Board build = Board.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .build();
        return build;
    }

    @Builder
    public BoardDto(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
