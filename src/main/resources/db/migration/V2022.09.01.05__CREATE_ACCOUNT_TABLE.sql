CREATE TABLE IF NOT EXISTS account
(
    id                bigserial
        constraint account_pkey
            primary key,
    balance           double precision,
    is_enabled        boolean not null,
    registration_date timestamp,
    update_date       timestamp,
    customer_id       bigint
        constraint fk_customer_id_account
            references customer
);

alter table account
    owner to postgres;


CREATE INDEX IF NOT EXISTS ix_account_id on account (ID);