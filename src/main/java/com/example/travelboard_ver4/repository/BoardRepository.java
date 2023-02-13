package com.example.travelboard_ver4.repository;

import com.example.travelboard_ver4.dto.BoardDTO;
import com.example.travelboard_ver4.entity.BoardEntity;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity , Long> {


//List<BoardEntity> findByBoardTitleContainingOrderById(String req);
//List<BoardEntity> findByBoardContentsContainingOrderById(String req);
//
//    List<BoardEntity> findByBoardTitleContainingOrBoardWriterContainingOrderByIdDesc(String req);



}
