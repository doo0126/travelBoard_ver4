package com.example.travelboard_ver4.service;

import com.example.travelboard_ver4.dto.BoardDTO;
import com.example.travelboard_ver4.dto.CommentDTO;
import com.example.travelboard_ver4.entity.BoardEntity;
import com.example.travelboard_ver4.entity.CommentEntity;
import com.example.travelboard_ver4.repository.BoardRepository;
import com.example.travelboard_ver4.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public Long save(CommentDTO commentDTO) {
        BoardEntity boardEntity = boardRepository.findById(commentDTO.getBoardId()).get();
        CommentEntity commentEntity = CommentEntity.toSaveEntity(boardEntity, commentDTO);
        Long id = commentRepository.save(commentEntity).getId();
        return id;
    }

    @Transactional
    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByEntityOrderByIdDesc(boardEntity);



    }
}
