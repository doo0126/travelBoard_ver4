package com.example.travelboard_ver4.dto;

import com.example.travelboard_ver4.entity.CommentEntity;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private int reCommentNum;
    private Timestamp commentCreatedDate;


    public static CommentDTO toDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setBoardId(commentEntity.getId());
        commentDTO.setReCommentNum(commentEntity.getReCommentNum());

        return commentDTO;


    }
}
