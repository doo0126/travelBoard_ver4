package com.example.travelboard_ver4.controller;

import com.example.travelboard_ver4.dto.CommentDTO;
import com.example.travelboard_ver4.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
@PostMapping("/save")
    public ResponseBody save(@ModelAttribute CommentDTO commentDTO){

commentService.save(commentDTO);
List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());


}
}
