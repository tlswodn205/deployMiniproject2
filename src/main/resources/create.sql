create TABLE users(
   user_id INT primary KEY auto_increment,
   username VARCHAR
(20),
   password VARCHAR
(20),
   role VARCHAR
(10),
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
   person_name VARCHAR
(20),
   person_email VARCHAR
(50),
   person_phone VARCHAR
(20),
   is_gender BOOLEAN,
   address VARCHAR
(50),
   degree VARCHAR
(20),
   career INT,
   created_at TIMESTAMP
);


create table person_skill(
  person_skill_id INT primary KEY auto_increment,
  person_id INT,
  skill VARCHAR
(20),
  created_at TIMESTAMP
);

create table resume(
  resume_id INT primary KEY auto_increment,
  person_id INT,
  resume_title VARCHAR
(20),
  photo blob,
  introduction LONGTEXT,
  my_cloud LONGTEXT,
  created_at TIMESTAMP
);


create table company (
   company_id INT primary KEY auto_increment,
   user_id INT,
   company_name VARCHAR
(20),
   company_email VARCHAR
(50),
   company_phone VARCHAR
(20),
   tech VARCHAR
(20),
   address LONGTEXT,
   history INT,
   introduction LONGTEXT,
   photo LONGTEXT,
   company_goal LONGTEXT,
   ceo_name VARCHAR
(20),
   created_at TIMESTAMP
);

create table notice(
   notice_id INT primary KEY auto_increment,
   company_id INT,
   notice_title VARCHAR
(20),
   is_closed boolean,
   salary VARCHAR
(20),
   degree VARCHAR
(20),
   notice_content LONGTEXT,
   career INT,
   created_at TIMESTAMP
);

create table need_skill(
   need_skill_id INT primary KEY auto_increment,
   notice_id INT,
   skill VARCHAR
(20),
   created_at TIMESTAMP
);

create table submit_resume(
  submit_resume_id int primary KEY auto_increment,
  resume_id INT,
  notice_id INT,
  created_at TIMESTAMP
);


insert into users
   (username, password, role, created_at)
values('ssar', '1234', 'person', NOW());
insert into users
   (username, password, role, created_at)
values('cos', '1234', 'person', NOW());
insert into users
   (username, password, role, created_at)
values('sin', '1234', 'person', NOW());
insert into users
   (username, password, role, created_at)
values('tan', '1234', 'person', NOW());
insert into users
   (username, password, role, created_at)
values('akr', '1234', 'person', NOW());
insert into users
   (username, password, role, created_at)
values('arl', '1234', 'person', NOW());
insert into users
   (username, password, role, created_at)
values('ire', '1234', 'company', NOW());
insert into users
   (username, password, role, created_at)
values('pac', '1234', 'company', NOW());
insert into users
   (username, password, role, created_at)
values('hept', '1234', 'company', NOW());
insert into users
   (username, password, role, created_at)
values('ppc', '1234', 'company', NOW());
insert into users
   (username, password, role, created_at)
values('empc', '1234', 'company', NOW());
insert into users
   (username, password, role, created_at)
VALUES('psg', '1234', 'company', NOW());

insert into recommend
   (user_id, subject_id, created_at)
VALUES('6', '7', NOW());

insert into subscribe
   (user_id, subject_id, created_at)
VALUES('4', '7', NOW());


insert into person
   (user_id, person_name, person_email, person_phone, is_gender, address,degree, career, created_at)
values('1', '주시윤', 'ssar@nate.com', '01000000000', 0 , '부산광역시' , '대졸 전공', '1', NOW());
insert into person
   (user_id, person_name, person_email, person_phone, is_gender, address,degree, career, created_at)
VALUES('2', '김초원', 'cos@nate.com', '01000000000', 1 , '서울특별시' , '대졸 전공', '10', NOW());
insert into person
   (user_id, person_name, person_email, person_phone, is_gender, address,degree, career, created_at)
