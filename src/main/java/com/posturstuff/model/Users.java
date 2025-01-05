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

    @Id
    private String id;

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
    private String description;

    public Users(String username, String displayName, String email, String password, LocalDate joinedAt, LocalDate birthDate, AccountVisibility accountVisibility, String profilePicture, String profileCover, String description) {
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.joinedAt = joinedAt;
        this.birthDate = birthDate;
        this.accountVisibility = accountVisibility;
        this.profilePicture = profilePicture;
        this.profileCover = profileCover;
        this.description = description;
    }

}
