-- exam3
create database test;
use test;
-- community_member: 회원정보를 저장하기 위한 테이블
CREATE TABLE community_member
(
      member_id VARCHAR(20)     PRIMARY KEY,
      member_pw VARCHAR(100)    NOT NULL,
      name      VARCHAR(20)     NOT NULL,
      email     VARCHAR(200)
);

select * from community_member;

-- community_board: 모집 글을 저장하기 위한 테이블
CREATE TABLE community_board (
     board_num  INT AUTO_INCREMENT PRIMARY KEY,
     member_id  VARCHAR(20),
     category   VARCHAR(50) NOT NULL,
     title      VARCHAR(200) NOT NULL,
     contents   VARCHAR(2000) NOT NULL,
     headcnt    INT DEFAULT 0,
     capacity   INT DEFAULT 5,
     status     VARCHAR(100) DEFAULT 'OPEN',
     create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

     CONSTRAINT fk_board_member
         FOREIGN KEY (member_id)
             REFERENCES community_member(member_id)
             ON DELETE SET NULL
);

-- community_group : 모임 정보를 저장할 테이블
CREATE TABLE community_group
(
     group_id INT AUTO_INCREMENT PRIMARY KEY,
     board_num INT,
     member_id VARCHAR(20),
     role VARCHAR(20) DEFAULT 'MEMBER',
     status VARCHAR(20) DEFAULT 'PENDING',
     joined_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

     CONSTRAINT fk_group_board FOREIGN KEY (board_num) REFERENCES community_board(board_num) ON DELETE CASCADE,
     CONSTRAINT fk_group_member FOREIGN KEY (member_id) REFERENCES community_member(member_id) ON DELETE CASCADE
);

delete from community_board;
