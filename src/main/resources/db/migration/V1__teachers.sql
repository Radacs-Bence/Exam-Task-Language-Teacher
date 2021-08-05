create table teachers (
                          id bigint not null auto_increment,
                          address varchar(255),
                          e_mail varchar(255),
                          phone varchar(255),
                          name varchar(255),
                          primary key (id)
) engine=InnoDB;