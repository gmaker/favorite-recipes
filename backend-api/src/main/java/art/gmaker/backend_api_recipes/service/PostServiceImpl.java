package art.gmaker.backend_api_recipes.service;

import art.gmaker.backend_api_recipes.dto.PostDTO;
import art.gmaker.backend_api_recipes.mapper.PostMapper;
import art.gmaker.backend_api_recipes.model.Post;
import art.gmaker.backend_api_recipes.repository.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public Optional<PostDTO> getPostById(Long id) {
        return postRepository.findById(id).map(postMapper::toPostDTO);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toPostDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = postMapper.toPost(postDTO);
        Post saved = postRepository.save(post);
        return postMapper.toPostDTO(saved);
    }

    @Override
    public Optional<PostDTO> updatePost(Long id, PostDTO postDTO) {
        return postRepository.findById(id).map(existing -> {
            existing.setTitle(postDTO.getTitle());
            existing.setBody(postDTO.getBody());
            // Можно обновить и другие поля, если требуется
            Post updated = postRepository.save(existing);
            return postMapper.toPostDTO(updated);
        });
    }

    @Override
    public boolean deletePost(Long id) {
        return postRepository.findById(id).map(post -> {
            postRepository.delete(post);
            return true;
        }).orElse(false);
    }

    @Override
    public List<PostDTO> searchPosts(String query) {
        return postRepository.search(query).stream()
                .map(postMapper::toPostDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> multiLanguageFullTextSearch(String query) {
        return postRepository.multiLanguageFullTextSearch(query).stream()
                .map(postMapper::toPostDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).stream()
                .map(postMapper::toPostDTO)
                .collect(Collectors.toList());
    }

    // Новый метод для фильтрации по cooked и тегам
    @Override
    public List<PostDTO> findAll(Pageable pageable, Boolean cooked, List<String> tags) {
        return postRepository.findByFilters(cooked, tags, pageable)
                .stream()
                .map(postMapper::toPostDTO)
                .collect(Collectors.toList());
    }
}
