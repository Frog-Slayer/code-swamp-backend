CREATE TABLE outbox_event (
    id BIGINT PRIMARY KEY,
    event_key TEXT NOT NULL,
    event_type TEXT NOT NULL ,
    payload_json JSONB NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP(3) NOT NULL,
    retry_count SMALLINT,
    service_name TEXT NOT NULL
);