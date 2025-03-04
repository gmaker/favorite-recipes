package art.gmaker.backend_api_recipes.service;

import art.gmaker.backend_api_recipes.dto.PostDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<PostDTO> getPostById(Long id);

    List<PostDTO> getAllPosts();

    PostDTO createPost(PostDTO postDTO);

    Optional<PostDTO> updatePost(Long id, PostDTO postDTO);

    boolean deletePost(Long id);

    List<PostDTO> searchPosts(String query);

    List<PostDTO> multiLanguageFullTextSearch(String query);

    List<PostDTO> findAll(Pageable pageable);
}
