create table users
(
    id       integer not null
        primary key,
    password varchar(255),
    role     varchar(255)
        constraint users_role_check
            check ((role)::text = 'USER'::text),
    username varchar(255)
);

alter table users
    owner to admin;

create table pastes
(
    user_id         integer
        constraint fkiw0m7hqavaalcxl8m5ijfp8o4
            references users,
    expiration_time timestamp(6),
    content         varchar(255),
    hash            varchar(255) not null
        primary key,
    title           varchar(255),
    type_paste      varchar(255)
        constraint pastes_type_paste_check
            check ((type_paste)::text = ANY
        ((ARRAY ['PUBLIC'::character varying, 'UNLISTED'::character varying])::text[]))
    );

alter table pastes
    owner to admin;

create table refresh_token
(
    id          serial
        primary key,
    user_id     integer
        unique
        constraint fkjtx87i0jvq2svedphegvdwcuy
            references users,
    expiry_date timestamp(6),
    token       varchar(255)
);

alter table refresh_token
    owner to admin;

