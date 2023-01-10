package com.example.travelboard_ver4.controller;

import com.example.travelboard_ver4.dto.MemberDTO;
import com.example.travelboard_ver4.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private  final MemberService memberService;

    //-------------------Form------------------------------------------
    @GetMapping("/saveForm")
    public String saveForm() {
        return "/memberPages/save";
    }

    //----------------------duplicateAxios-----------------------------------
        @PostMapping("/dupEmail")
        public ResponseEntity  dupEmail(@RequestParam("inputEmail")String inputEmail){
            return duplicate(memberService.dupCheck(inputEmail));

        }
        @PostMapping("dupNickname")
        public ResponseEntity duNickname(@RequestParam("inputNickname") String inputNickname){
        return  duplicate(memberService.dupCheck(inputNickname));
        }
        public ResponseEntity  duplicate(boolean result) {
            if (result == true){
                return new ResponseEntity <>("사용 가능",HttpStatus.OK);
            }
            else {
                return new ResponseEntity <>("사용 불가", HttpStatus.CONFLICT);
            }
        }

// -----------------------------------------------------------------------
    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.save(memberDTO);
    return "index";
    }
}
