package art.gmaker.backend_api_recipes.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class MediaService {

    private final Path uploadPath = Paths.get("uploads");

    public MediaService() {
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать директорию для загрузок", e);
        }
    }

    // Сохраняет файл с именем, содержащим postId и временную метку
    public String saveMedia(MultipartFile file, long postId) {
        String originalFilename = file.getOriginalFilename();
        String newFileName = postId + "_" + System.currentTimeMillis() + "_" + originalFilename;
        Path filePath = uploadPath.resolve(newFileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении файла", e);
        }
        // Предполагается, что статика раздаётся из /uploads
        return "/uploads/" + newFileName;
    }

    // Удаляет все файлы, связанные с указанным postId
    public void deleteMediaByPostId(long postId) {
        try (Stream<Path> files = Files.list(uploadPath)) {
            files.filter(path -> path.getFileName().toString().startsWith(postId + "_"))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
