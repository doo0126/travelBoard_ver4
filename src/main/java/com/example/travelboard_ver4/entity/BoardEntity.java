package com.example.travelboard_ver4.entity;

import com.example.travelboard_ver4.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "board_table")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String boardOriginalFileName;
    @Column
    private String boardStoredFileName;
    @Column(length = 20 , nullable = false)
    private String boardTitle;
    @Column(nullable = false)
    private String boardContents;
    @Column(length = 30 , nullable = false)
    private  String boardWriter;

   @Column(length = 30 ,  nullable = false)
   private String boardPassword;




    @Column
    private int boardFileAttached;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberEntity")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "boardEntity" ,cascade = CascadeType.REMOVE , orphanRemoval = true ,fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    public static BoardEntity toSaveEntity(BoardDTO boardDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setMemberEntity(memberEntity);
        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
        boardEntity.setBoardFileAttached(0);

        //하위에선 상위 엔티티로 외래키 저장

        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO, MemberEntity memberEntity) {
      BoardEntity boardEntity = new BoardEntity();
      boardEntity.setBoardWriter(boardDTO.getBoardWriter());
      boardEntity.setBoardTitle(boardDTO.getBoardTitle());

      boardEntity.setBoardContents(boardDTO.getBoardContents());
      boardEntity.setBoardFileAttached(1);
      boardEntity.setMemberEntity(memberEntity);
      return boardEntity;


    }
    public static BoardEntity toUpdateEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardPassword(boardDTO.getBoardPassword());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        return boardEntity;
    }
}
