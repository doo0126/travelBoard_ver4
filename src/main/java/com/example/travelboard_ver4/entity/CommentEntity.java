package com.example.travelboard_ver4.entity;

import com.example.travelboard_ver4.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "comment_table")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,nullable = false)
    private String commentWriter;

    @Column(length = 100 , nullable = false)
    private String commentContents;
    @Column
    private int reCommentNum;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch =  FetchType.LAZY)
    private BoardEntity boardEntity;



    public static CommentEntity toSaveEntity(BoardEntity boardEntity , CommentDTO commentDTO){


        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }

}
