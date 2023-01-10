package com.example.travelboard_ver4.entity;

import com.example.travelboard_ver4.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "member_table")

public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20 , nullable = false , unique = true)
    private String memberEmail;

    @Column(length = 20 , nullable = false )
    private String memberPassword;

    @Column(length = 10 , nullable = false)
    private String memberName;

    @Column(length = 15, nullable = false , unique = true)
    private String memberNickname;

    @Column(length =13  , nullable = false)
    private String memberMobile;

    @Column(length = 10)
    private String memberGender;

    @Column
    private int fileAttached;

    @OneToMany(mappedBy = "memberEntity" , cascade = CascadeType.REMOVE , orphanRemoval = true, fetch = FetchType.LAZY)
     private List<MemberFileEntity> memberFileEntityList = new ArrayList<>();



    public static MemberEntity toSaveEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberNickname(memberDTO.getMemberNickname());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setMemberGender(memberDTO.getMemberGender());
        memberEntity.setFileAttached(0);
        return memberEntity;
    }
    public static MemberEntity toSaveFile(MemberDTO memberDTO){

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberNickname(memberDTO.getMemberNickname());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setMemberGender(memberDTO.getMemberGender());
        memberEntity.setFileAttached(1);

        return memberEntity;
    }

}
