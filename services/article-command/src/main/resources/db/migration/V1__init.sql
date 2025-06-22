CREATE TABLE folder (
        id BIGINT PRIMARY KEY,
        name TEXT NOT NULL,
        owner_id BIGINT NOT NULL,
        parent_id BIGINT,
        full_path TEXT NOT NULL UNIQUE,

        CONSTRAINT fk_folder_parent
            FOREIGN KEY (parent_id) REFERENCES folder(id)
                ON DELETE SET NULL
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

     UNIQUE (folder_id, slug),
     CONSTRAINT fk_article_metadata_folder FOREIGN KEY (folder_id) REFERENCES folder(id) ON DELETE RESTRICT
);

CREATE TABLE version (
      id BIGINT PRIMARY KEY,
      article_id BIGINT NOT NULL,
      prev_version_id BIGINT,
      title TEXT,
      diff TEXT NOT NULL,
      created_at TIMESTAMP(3) NOT NULL,
      state VARCHAR(50) NOT NULL,
      is_base BOOLEAN NOT NULL,

      CONSTRAINT fk_versions_article FOREIGN KEY (article_id) REFERENCES article_metadata(id),
      CONSTRAINT fk_versions_prev_version FOREIGN KEY (prev_version_id) REFERENCES version(id)
);

CREATE TABLE base_version (
      version_id BIGINT PRIMARY KEY REFERENCES version(id),
      content TEXT NOT NULL
);

CREATE TABLE article_outbox (
    id BIGINT PRIMARY KEY,
    event_type TEXT NOT NULL ,
    payload_json JSONB NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP(3) NOT NULL
)
