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

