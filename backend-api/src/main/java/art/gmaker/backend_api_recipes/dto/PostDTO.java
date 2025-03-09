package art.gmaker.backend_api_recipes.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostDTO {
    private long id;
    private String url;
    private String title;
    private String coverImageUrl;
    private String status;
    private int cookedCount;
    private Set<TagDTO> tags;
    private String body;
    private String parsedData;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
