package net.datasa.exam3.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.datasa.exam3.entity.BoardEntity;
import net.datasa.exam3.entity.GroupEntity;
import net.datasa.exam3.repository.BoardRepository;
import net.datasa.exam3.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final BoardRepository boardRepository;

    /**
     * 게시글 단건 조회
     */
    public BoardEntity getBoard(Integer boardNum) {
        return boardRepository.findById(boardNum).orElseThrow();
    }


    public List<GroupEntity> getGroupList(Integer boardNum) {
        return groupRepository.findByBoard_BoardNum(boardNum);
    }

    /**
     * 승인 처리
     */
    public void approve(Integer groupId) {

        GroupEntity group = groupRepository.findById(groupId).orElseThrow();
        BoardEntity board = group.getBoard();

        if (!"PENDING".equals(group.getStatus())) return;
        if ("CLOSED".equals(board.getStatus())) return;

        group.setStatus("JOINED");
        board.setHeadcnt(board.getHeadcnt() + 1);

        if (board.getHeadcnt() >= board.getCapacity()) {
            board.setStatus("CLOSED");
        }
    }

    /**
     * 거절 처리
     * @param groupId
     */
    public void reject(Integer groupId) {

        GroupEntity group = groupRepository.findById(groupId).orElseThrow();
        BoardEntity board = group.getBoard();

        if ("JOINED".equals(group.getStatus())) {
            board.setHeadcnt(board.getHeadcnt() - 1);
        }

        group.setStatus("REJECTED");
    }

}
