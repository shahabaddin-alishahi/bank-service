CREATE TABLE IF NOT EXISTS authority
(
    id          bigserial
        constraint authority_pkey
            primary key,
    create_date timestamp,
    description varchar(255),
    title       varchar(255),
    update_date timestamp
);

alter table authority
    owner to postgres;



CREATE INDEX IF NOT EXISTS ix_authority_id on AUTHORITY (ID);

CREATE INDEX IF NOT EXISTS ix_authority_title on AUTHORITY (TITLE);

