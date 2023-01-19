package com.example.travelboard_ver4.repository;

import com.example.travelboard_ver4.entity.BoardFileEntity;
import com.example.travelboard_ver4.entity.MemberFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity , Long > {
}
