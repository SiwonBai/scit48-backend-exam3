package net.datasa.exam3.controller;

import lombok.RequiredArgsConstructor;
import net.datasa.exam3.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    /**
     * 모집 현황 조회 (게시글 작성자만 접근)
     */
    @GetMapping("/check")
    public String check(
            @RequestParam Integer boardNum,
            Model model) {

        model.addAttribute("board", groupService.getBoard(boardNum));
        model.addAttribute("list", groupService.getGroupList(boardNum));

        return "group/check";
    }

    /**
     * 승인 처리
     */
    @PostMapping("/approve")
    public String approve(
            @RequestParam Integer groupId,
            @RequestParam Integer boardNum) {

        groupService.approve(groupId);
        return "redirect:/group/check?boardNum=" + boardNum;
    }

    /**
     * 거절 처리
     */
    @PostMapping("/reject")
    public String reject(
            @RequestParam Integer groupId,
            @RequestParam Integer boardNum) {

        groupService.reject(groupId);
        return "redirect:/group/check?boardNum=" + boardNum;
    }
}
