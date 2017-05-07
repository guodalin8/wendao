
create database wendao character set utf8;

use wendao;

 create table t_user (
	     id int(10) primary key auto_increment ,
	     username varchar(20) not null unique ,
	     password varchar(18) not null 
 ) ;
 
 create table  t_topic (
      id  int(10) primary key auto_increment , 
      title varchar( 255 ) , 
      content  text ,
      publish_time timestamp ,
      publish_ip varchar(40) ,
      user_id int(10) ,
      foreign key ( user_id ) references t_user ( id )
 );
 
  create table  t_explain (
      id  int(10) primary key auto_increment , 
      content  text ,
      explain_time timestamp ,
      explain_ip varchar(40) ,
      user_id int(10) ,
      foreign key ( user_id ) references t_user ( id ) ,
      topic_id int(10) ,
      foreign key ( topic_id ) references t_topic ( id )
 );
 
