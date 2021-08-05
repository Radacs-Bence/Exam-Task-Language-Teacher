alter table languages
    add constraint FK8838qb92h7nq3ef5o8mb8al21
        foreign key (teach_id)
            references teachers (id);