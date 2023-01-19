package com.example.travelboard_ver4.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board_file_table")


public class BoardFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String boardOriginalFileName;
    @Column
    private String boardStoredFileName;

// ------------- board - file
    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static BoardFileEntity toSaveBoardFileEntity(String boardOriginalFileName  , String boardStoredFileName , BoardEntity boardEntity){
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setBoardOriginalFileName(boardOriginalFileName);
        boardFileEntity.setBoardStoredFileName(boardStoredFileName);
        boardFileEntity.setBoardEntity(boardEntity);
        return boardFileEntity;
    }
}
