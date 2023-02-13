package com.example.travelboard_ver4.dto;

import com.example.travelboard_ver4.entity.BoardEntity;
import com.example.travelboard_ver4.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor


public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private String boardPassword;


    private List<MultipartFile> boardFile;
    private List<String> boardOriginalFileName;
    private List<String> boardStoredFileName;
    private int boardFileAttached;

    public BoardDTO(Long id, String boardTitle, String boardContents, String boardWriter) {
        this.setId(id);
        this.setBoardContents(boardContents);
        this.setBoardTitle(boardTitle);
        this.setBoardWriter(boardWriter);


    }

    public static BoardDTO toDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardPassword(boardEntity.getBoardPassword());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());

        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        if (boardEntity.getBoardFileAttached() == 1) {
            //file o
            boardDTO.setBoardFileAttached(boardEntity.getBoardFileAttached());
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()) {//엔티티 디티오 +연관 관계 해야함
                originalFileNameList.add(boardFileEntity.getBoardOriginalFileName());
                storedFileNameList.add(boardFileEntity.getBoardStoredFileName());
            }

            boardDTO.setBoardOriginalFileName(originalFileNameList);
            boardDTO.setBoardStoredFileName(storedFileNameList);

        } else {
            boardDTO.setBoardFileAttached(0);
        }
        return boardDTO;
    }
}




