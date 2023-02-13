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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardFileRepository boardFileRepository;

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

        return BoardDTO.toDTO(boardEntity);


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
            return saveId;
        }
    }

//    public boolean delete(Long id) {
//        boardRepository.deleteById(id);
//        Optional<BoardEntity> boardEntity = boardRepository.findById(id);
//    if(boardEntity.isPresent()){
//        return true;
//    }else{
//        return false;
//    }
//    }

//    @Transactional
//    public boolean passwordAxios(Long id, String memberEmail) {
//
//        if (memberEmail.equals(boardRepository.findById(id).get().getBoardWriter())) {
//
//
//            System.out.printf("axios Ture");
//            return true;
//        } else {
//            System.out.printf("false Ture");
//            return false;
//        }
//    }

    @Transactional
    public BoardDTO findAllById(Long id) {

        BoardEntity boardEntity = boardRepository.findById(id).get();
        return BoardDTO.toDTO(boardEntity);


    }

    public void update(BoardDTO boardDTO) {

        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);

        boardRepository.save(boardEntity);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

//    public List<BoardDTO> search(String req, int type) {
//
//
//        //type 1 == title + contents
//        //type 2 == title
//        //type 3 == contents
//
//        if (type == 1) {
//            return toDTOList(boardRepository.findByBoardTitleContainingOrderById(req));
//
//        } else if( type == 2){
//                return     toDTOList(boardRepository.findByBoardContentsContainingOrderById(req));
//        }else if ( type == 3){
//            return  toDTOList(boardRepository.findByBoardTitleContainingOrBoardWriterContainingOrderByIdDesc(req));
//        }
//
//else {
//    return null;
//        }
//
//    }


    public List<BoardDTO> toDTOList(List<BoardEntity> boardEntityList) {
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (int i = 0; i < boardEntityList.size(); i++) {
            boardDTOList.add(BoardDTO.toDTO(boardEntityList.get(i)));
        }
        return boardDTOList;
    }
}

