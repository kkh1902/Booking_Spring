package com.example.springCrud.service;


import com.example.springCrud.domain.entity.Board;
import com.example.springCrud.domain.repository.BoardRepository;
import com.example.springCrud.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Service
// 비즈니스 로직을 구현
// 데이터 처리(모델)을 담당하는 repository에서 데이터를 가져와서 controller에 넘겨주거나
// 비즈니스 로직을 처리
@Service
public class BoardService {


    private BoardRepository boardRepository;
    private static final  int BLOCK_PAGE_NUM_COUNT = 5;  // 블럭에 존재하는 페이지 수
    private static final  int PAGE_POST_COUNT = 4;  // 한 페이지에 존재하는 게시글 수


    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 저장하기
    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    // 게시글 정보 조회
    @Transactional
    public List<BoardDto> getBoardList(Integer pageNum) {

        Page<Board> page = boardRepository
                .findAll(PageRequest
                        .of(pageNum-1, PAGE_POST_COUNT,Sort.by(Sort.Direction.ASC, "createDate")));


//        List<Board> boards = boardRepository.findAll();
        List<Board> boards = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (Board board : boards) {
            boardDtoList.add(this.convertEntityToDto(board));
        }

        return boardDtoList;
    }

    // 게시글 등록
    @Transactional
    public BoardDto getPost(Long id) {
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();

        BoardDto boardDto = BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createdDate(board.getCreateDate())
                .build();

        return boardDto;
    }

//    삭제
    @Transactional
    public void deletePost(Long id){
        boardRepository.deleteById(id);
    }

//    검색
    @Transactional
    public List<BoardDto> searchPosts(String keyword) {
        List<Board> boards = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if(boards.isEmpty()) return  boardDtoList;

        for (Board board : boards) {
            boardDtoList.add(this.convertEntityToDto(board));
        }

        return boardDtoList;
    }

    private  BoardDto convertEntityToDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createdDate(board.getCreateDate())
                .build();
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

        // 총 게시글 수를 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        //페이지 시작 번호 조정
        curPageNum = (curPageNum<=3) ? 1 : curPageNum-2;

        //페이지 번호 할당
        for (int val=curPageNum, i=0;val<=blockLastPageNum;val++, i++){
            pageList[i] = val;
        }

        return pageList;
    }

    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }



}
