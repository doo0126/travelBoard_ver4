package com.example.travelboard_ver4.repository;

import com.example.travelboard_ver4.entity.MemberEntity;
import com.example.travelboard_ver4.entity.MemberFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFileRepository extends JpaRepository<MemberFileEntity, Long> {


}
