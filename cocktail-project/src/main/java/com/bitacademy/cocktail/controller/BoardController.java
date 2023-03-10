package com.bitacademy.cocktail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bitacademy.cocktail.domain.Board;
import com.bitacademy.cocktail.domain.BoardImage;
import com.bitacademy.cocktail.domain.ReviewBoard;
import com.bitacademy.cocktail.repository.MemberRepository;
import com.bitacademy.cocktail.service.BoardImageService;
import com.bitacademy.cocktail.service.BoardService;
import com.bitacademy.cocktail.service.ReviewBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardController {

	@Autowired
	BoardService boardService;

	@Autowired
	ReviewBoardService reviewBoardService;

	@Autowired
	BoardImageService boardImageService;
	
	MemberRepository memberRepository;

//	게시글 리스트
	@GetMapping("/board/list")
	public List<Board> boardList(Model model) {
		List<Board> boardList = boardService.boardList();
		model.addAttribute("boardList", boardList);
		return boardList;
	}

//	게시글 작성
	@PostMapping("/board/write")
	public void boardWrite(Board board, BoardImage boardImage,
						List<MultipartFile> files) throws Exception {
		System.out.println("board = " + board);
		board.setHit(0L);
		System.out.println("*****************" + SecurityContextHolder.getContext().getAuthentication());
		boardService.boardWrite(board);
		System.out.println(!files.isEmpty());
		if (!files.isEmpty()) {
			for (MultipartFile file : files) {
				boardImageService.saveFile(board, boardImage, file);
			}
		}
	}

//	게시글 보기
	@GetMapping("/board/view/{no}")
	public Board boardView(Model model, @PathVariable("no") Long no) {

		model.addAttribute("boardList", boardService.boardView(no));
		return boardService.boardView(no);
	}

//	댓글쓰기
	@PostMapping("/board/view/{no}/review/write")
	public void reviewWrite(@PathVariable("no") Long no, ReviewBoard reviewBoard) {
		reviewBoard.setNo(null);
		reviewBoardService.reviewWrite(no, reviewBoard);
	}

//	댓글삭제
	@GetMapping("/board/view/{no}/review/delete/{bno}")
	public void reivewDelete(@PathVariable("no") Long no, @PathVariable("bno") Long bno, ReviewBoard reviewBoard) {
		reviewBoardService.reviewDelete(bno);
	}

//	게시글 수정
	@PostMapping("/board/update/{no}")
	public void boardUpdate(@PathVariable("no") Long no, Board board, BoardImage boardImage, List<MultipartFile> files)
			throws Exception {
		Board boardTemp = boardService.boardView(no);
		boardTemp.setCategory(board.getCategory());
		boardTemp.setTitle(board.getTitle());
		boardTemp.setContents(board.getContents());
		boardTemp.setImgs(board.getImgs());
		boardService.boardWrite(boardTemp);

		if (!files.isEmpty()) {
			for (MultipartFile file : files) {
				boardImageService.saveFile(board, boardImage, file);
			}
		}
	}

//	게시글삭제
	@GetMapping("/board/delete/{no}")
	public void boardDelete(@PathVariable("no") Long no) {

		boardService.boardDelete(no);
	}

//	이미지 삭제
	@GetMapping("/board/{no}/img/delete/{bno}")
	public void imgDelete(@PathVariable("no") Long no, @PathVariable("bno") Long bno) {
		boardImageService.imgDelete(bno);
	}
}
