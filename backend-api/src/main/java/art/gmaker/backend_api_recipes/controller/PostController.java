package art.gmaker.backend_api_recipes.controller;

import art.gmaker.backend_api_recipes.dto.PostDTO;
import art.gmaker.backend_api_recipes.service.MediaService;
import art.gmaker.backend_api_recipes.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final MediaService mediaService;


    public PostController(PostService postService, MediaService mediaService) {
        this.postService = postService;
        this.mediaService = mediaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<PostDTO> getAllPosts(Pageable pageable,
                                     @RequestParam(name = "cooked", required = false) Boolean cooked,
                                     @RequestParam(name = "tags", required = false) String tags) {
        List<String> tagList = null;
        if (tags != null && !tags.isBlank()) {
            tagList = Arrays.stream(tags.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
        }
        return postService.findAll(pageable, cooked, tagList);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        PostDTO savedPost = postService.createPost(postDTO);
        return ResponseEntity.ok(savedPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        return postService.updatePost(id, postDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        mediaService.deleteMediaByPostId(id);
        return ResponseEntity.noContent().build();
    }

    // Пример для поиска (можно также создать отдельные эндпоинты)
    @GetMapping("/search")
    public List<PostDTO> searchPosts(@RequestParam String query) {
        return postService.searchPosts(query);
    }

    @GetMapping("/multilang-search")
    public List<PostDTO> multiLanguageFullTextSearch(@RequestParam String query) {
        return postService.multiLanguageFullTextSearch(query);
    }
}
