package com.example.travelboard_ver4.dto;

import com.example.travelboard_ver4.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class MemberDTO {
private Long id;
private String memberEmail;
private String memberPassword;
private String memberName;
private String memberNickname;
private String memberMobile;
private String memberGender;

private MultipartFile memberFile;
private String originalFileName;
private String storedFileName;
private int Attached;

public static MemberDTO toMemberDTO(MemberEntity memberEntity){
     MemberDTO memberDTO = new MemberDTO();
              memberDTO.setId(memberEntity.getId());

            memberDTO.setMemberEmail(memberEntity.getMemberEmail());
            memberDTO.setMemberPassword(memberEntity.getMemberPassword());
            memberDTO.setMemberName(memberEntity.getMemberName());
            memberDTO.setMemberNickname(memberEntity.getMemberNickname());
            memberDTO.setMemberGender(memberEntity.getMemberGender());
            if(memberEntity.getFileAttached() == 0){
                   memberDTO.setAttached(memberEntity.getFileAttached());//no file

            }else{
                   memberDTO.setAttached(memberEntity.getFileAttached());//file
                            //select * from member_table inner join member_file_table on
                            //member_table.id =member_file_table.member_id; 이 쿼리
                   memberDTO.setOriginalFileName(memberEntity.getMemberFileEntityList().get(0).getOriginalFileName());
                     memberDTO.setStoredFileName(memberEntity.getMemberFileEntityList().get(0).getStoredFileName());


            }
                System.out.printf("DTO:%s",memberDTO);
            return memberDTO;
}


}
