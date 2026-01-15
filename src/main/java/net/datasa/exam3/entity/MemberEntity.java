package net.datasa.exam3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "community_member")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class MemberEntity {

    @Id
    @Column(length = 20)
    private String memberId;

    @Column(nullable = false, length = 100)
    private String memberPw;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 200)
    private String email;

}
