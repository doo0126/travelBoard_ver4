package com.example.travelboard_ver4.repository;

import com.example.travelboard_ver4.entity.BoardEntity;
import com.example.travelboard_ver4.paging.BoardPaging;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity , Long> {



    Optional<BoardEntity> findByBoardWriter(String boardWriter);
}
