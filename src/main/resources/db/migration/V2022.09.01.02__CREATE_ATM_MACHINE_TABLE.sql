CREATE TABLE IF NOT EXISTS atm_machine
(
    id          bigserial
        constraint atm_machine_pkey
            primary key,
    atm_model   varchar(255),
    create_date timestamp,
    ip_address  varchar(255),
    is_enabled  boolean,
    password    varchar(255),
    update_date timestamp,
    username    varchar(255)
        constraint uk_username_atm_machine
            unique
);

alter table atm_machine
    owner to postgres;

CREATE INDEX IF NOT EXISTS ix_atm_machine_id on ATM_MACHINE (ID);

CREATE INDEX IF NOT EXISTS ix_atm_machine_username on ATM_MACHINE (USERNAME);