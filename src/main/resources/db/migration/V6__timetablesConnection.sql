alter table timetables
    add constraint FKtm617j76vqdyqq14nnwwmpyuf
        foreign key (course_id)
            references courses (id);