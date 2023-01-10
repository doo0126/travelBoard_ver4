package com.example.travelboard_ver4.repository;

import com.example.travelboard_ver4.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity , Long> {
    Optional<MemberEntity> findByMemberEmail(String input);

    Optional<MemberEntity> findByMemberNickname(String input);
    int countByMemberEmail(String input);
    int countByMemberNickname(String input);
}
