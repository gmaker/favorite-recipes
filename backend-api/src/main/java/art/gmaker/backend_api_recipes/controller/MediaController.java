package art.gmaker.backend_api_recipes.controller;

import art.gmaker.backend_api_recipes.dto.MediaResponse;
import art.gmaker.backend_api_recipes.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }
    // Endpoint для загрузки медиа с обязательным параметром postId
    @PostMapping("/upload")
    public ResponseEntity<MediaResponse> uploadMedia(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("postId") long postId) {
        String relativePath = mediaService.saveMedia(file, postId);
        return ResponseEntity.ok(new MediaResponse(relativePath));
    }
}
