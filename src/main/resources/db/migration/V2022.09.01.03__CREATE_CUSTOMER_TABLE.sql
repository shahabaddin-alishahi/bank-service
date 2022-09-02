CREATE TABLE IF NOT EXISTS customer
(
    id                   bigserial
        constraint customer_pkey
            primary key,
    create_date          timestamp,
    finger_print         varchar(255),
    finger_print_enabled boolean not null,
    first_name           varchar(255),
    is_active            boolean not null,
    last_name            varchar(255),
    mobile_number        varchar(255),
    national_code        varchar(255),
    update_date          timestamp
);

alter table customer
    owner to postgres;

CREATE INDEX IF NOT EXISTS ix_costumer_id on customer (ID);
