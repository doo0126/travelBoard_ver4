package com.example.travelboard_ver4.controller;

import com.example.travelboard_ver4.dto.BoardDTO;


import com.example.travelboard_ver4.repository.MemberRepository;
import com.example.travelboard_ver4.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
private final BoardService boardService;
    private final MemberRepository memberRepository;
// ------------------------form----------------------------------------



    @GetMapping("/saveForm")
    public String saveForm(){
        return "boardPages/save";
    }


    @GetMapping("/updateForm/{id}")
    public String updateForm(@PathVariable Long id , Model  model){
        System.out.printf("updateForm",id);
         BoardDTO boardDTO = boardService.detail(id);
        model.addAttribute("boardDTO" , boardDTO);
        return "boardPages/update";

    }

    //-----------------------------logic--------------------------------


    @GetMapping("/list")
    public String paging(@PageableDefault(page = 1)Pageable pageable, Model model, HttpSession session){

        Page<BoardDTO> boardDTOList = boardService.paging(pageable);
        model.addAttribute("boardList",boardDTOList);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit -1)< boardDTOList.getTotalPages())? startPage + blockLimit -1 : boardDTOList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage" ,endPage);
        String memberSession = (String) session.getAttribute("memberSession");
        model.addAttribute("memberSession" , memberSession);
        return "boardPages/list";




    }


    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id , Model model){
        BoardDTO boardDTO = boardService.detail(id);
        model.addAttribute(boardDTO);
        return "boardPages/detail";

    }
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        boardService.save(boardDTO);



        return "redirect:/board/list";
    }
    @GetMapping("/delete/{id}")
public String delete(@PathVariable Long id ){
        System.out.printf("deleteService");
        boardService.delete(id);
        return "redirect:/board/list";

    }

        @PostMapping("/passwordAxios")
    public ResponseEntity passwordAxios(@RequestParam("id") Long id , HttpSession session) {
        System.out.printf("boardAxios:%s\n", id);
        String memberEmail=(String)session.getAttribute("memberSession");
        boolean result = boardService.passwordAxios(id , memberEmail );

        if(result){
            return new ResponseEntity("사용가능" , HttpStatus.OK);
        }
        else{
            return new ResponseEntity("사용 불가" , HttpStatus.CONFLICT);
        }

        }


}
