package com.posturstuff.model;

import com.posturstuff.enums.AccountVisibility;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Users {

    @Indexed(unique = true)
    private String username;
    private String displayName;
    @Indexed(unique = true)
    private String email;
    private String password;

    private LocalDate joinedAt;
    private LocalDate birthDate;

    private AccountVisibility accountVisibility;

    private String profilePicture;
    private String profileCover;

}
