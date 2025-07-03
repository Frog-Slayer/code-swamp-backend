CREATE VIEW folder AS
SELECT id, name, owner_id, parent_id, full_path
FROM article.folder;

CREATE VIEW published_articles AS
SELECT id, author_id, folder_id, created_at, updated_at, summary, thumbnail, is_public, slug, title, content
FROM projection.published_articles;

