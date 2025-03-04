package art.gmaker.backend_api_recipes.mapper;


import art.gmaker.backend_api_recipes.dto.PostDTO;
import art.gmaker.backend_api_recipes.model.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper (componentModel = "spring")
public interface PostMapper {
    PostDTO toPostDTO(Post post);
    Post toPost(PostDTO postDTO);
    List<PostDTO> toPostDTO(List<Post> posts);
    List<Post> toPost(List<PostDTO> postDTOs);
}
