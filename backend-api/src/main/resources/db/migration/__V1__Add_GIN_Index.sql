CREATE INDEX idx_posts_fulltext ON posts
    USING gin (to_tsvector('russian', title || ' ' || body));
