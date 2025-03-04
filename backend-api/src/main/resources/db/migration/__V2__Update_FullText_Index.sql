-- Удаляем старый индекс, если он существует
DROP INDEX IF EXISTS idx_posts_fulltext;

-- Создаем новый GIN индекс, объединяющий русский и английский векторы
CREATE INDEX idx_posts_fulltext ON posts
    USING gin (
    (to_tsvector('russian', title || ' ' || body) ||
    to_tsvector('english', title || ' ' || body))
    );
