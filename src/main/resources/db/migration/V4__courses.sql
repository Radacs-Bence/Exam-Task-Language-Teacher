create table courses (
                         id bigint not null auto_increment,
                         end date,
                         language varchar(255),
                         name varchar(255),
                         start date,
                         teacher_id bigint,
                         primary key (id)
) engine=InnoDB