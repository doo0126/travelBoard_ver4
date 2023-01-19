package com.example.travelboard_ver4.service;

import com.example.travelboard_ver4.dto.BoardDTO;

import com.example.travelboard_ver4.entity.BoardEntity;
import com.example.travelboard_ver4.entity.BoardFileEntity;
import com.example.travelboard_ver4.entity.MemberEntity;
import com.example.travelboard_ver4.repository.BoardFileRepository;
import com.example.travelboard_ver4.repository.BoardRepository;
import com.example.travelboard_ver4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service

public class BoardService {
    @Autowired
    private  BoardRepository boardRepository;
    @Autowired
    private  MemberRepository memberRepository;
    @Autowired
    private  BoardFileRepository boardFileRepository;

    public BoardService(BoardRepository boardRepository,
                        MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }


    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        final int pageLimit = 10;
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardDTO> boardList = boardEntities.map(
                board -> new BoardDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardContents(),
                        board.getBoardWriter()
                )
        );
        return boardList;


    }


    //아직 안만듬
    @Transactional
    public BoardDTO detail(Long id) {
    BoardEntity boardEntity = boardRepository.findById(id).get();

    return  BoardDTO.toDTO(boardEntity);


    }

    public Long save(BoardDTO boardDTO) throws IOException {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(boardDTO.getBoardWriter()).get();
        if (boardDTO.getBoardFile() == null || boardDTO.getBoardFile().size() == 0) {
            System.out.printf("file x\n");
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, memberEntity);
            return boardRepository.save(boardEntity).getId();
        } else {
            System.out.printf("file o\n");
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO, memberEntity);
            Long saveId = boardRepository.save(boardEntity).getId();
            BoardEntity entity = boardRepository.findById(saveId).get();
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFileName = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "D:\\travelBoardFile_img";
                boardFile.transferTo(new File(savePath));
                BoardFileEntity boardFileEntity = BoardFileEntity.toSaveBoardFileEntity(originalFileName, storedFileName, entity);
                boardFileRepository.save(boardFileEntity).getId();

            }

return  saveId;
        }

    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public boolean passwordAxios(Long id, String memberEmail) {
        BoardEntity boardEntity=boardRepository.findById(id).get();
        System.out.printf("entity%s",boardEntity);
    if(boardEntity.getBoardWriter().equals(memberEmail)){
        System.out.printf("로그인정보 일치\n");
        return true;
    }else{
        return false;

    }
    }
}

