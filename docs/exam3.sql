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
-- 암호화 처리된 멤버 정보 회원가입 완료

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

select * from community_board;
-- 보드 더미 데이터
insert into community_board (member_id, category, title, contents, capacity)
values
    ('aaa', 'HEALTHY',  '건강을 위해 운동할 사람들', '건강', 5),
    ('ccc', 'TRIP', '세계여행 인원 모집', '세계여행', 4),
    ('ccc', 'HEALTHY', '헛둘헛둘 한강러닝', '헛둘', 10),
    ('aaa', 'STUDY', '카공 인원 모집', '신촌역 5시', 4);


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

select * from community_group;
-- 그룹 더미 데이터
insert into community_group (board_num, member_id, role, status)
values
    (1, 'aaa', 'LEADER', 'JOINED'),
    (2, 'ccc', 'LEADER', 'JOINED'),
    (2, 'ccc', 'LEADER', 'JOINED'),
    (1, 'aaa', 'LEADER', 'JOINED'),
    (4, 'ddd', 'MEMBER', 'JOINED');

