package com.posturstuff.model;

import com.posturstuff.enums.PostVisibility;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {

    @Id
    private String id;

    private String content;
    private List<String> images;
    @DBRef
    private Users user;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private PostVisibility visibility;

    public Post(String content, List<String> images, Users user, LocalDateTime createdAt, PostVisibility visibility) {
        this.content = content;
        this.images = images;
        this.user = user;
        this.createdAt = createdAt;
        this.visibility = visibility;
    }

}
