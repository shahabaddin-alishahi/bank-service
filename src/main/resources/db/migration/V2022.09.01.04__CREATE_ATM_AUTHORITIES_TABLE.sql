CREATE TABLE IF NOT EXISTS atm_authorities
(
    ATM_MACHINE_ID  bigint not null
        constraint fk_atm_machine_id_atm_authorities
            references atm_machine,
    authority_id bigint not null
        constraint fk_authority_id_atm_authorities
            references authority,
    constraint atm_authorities_pkey
        primary key (ATM_MACHINE_ID, authority_id)
);

alter table atm_authorities
    owner to postgres;

