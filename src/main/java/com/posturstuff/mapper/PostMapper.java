package com.posturstuff.mapper;

import com.posturstuff.dto.posts.PostViewDto;
import com.posturstuff.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostViewDto postToPostViewDto(Post post);

}
