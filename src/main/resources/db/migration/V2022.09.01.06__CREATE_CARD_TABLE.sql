CREATE TABLE IF NOT EXISTS card
(
    id                  bigserial
        constraint card_pkey
            primary key,
    blocked             boolean not null,
    card_number         varchar(255),
    create_date         timestamp,
    cvv2                integer not null,
    expire_date         integer not null,
    failure_retry_count integer not null,
    is_enabled          boolean not null,
    pin1                varchar(255),
    pin2                varchar(255),
    suspended           boolean not null,
    update_date         timestamp,
    account_id          bigint
        constraint fk_account_id_card
            references account,
    customer_id         bigint
        constraint fk_customer_id_customer
            references customer
);

alter table card
    owner to postgres;



CREATE INDEX IF NOT EXISTS ix_card_id on card (ID);

