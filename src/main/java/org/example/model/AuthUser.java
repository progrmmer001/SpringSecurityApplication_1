package org.example.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUser {
    private Integer id;
    private String username;
    private String password;
    private String role;
}
