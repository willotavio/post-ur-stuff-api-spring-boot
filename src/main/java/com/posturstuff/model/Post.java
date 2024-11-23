package com.posturstuff.model;

import com.posturstuff.enums.PostVisibility;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
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
    private LocalDate createdAt;
    private LocalDate editedAt;
    private PostVisibility visibility;

    public Post(String content, List<String> images, Users user, LocalDate createdAt, LocalDate editedAt, PostVisibility visibility) {
        this.content = content;
        this.images = images;
        this.user = user;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
        this.visibility = visibility;
    }

}
