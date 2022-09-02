ALTER TABLE account
    ADD COLUMN failure_retry_count integer ;

ALTER TABLE account
ALTER COLUMN failure_retry_count SET NOT NULL;
