package net.datasa.exam3.dto;

import lombok.Data;

/**
 * 게시글 작성용
 */
@Data
public class BoardDTO {

    private String category;
    private String title;
    private String contents;
    private Integer capacity;

    // 로그인 사용자 ID (컨트롤러에서 세팅)
    private String memberId;
}
