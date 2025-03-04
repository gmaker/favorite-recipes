package art.gmaker.backend_api_recipes.repository;

import art.gmaker.backend_api_recipes.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.body) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Post> search(@Param("query") String query);


    @Query(value = "SELECT * FROM posts " +
            "WHERE to_tsvector('russian', title || ' ' || body) " +
            "@@ plainto_tsquery('russian', :query)",
            nativeQuery = true)
    List<Post> fullTextSearch(@Param("query") String query);

    @Query(value = "SELECT * FROM posts " +
            "WHERE (to_tsvector('russian', title || ' ' || body) || " +
            "       to_tsvector('english', title || ' ' || body)) " +
            "@@ plainto_tsquery('russian', :query) " +
            "OR (to_tsvector('russian', title || ' ' || body) || " +
            "    to_tsvector('english', title || ' ' || body)) " +
            "@@ plainto_tsquery('english', :query)",
            nativeQuery = true)
    List<Post> multiLanguageFullTextSearch(@Param("query") String query);
}
