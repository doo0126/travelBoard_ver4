package com.example.travelboard_ver4.service;

import com.example.travelboard_ver4.dto.MemberDTO;
import com.example.travelboard_ver4.entity.MemberEntity;
import com.example.travelboard_ver4.entity.MemberFileEntity;
import com.example.travelboard_ver4.repository.MemberFileRepository;
import com.example.travelboard_ver4.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberFileRepository memberFileRepository;

    public boolean dupCheck(String input) {

        boolean emailOrNickname = false; // 이메일이면 true 닉네임일 시 false
        for (int i = 0; i < input.length(); i++) {
            int result = input.charAt(i);
            System.out.printf("이메일 아스키:%d\n", result);
            if (result == 64) {
                emailOrNickname = true;
                break;
            } else {
                emailOrNickname = false;

            }


        }
        if (emailOrNickname == true) {
            //email


            int result = memberRepository.countByMemberEmail(input);

            return entityCheck(result);
        } else {
            //nickname

            int result = memberRepository.countByMemberNickname(input);
            System.out.printf("닉네임 값%s", result);
            return entityCheck(result);
        }


    }

    public boolean entityCheck(int result) {
        if (result == 0) {
            return true;
        } else {
            return false;

        }
    }

    public void save(MemberDTO memberDTO) throws IOException {
        if (memberDTO.getMemberFile().isEmpty()) {
            //file x
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            memberRepository.save(memberEntity);

        } else {
            MultipartFile memberFile = memberDTO.getMemberFile();
            String originalFileName = memberFile.getName();
            String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
            String savePath = "D:/travelMemberFile_img/" + storedFileName;
            memberFile.transferTo(new File(savePath));
            //fileEntity wjwkd
            MemberEntity memberEntity = MemberEntity.toSaveFile(memberDTO);
            Long saveId = memberRepository.save(memberEntity).getId(); //membertable save
            MemberEntity member = memberRepository.findById(saveId).get(); //외래키 가져오고
            MemberFileEntity memberFileEntity = MemberFileEntity.toMemberFileEntity(member, originalFileName, storedFileName);
            //자식은 fk 무조건 가져오고 엔티티 변환 후 세이브
            memberFileRepository.save(memberFileEntity);


        }
    }


    @Transactional
    public Boolean login(MemberDTO memberDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail()).get();

        System.out.printf("loginServiceEntity:%s",memberEntity);
        if (memberEntity.getMemberEmail().equals(memberDTO.getMemberEmail()) && memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {

            System.out.printf("loginServiceTrue");
            return true;

        } else {
            return false;
        }


    }


    }

