package com.example.travelboard_ver4.repository;

import com.example.travelboard_ver4.entity.BoardEntity;
import com.example.travelboard_ver4.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Long , CommentEntity> {

    //select * from comment_table where board_id = boardId order by id desc;
    List<CommentEntity> findAllByEntityOrderByIdDesc(BoardEntity boardEntity);

}
