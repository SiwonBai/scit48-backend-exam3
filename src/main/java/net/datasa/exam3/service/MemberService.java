package net.datasa.exam3.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.exam3.dto.MemberDTO;
import net.datasa.exam3.entity.GroupEntity;
import net.datasa.exam3.entity.MemberEntity;
import net.datasa.exam3.repository.GroupRepository;
import net.datasa.exam3.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final GroupRepository groupRepository;

    /**
     * 회원가입 처리
     * @param dto 아이디, 비번, 이름, 이메일
     */
    public void join(MemberDTO dto) {

        MemberEntity entity = MemberEntity.builder()
                .memberId(dto.getId())
                .memberPw(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .email(dto.getEmail())
                .build();

        memberRepository.save(entity);
    }

    // 주최한 모임
    public List<GroupEntity> getHostList(String memberId) {
        return groupRepository
                .findByMember_MemberIdAndRole(memberId, "LEADER");
    }

    // 참여한 모임 (승인된 JOINED만)
    public List<GroupEntity> getJoinList(String memberId) {
        return groupRepository.findByMember_MemberIdAndRoleAndStatus(
                memberId,
                "MEMBER",
                "JOINED"
        );
    }



}
