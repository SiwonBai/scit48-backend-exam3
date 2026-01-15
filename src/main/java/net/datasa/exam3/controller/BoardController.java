package net.datasa.exam3.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.exam3.dto.BoardDTO;
import net.datasa.exam3.entity.BoardEntity;
import net.datasa.exam3.security.AuthenticatedUser;
import net.datasa.exam3.service.BoardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
@Controller
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 목록 출력
     * @param category
     * @param model
     * @return
     */
    @GetMapping("list")
    public String boardList(
            @RequestParam(required = false) String category,
            Model model) {

        model.addAttribute("list", boardService.getBoardList(category));
        model.addAttribute("category", category);

        return "board/list";
    }

    /**
     * 게시글 작성
     * @param boardDTO
     * @param user
     * @return
     */
    @PostMapping("write")
    public String write(
            BoardDTO boardDTO,
            @AuthenticationPrincipal AuthenticatedUser user) {

        boardDTO.setMemberId(user.getUsername());
        boardService.write(boardDTO);

        return "redirect:/board/list";
    }

    /**
     * 게시글 작성 화면으로 이동
     * @return 게시글 작성 페이지
     */
    @GetMapping("/write")
    public String writeForm() {
        return "board/write";
    }

    /**
     * 게시글 상세보기
     */
    @GetMapping("/detail")
    public String detail(
            @RequestParam Integer boardNum,
            @AuthenticationPrincipal AuthenticatedUser user,
            Model model) {

        BoardEntity board = boardService.getBoard(boardNum);

        String loginId = user.getUsername();
        boolean isWriter = board.getMember().getMemberId().equals(loginId);
        boolean isApplied = boardService.isAlreadyApplied(boardNum, loginId);

        model.addAttribute("board", board);
        model.addAttribute("isWriter", isWriter);
        model.addAttribute("isApplied", isApplied);

        return "board/detail";
    }

    /**
     * 게시글 신청하기
     */
    @PostMapping("/apply")
    public String apply(
            @RequestParam Integer boardNum,
            @AuthenticationPrincipal AuthenticatedUser user,
            RedirectAttributes ra) {

        boolean applied = boardService.apply(boardNum, user.getUsername());

        if (!applied) {
            ra.addFlashAttribute("dup", true);
        }

        return "redirect:/board/detail?boardNum=" + boardNum;
    }

    /**
     * 게시글 삭제
     * - 작성자만 가능
     */
    @PostMapping("/delete")
    public String delete(
            @RequestParam Integer boardNum,
            @AuthenticationPrincipal AuthenticatedUser user) {

        boardService.delete(boardNum, user.getUsername());
        return "redirect:/board/list";
    }





}
