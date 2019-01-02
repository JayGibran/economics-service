package com.economics.dto.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

}
