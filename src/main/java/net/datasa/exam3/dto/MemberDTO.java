package net.datasa.exam3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String id;        // 로그인 ID
    private String password;  // 비밀번호
    private String name;      // 이름
    private String email;     // 이메일
}