VALUES('3', '강소영', 'sin@nate.com', '01000000000', 1 , '제주특별자치도' , '대졸 비전공', '5', NOW());
insert into person
   (user_id, person_name, person_email, person_phone, is_gender, address,degree, career, created_at)
VALUES('4', '이유리', 'tan@nate.com', '01000000000', 1 , '강원도 삼척시' , '고졸', '7', NOW());
insert into person
   (user_id, person_name, person_email, person_phone, is_gender, address,degree, career, created_at)
VALUES('5', '유미나', 'akr@nate.com', '01000000000', 1 , '제주특별자치도' , '대졸 비전공', '6', NOW());
insert into person
   (user_id, person_name, person_email, person_phone, is_gender, address,degree, career, created_at)
VALUES('6', '이유미', 'arl@nate.com', '01000000000', 1 , '경기도 하남시' , '대졸 비전공', '3', NOW());

INSERT into person_skill
   (person_id, skill, created_at)
VALUES('6', 'java', NOW());

INSERT into person_skill
   (person_id, skill, created_at)
VALUES('2', 'javaScript', NOW());

INSERT into person_skill
   (person_id, skill, created_at)
VALUES('2', 'HTML/CSS', NOW());

INSERT into person_skill
   (person_id, skill, created_at)
VALUES('4', 'MySQL', NOW());
INSERT into person_skill
   (person_id, skill, created_at)
VALUES('6', 'AWS', NOW());
INSERT into person_skill
   (person_id, skill, created_at)
VALUES('6', 'Flutter', NOW());

INSERT into person_skill
   (person_id, skill, created_at)
VALUES('1', 'AWS', NOW());

INSERT into person_skill
   (person_id, skill, created_at)
VALUES('1', 'Flutter', NOW());

INSERT into person_skill
   (person_id, skill, created_at)
VALUES('1', 'java', NOW());

insert into resume
   (person_id , resume_title, photo , introduction , my_cloud, created_at)
VALUES('1', '프론트엔드용이력서', '사진값', '안녕하세요', 'www.naver.com', NOW());

insert into resume
   (person_id , resume_title, photo , introduction , my_cloud, created_at)
VALUES('1', '백엔드용이력서', '사진값', '안녕하세요', 'www.naver.com', NOW());


insert into company
   (user_id, company_name , company_email , company_phone ,tech , address , history , introduction ,photo,company_goal, ceo_name, created_at)
VALUES('7', '삼성', 'ire@nate.com', '01000000000', '메타버스', '제주특별자치도', '2016', '안녕하세요', NULL, null , '이재용', NOW());

insert into company
   (user_id, company_name , company_email , company_phone ,tech , address , history , introduction ,photo,company_goal, ceo_name, created_at)
VALUES('8', 'LG', 'pac@nate.com', '01000000000', 'AI', '제주특별자치도', '2016', '안녕하세요', NULL, NULL , '구광모', NOW());

insert into company
   (user_id, company_name , company_email , company_phone ,tech , address , history , introduction ,photo,company_goal, ceo_name, created_at)
VALUES('9', 'SK', 'hept@nate.com', '01000000000', 'AI', '서울특별시도', '2016', '안녕하세요', NULL, null, '최태원', NOW());

insert into company
   (user_id, company_name , company_email , company_phone ,tech , address , history , introduction ,photo,company_goal, ceo_name, created_at)
VALUES('10', '소프트뱅크', 'hept@nate.com', '01000000000', 'AI', '서울특별시도', '2016', '안녕하세요', NULL, null, '손정의', NOW());

insert into company
   (user_id, company_name , company_email , company_phone ,tech , address , history , introduction ,photo,company_goal, ceo_name, created_at)
VALUES('11', 'SK', 'hept@nate.com', '01000000000', 'AI', '서울특별시도', '2016', '안녕하세요', NULL, null, '이나바', NOW());

insert into company
   (user_id, company_name , company_email , company_phone ,tech , address , history , introduction ,photo,company_goal, ceo_name, created_at)
