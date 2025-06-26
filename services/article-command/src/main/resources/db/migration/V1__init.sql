CREATE TABLE folder (
        id BIGINT PRIMARY KEY,
        name TEXT NOT NULL,
        owner_id BIGINT NOT NULL,
        parent_id BIGINT,
        full_path TEXT NOT NULL UNIQUE,

        CONSTRAINT fk_folder_parent
            FOREIGN KEY (parent_id) REFERENCES folder(id)
                ON DELETE SET NULL,
        UNIQUE (parent_id, name)
);

CREATE TABLE article_metadata (
     id BIGINT PRIMARY KEY,
     folder_id BIGINT NOT NULL,
     slug TEXT,
     author_id BIGINT NOT NULL,
     is_public BOOLEAN NOT NULL ,
     is_published BOOLEAN NOT NULL,
     created_at TIMESTAMP(3) NOT NULL ,
     summary TEXT NOT NULL DEFAULT '',
     thumbnail TEXT,

     CONSTRAINT fk_article_metadata_folder FOREIGN KEY (folder_id) REFERENCES folder(id) ON DELETE RESTRICT,

    UNIQUE (folder_id, slug)
);

CREATE TABLE version (
      id BIGINT PRIMARY KEY,
      article_id BIGINT NOT NULL,
      parent_id BIGINT,
      title TEXT,
      diff TEXT NOT NULL,
      created_at TIMESTAMP(3) NOT NULL,
      state VARCHAR(50) NOT NULL,

      CONSTRAINT fk_version_article FOREIGN KEY (article_id) REFERENCES article_metadata(id),
      CONSTRAINT fk_version_prev_version FOREIGN KEY (parent_id) REFERENCES version(id)
);

/** 각 글에는 PUBLISHED 버전이 유일해야 함 **/
CREATE UNIQUE INDEX unique_published_per_article
ON version(article_id)
WHERE state = 'PUBLISHED';

CREATE TABLE outbox_event (
    id BIGINT PRIMARY KEY,
    event_key TEXT NOT NULL,
    event_type TEXT NOT NULL ,
    payload_json JSONB NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP(3) NOT NULL,
    retry_count SMALLINT,
    service_name TEXT NOT NULL
)