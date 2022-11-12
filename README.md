# MyBatis DB연결 세팅

### MariaDB 사용자 생성 및 권한 주기
```sql
CREATE USER 'green'@'%' IDENTIFIED BY 'green1234';
CREATE DATABASE greendb;
GRANT ALL PRIVILEGES ON greendb.* TO 'green'@'%';
```

### 테이블 생성
```sql
USE greendb;



create table user(
   user_id INT primary KEY auto_increment,
   username VARCHAR(20),
   password VARCHAR(20),
   role VARCHAR(10),
   created_at TIMESTAMP
);


create table recommend(
   recommend_id INT primary KEY auto_increment,
   user_id INT,
   subject_id INT,
   created_at TIMESTAMP
);

create table subscribe(
   subscribe_id INT primary KEY auto_increment,
   user_id INT,
   subject_id INT, 
   created_at TIMESTAMP
);

create TABLE person(
   person_id INT primary KEY AUTO_INCREMENT,
   user_id INT,
   person_name VARCHAR(20),
   person_email VARCHAR(50),
   person_phone VARCHAR(20),
   is_gender BOOLEAN,
   address VARCHAR(50),
   degree VARCHAR(20),
   career INT,
   created_at TIMESTAMP
);


create table person_skill(
  person_skill_id INT primary KEY auto_increment,
  person_id INT,
  skill VARCHAR(20),
  created_at TIMESTAMP
);

create table resume(
  resume_id INT primary KEY auto_increment,
  person_id INT,
  resume_title VARCHAR(20),
  photo blob,
  introduction LONGTEXT,
  my_cloud LONGTEXT,
  created_at TIMESTAMP
);


create table company (
   company_id INT primary KEY auto_increment,
   user_id INT,
   company_name VARCHAR(20),
   company_email VARCHAR(50),
   company_phone VARCHAR(20),
   tech VARCHAR(20),
   address LONGTEXT,
   history INT,
   introduction LONGTEXT,
   photo LONGTEXT,
   company_goal LONGTEXT,
   ceo_name VARCHAR(20),
   created_at TIMESTAMP
);

create table notice(
   notice_id INT primary KEY auto_increment,
   company_id INT,
   notice_title VARCHAR(20),
   is_closed boolean,
   salary VARCHAR(20),
   degree VARCHAR(20),
   career INT,
   notice_content LONGTEXT,
   created_at TIMESTAMP
);

create table need_skill(
   need_skill_id INT primary KEY auto_increment,
   notice_id INT,
   skill VARCHAR(20),
   created_at TIMESTAMP
);

create table submit_resume(
  submit_resume_id int primary KEY auto_increment,
  resume_id INT,
  notice_id INT,
  created_at TIMESTAMP
);

```

### 더미데이터 추가
```sql
insert into user(username, password, role, created_at) values('ssar', '1234', 'company', NOW());
insert into user(username, password, role, created_at) values('cos', '1234', 'person', NOW());

 
insert into recommend(user_id, subject_id, created_at) VALUES('1', '2', NOW());
 
insert into subscribe(user_id, subject_id, created_at) VALUES('2', '3', NOW());


insert into person(user_id, person_name, person_email, person_phone, is_gender, address,degree, career, created_at)
values('1', '홍길동', 'ssar@nate.com', '01000000000', 0 , '부산광역시' , '4년제', '1', NOW());

INSERT into person_skill(person_id, skill, created_at) VALUES('1', '자바', NOW());

insert into resume(person_id , resume_title, photo , introduction , my_cloud, created_at) 
VALUES('2', '멋진이력서', '사진값', '안녕하세요',"www.naver.com", NOW());

insert into company (user_id, company_name , company_email , company_phone ,tech , address , history ,  introduction ,photo,company_goal, ceo_name, created_at) 
VALUES('2', '그린회사', 'cos@nate.com', '01000000000', 'C언어', '부산','2016','안녕하세요','사진값','주식상장이 목표','호날두', NOW());

insert into notice(company_id , notice_title, is_closed , salary ,degree, career, created_at) 
VALUES('2','개발자 모셔갑니다',0, '3천만원','4년제', 10 ,  NOW());

insert into need_skill(notice_id , skill , created_at) VALUES('2', '자바스크립트', NOW());

```

## Tip
#### MariaDB auto commit 설정-해제 하기
```sql
show variables like 'autocommit%'; ### 현재 상태 확인
SET AUTOCOMMIT = TRUE;  ### 설정
SET AUTOCOMMIT = FALSE; ### 해제
```

