package com.example.travelboard_ver4.controller;

import com.example.travelboard_ver4.dto.MemberDTO;
import com.example.travelboard_ver4.repository.MemberRepository;
import com.example.travelboard_ver4.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Scanner;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private  final MemberService memberService;

    //-------------------Form------------------------------------------
    @GetMapping("/saveForm")
    public String saveForm() {
        return "memberPages/save";
    }
    @GetMapping("loginForm")
    public String loginForm(){
        return "memberPages/login";
    }

    //----------------------duplicateAxios-----------------------------------
        @PostMapping("/dupEmail")
        public ResponseEntity  dupEmail(@RequestParam("inputEmail")String inputEmail){
            return duplicate(memberService.dupCheck(inputEmail));

        }
        @PostMapping("/dupNickname")
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
    Scanner sc= new Scanner(System.in);
    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public  String login(@ModelAttribute MemberDTO memberDTO , Model model , HttpSession session){

       boolean resultDTO = memberService.login(memberDTO);


            if(resultDTO) {
                model.addAttribute("memberDTO", memberDTO);

                session.setAttribute("memberSession", memberDTO.getMemberEmail());


                return "index";
            }else{

                return "redirect:/member/loginForm";

            }




    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();

        return "index";
    }
}
