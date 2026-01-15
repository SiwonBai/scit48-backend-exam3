package net.datasa.exam3.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.exam3.dto.MemberDTO;
import net.datasa.exam3.repository.MemberRepository;
import net.datasa.exam3.security.AuthenticatedUser;
import net.datasa.exam3.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberService memberService;

    /**
     * 회원가입 화면으로 이동
     * @return join.html
     */
    @GetMapping("joinForm")
    public String joinForm() {
        return "member/join";
    }

    /**
     * 회원가입 처리
     * @param memberDTO 이름, 비밀번호, 이름, 이메일
     * @return home.html으로 리다이렉트
     */
    @PostMapping("join")
    public String join(MemberDTO memberDTO) {
        log.debug("회원가입: {}", memberDTO);
        memberService.join(memberDTO);
        return "redirect:/";
    }

    /**
     * 로그인 화면으로 이동
     * @return login.html
     */
    @GetMapping("loginForm")
    public String loginForm() {
        return "member/login";
    }

    /**
     * 마이페이지로 이동
     * @param user 로그인 사용자
     * @param model
     * @return mypage.html
     */
    @GetMapping("/mypage")
    public String mypage(
            @AuthenticationPrincipal AuthenticatedUser user,
            Model model) {

        String loginId = user.getUsername();

        model.addAttribute("loginId", loginId);
        model.addAttribute("hostList", memberService.getHostList(loginId));
        model.addAttribute("joinList", memberService.getJoinList(loginId));

        return "member/mypage";
    }
}
