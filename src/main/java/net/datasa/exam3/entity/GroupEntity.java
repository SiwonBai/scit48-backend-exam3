package net.datasa.exam3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Table(name = "community_group")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;

    @ManyToOne
    @JoinColumn(name = "board_num")
    private BoardEntity board;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column
    private String role = "MEMBER";

    @Column
    private String status = "PENDING";

    @CreationTimestamp
    private LocalDateTime joinedDate;
}
