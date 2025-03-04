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
    private final String mediaBaseUrl;

    public MediaController(MediaService mediaService,
                           @Value("${media.base.url:http://localhost:8081}") String mediaBaseUrl) {
        this.mediaService = mediaService;
        this.mediaBaseUrl = mediaBaseUrl;
    }
    // Endpoint для загрузки медиа с обязательным параметром postId
    @PostMapping("/upload")
    public ResponseEntity<MediaResponse> uploadMedia(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("postId") long postId) {
        // Сохраняем файл. Метод возвращает относительный путь, например, "/uploads/1740714192172_1740714386168_00.png"
        String relativePath = mediaService.saveMedia(file, postId);
        // Формируем абсолютный URL для клиента, используя базовый URL, который можно задать в конфигурации
        String absoluteUrl = mediaBaseUrl + relativePath;
        return ResponseEntity.ok(new MediaResponse(absoluteUrl));
    }
}
