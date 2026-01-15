package net.datasa.exam3.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.exam3.dto.BoardDTO;
import net.datasa.exam3.entity.BoardEntity;
import net.datasa.exam3.entity.GroupEntity;
import net.datasa.exam3.entity.MemberEntity;
import net.datasa.exam3.repository.BoardRepository;
import net.datasa.exam3.repository.GroupRepository;
import net.datasa.exam3.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    /**
     * 게시판
     * @param category STUDY / HEALTHY / TRIP
     * @return
     */
    public List<BoardEntity> getBoardList(String category) {

        if (category == null || category.equals("ALL")) {
            return boardRepository.findAllByOrderByCreateDateDesc();
        }
        return boardRepository.findByCategoryOrderByCreateDateDesc(category);
    }

    /**
     * 게시글 저장
     * @param dto 게시글 입력 정보
     */
    public void write(BoardDTO dto) {

        MemberEntity member = memberRepository.findById(dto.getMemberId()).orElseThrow();

        BoardEntity board = BoardEntity.builder()
                .category(dto.getCategory())
                .title(dto.getTitle())
                .contents(dto.getContents())
                .capacity(dto.getCapacity())
                .headcnt(0)
                .status("OPEN")
                .member(member)
                .build();

        boardRepository.save(board);

        // LEADER 자동 생성
        GroupEntity leader = GroupEntity.builder()
                .board(board)
                .member(member)
                .role("LEADER")
                .status("JOINED")
                .build();

        groupRepository.save(leader);
    }


    /**
     * 게시글 단건 조회
     */
    public BoardEntity getBoard(Integer boardNum) {
        return boardRepository.findById(boardNum).orElseThrow();
    }

    /**
     * 동일 게시글 중복 신청 여부 확인
     */
    public boolean isAlreadyApplied(Integer boardNum, String memberId) {
        return groupRepository.existsByBoard_BoardNumAndMember_MemberId(boardNum, memberId);
    }

    /**
     * 게시글 신청 처리
     */
    public boolean apply(Integer boardNum, String memberId) {

        BoardEntity board = boardRepository.findById(boardNum).orElseThrow();

        if ("CLOSED".equals(board.getStatus())) return false;
        if (isAlreadyApplied(boardNum, memberId)) return false;

        MemberEntity member = memberRepository.findById(memberId).orElseThrow();

        GroupEntity group = GroupEntity.builder()
                .board(board)
                .member(member)
                .role("MEMBER")
                .status("PENDING")
                .build();

        groupRepository.save(group);
        return true;
    }

    /**
     * 게시글 삭제
     * - 작성자 검증 후 삭제
     */
    public void delete(Integer boardNum, String loginId) {

        BoardEntity board = boardRepository.findById(boardNum).orElseThrow();

        // 작성자 아니면 삭제 불가
        if (!board.getMember().getMemberId().equals(loginId)) {
            return;
        }

        // 그룹 정보 먼저 삭제
        groupRepository.deleteByBoard_BoardNum(boardNum);

        // 게시글 삭제
        boardRepository.delete(board);
    }

}
