package com.example.springCrud.controller;


import com.example.springCrud.dto.BoardDto;
import com.example.springCrud.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

// Controller
// URL과 실행함수를 매핑
// 비즈니스 로직이 있는 service를 호출하여 비즈니스 로직 처리
// 반환할 템플릿을 정의 및 json등으로 응답

@Controller
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //메인페이지
    @GetMapping("/")
    public String List(Model model , @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardDtoList = boardService.getBoardList(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardDtoList);
        model.addAttribute("pageList", pageList);


        return "board/list.html";
    }

    // 작성페이지
    @GetMapping("/post")
    public String write() {
        return "board/write.html";
    }

    // 수정
    @PostMapping("post")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/";
    }

    //상세페이지
    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long id, Model model) {
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("boardDto", boardDto);
        return "board/detail.html";
    }

    //수정 페이지
    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long id ,Model model) {
        BoardDto boardDto = boardService.getPost(id);

        model.addAttribute("boardDto", boardDto);
        return "board/update";
    }

    //수정
    @PutMapping("/post/edit/{no}")
    public String update(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/";
    }

    //삭제
    // pathVariable url에서 변수를 받아 씀 requestparams 느낌으로(node)
    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long id) {
        boardService.deletePost(id);

        return "redirect:/";
    }

    //검색
    // reqbody느낌으로 받아서 사용
    @GetMapping("/board/search")
    public String search(@RequestParam(value= "keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);
        model.addAttribute("boardList",boardDtoList);

        return "board/list.html";
    }

}
