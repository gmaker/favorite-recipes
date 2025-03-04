package art.gmaker.backend_api_recipes.dto;

import lombok.Data;

@Data
public class PostDTO {
    private long id;
    private String title;
    private String body;
}
