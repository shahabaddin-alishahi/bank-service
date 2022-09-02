
CREATE TABLE IF NOT EXISTS transaction
(
    id                      bigserial
        constraint transaction_pkey
            primary key,
    amount                  double precision,
    create_date             timestamp,
    destination_card_number varchar(255),
    request_type            varchar(255),
    source_card_number      varchar(255),
    status_type             varchar(255),
    transaction_key         varchar(255),
    card_id                 bigint
        constraint fk_card_id_transaction
            references card
);

alter table transaction
    owner to postgres;


CREATE INDEX IF NOT EXISTS ix_transaction_id on transaction (ID);