VALUES('12', '코핀', 'ire@nate.com', '01000000000', '메타버스', '제주특별자치도', '2016', '안녕하세요', NULL, null, '관리자', NOW());

UPDATE company SET company_goal = '세계를 선도하는 기업' WHERE company_Id =1;

insert into notice
   (company_id , notice_title, is_closed , salary ,degree, career, created_at)
VALUES('1', 'DB구축 가능하신분 구합니다. ', 0, '1억원', '대졸 전공', 5 , NOW());

insert into notice
   (company_id , notice_title, is_closed , salary ,degree, career, created_at)
VALUES('1', '웹 서비스 만드실분 구합니다.', 0, '1억원', '대졸 비전공', 10 , NOW());

insert into notice
   (company_id , notice_title, is_closed , salary ,degree, career, created_at)
VALUES('3', 'DB구축 가능하신분 구합니다', 0, '1억원', '대졸전공', 2 , NOW());

insert into notice
   (company_id , notice_title, is_closed , salary ,degree, career, created_at)
VALUES('4', '', 0, '1억원', '대졸전공', 3 , NOW());

insert into notice
   (company_id , notice_title, is_closed , salary ,degree, career, created_at)
VALUES('5', '침식체 잡을 카운터구함', 0, '6천만원', '대졸비전공', 3 , NOW());

insert into notice
   (company_id , notice_title, is_closed , salary ,degree, career, created_at)
VALUES('11', '침식체 잡을 카운터구함', 0, '3천만원', '대졸비전공', 5 , NOW());


insert into notice
   (company_id , notice_title, is_closed , salary ,degree, career, created_at)
VALUES('12', '침식체 잡을 카운터구함', 6, '3천만원', '대졸비전공', 7 , NOW());

INSERT INTO recommend
   (user_id, subject_id, created_at)
VALUES(1, 4 , NOW());
INSERT INTO recommend
   (user_id, subject_id, created_at)
VALUES(7, 1 , NOW());
INSERT INTO recommend
   (user_id, subject_id, created_at)
VALUES(7, 4 , NOW());

INSERT into need_skill
   (notice_id, skill, created_at)
VALUES('1', 'javaScript', NOW());
INSERT into need_skill
   (notice_id, skill, created_at)
VALUES('1', 'HTML/CSS', NOW());
insert into need_skill
   (notice_id , skill , created_at)
VALUES('1', 'JAVA', NOW());
insert into need_skill
   (notice_id , skill , created_at)
VALUES('2', 'java', NOW());
insert into need_skill
   (notice_id , skill , created_at)
VALUES('2', 'JavaScript', NOW());
insert into need_skill
   (notice_id , skill , created_at)
VALUES('4', 'JavaScr	ipt', NOW());
INSERT into need_skill
   (notice_id, skill, created_at)
VALUES('6', 'MySQL', NOW());
INSERT into need_skill
   (notice_id, skill, created_at)
VALUES('6', 'AWS', NOW());
INSERT into need_skill
   (notice_id, skill, created_at)
VALUES('5', 'Flutter', NOW());
INSERT INTO subscribe
   (user_id, subject_id, created_At)
VALUES(1,
      5,
      NOW()
);

UPDATE company SET introduction = '알파트릭스는 혁신을 이끄는 기업입니다.' WHERE company_Id = 2;

INSERT INTO resume
   (person_id, resume_title, photo, introduction, my_cloud, created_At )
VALUES(2,
      '재능충지아링',
      NULL,
      '지아링링',
      'www.naver.com',
      NOW()
);

INSERT into person_skill
   (person_id, skill, created_at)
VALUES('4', 'JAVA', NOW());


INSERT INTO submit_resume
   (resume_id, notice_id, created_at)
VALUES(3, 1, NOW());
INSERT INTO submit_resume
   (resume_id, notice_id, created_at)
VALUES(1, 2, NOW());
