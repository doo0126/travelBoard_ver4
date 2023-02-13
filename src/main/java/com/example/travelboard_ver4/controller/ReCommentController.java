package com.example.travelboard_ver4.controller;

import com.example.travelboard_ver4.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reComment")
public class ReCommentController {
private final ReCommentService reCommentService;

}
