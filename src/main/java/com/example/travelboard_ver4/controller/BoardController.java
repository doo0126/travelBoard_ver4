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
    public  String updateForm (@PathVariable Long id , Model model) {
         BoardDTO boardDTO=boardService.findAllById(id);
         System.out.printf("boarDTO\n",boardDTO);
         model.addAttribute("boardDTO" ,boardDTO);

        return "boardPages/update";

    }
    @GetMapping("/filtering")
    public String filteringForm(){
        return "filteringPages/save";
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
        BoardDTO boardDTO = boardService.findAllById(id);
        model.addAttribute(boardDTO);
        return "boardPages/detail";

    }
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.printf("saveController:%s",boardDTO);
        boardService.save(boardDTO);



        return "redirect:/board/list";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO ){
        boardService.update(boardDTO);

        return "redirect:/board/list";
    }
    @PostMapping("/delete/{id}")
    public String delete (@PathVariable Long id ){
        boardService.delete(id);
                return "redirect:/board/list";
    }
//    @GetMapping("/search")
//    public String search(@RequestParam("req") String req , @RequestParam("type") int type , Model model){
//        List<BoardDTO> resultList = boardService.search(req , type);
//            model.addAttribute("boardList" , resultList);
//        return "boardPages/list";
//
//
//    }







}
