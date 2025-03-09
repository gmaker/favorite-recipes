package art.gmaker.backend_api_recipes.mapper;


import art.gmaker.backend_api_recipes.dto.PostDTO;
import art.gmaker.backend_api_recipes.dto.TagDTO;
import art.gmaker.backend_api_recipes.model.Post;
import art.gmaker.backend_api_recipes.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper (componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "posts", ignore = true)
    Tag toTag (TagDTO tagDTO);
    TagDTO toTagDTO (Tag tag);

    PostDTO toPostDTO(Post post);
    Post toPost(PostDTO postDTO);
    List<PostDTO> toPostDTO(List<Post> posts);
    List<Post> toPost(List<PostDTO> postDTOs);
}
