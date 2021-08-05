create table timetables (
                            id bigint not null auto_increment,
                            day integer,
                            length_in_min integer,
                            start_time time,
                            course_id bigint,
                            primary key (id)
) engine=InnoDB;