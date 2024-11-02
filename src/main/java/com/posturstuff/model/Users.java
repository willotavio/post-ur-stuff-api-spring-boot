package com.posturstuff.model;

import com.posturstuff.enums.AccountVisibility;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Users {

    private String username;
    private String displayName;
    private String email;
    private String password;

    private LocalDate joinedAt;
    private LocalDate birthDate;

    private AccountVisibility accountVisibility;

    private String profilePicture;
    private String profileCover;

}
