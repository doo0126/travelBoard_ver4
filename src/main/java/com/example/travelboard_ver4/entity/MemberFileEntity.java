package com.example.travelboard_ver4.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "member_file_table")
public class MemberFileEntity {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Column
    private String originalFileName;
@Column
    private String storedFileName;

@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;

 public static MemberFileEntity toMemberFileEntity(MemberEntity memberEntity , String originalFileName , String storedFileName){
     MemberFileEntity memberFileEntity = new MemberFileEntity();
        memberFileEntity.setOriginalFileName(originalFileName);
        memberFileEntity.setStoredFileName(storedFileName);
            memberFileEntity.setMemberEntity(memberEntity);
            return memberFileEntity;

 }

}
