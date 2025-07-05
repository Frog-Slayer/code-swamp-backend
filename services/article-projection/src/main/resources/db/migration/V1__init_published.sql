CREATE TABLE published_articles
(
    id  BIGINT PRIMARY KEY,
    version_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    folder_id BIGINT NOT NULL,
    created_at TIMESTAMP(3) NOT NULL,
    updated_at TIMESTAMP(3) NOT NULL,
    summary TEXT NOT NULL,
    thumbnail TEXT,
    is_public BOOLEAN NOT NULL,
    slug TEXT NOT NULL,
    title TEXT NOT NULL,
    content TEXT NOT NULL
);