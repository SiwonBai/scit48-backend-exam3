package net.datasa.exam3.repository;

import net.datasa.exam3.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {

    boolean existsByBoard_BoardNumAndMember_MemberId(Integer boardNum, String memberId);

    List<GroupEntity> findByBoard_BoardNum(Integer boardNum);

    void deleteByBoard_BoardNum(Integer boardNum);

    // 내가 주최한 모임
    List<GroupEntity> findByMember_MemberIdAndRole(
            String memberId, String role
    );

    // 참여한 모임: MEMBER + JOINED 만
    List<GroupEntity> findByMember_MemberIdAndRoleAndStatus(
            String memberId,
            String role,
            String status
    );



}


