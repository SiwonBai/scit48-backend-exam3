package net.datasa.exam3.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.exam3.entity.MemberEntity;
import net.datasa.exam3.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 로그인 시 DB에서 회원 정보 조회
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticatedUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id)
            throws UsernameNotFoundException {

        log.info("로그인 시도 ID = {}", id);

        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException(id + " : 없는 ID"));

        log.info("회원 조회 성공");

        return AuthenticatedUser.builder()
                .id(memberEntity.getMemberId())       // Entity 필드명 주의
                .password(memberEntity.getMemberPw()) // 암호화된 비밀번호
                .name(memberEntity.getName())
                .roleName("ROLE_USER")                // 고정 권한
                .enabled(true)                        // 항상 true
                .build();
    }
}
