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
-- delete from community_member;


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
insert into test.community_board (member_id, category, title, contents, capacity, status)
values
    ('aaa', 'HEALTHY', '건강해져요', '주 2회 한강 러닝할분', 5, 'CLOSE'),
    ('aaa', 'STUDY', 'Spring 스터디 모집', '주 2회 스프링 공부할 분', 5, 'CLOSE'),
    ('aaa', 'TRIP', '부산 여행 같이 가요', '2박 3일 여행 멤버 모집', 4, 'OPEN');

UPDATE community_board
SET status = 'CLOSED'
WHERE status = 'CLOSE';


select * from community_board;
delete from community_board;

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

INSERT INTO community_group (board_num, member_id, role, status)
VALUES
    (1, 'user01', 'LEADER', 'JOINED'),
    (1, 'user02', 'MEMBER', 'JOINED'),
    (1, 'user03', 'MEMBER', 'PENDING'),
    (2, 'user02', 'LEADER', 'JOINED');

select * from community_group;
