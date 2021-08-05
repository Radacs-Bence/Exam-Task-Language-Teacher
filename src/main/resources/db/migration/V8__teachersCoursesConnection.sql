alter table courses
    add constraint FK468oyt88pgk2a0cxrvxygadqg
        foreign key (teacher_id)
            references teachers (id);