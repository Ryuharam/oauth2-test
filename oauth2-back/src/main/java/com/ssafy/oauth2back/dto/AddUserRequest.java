package com.ssafy.oauth2back.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String email;
    private String name;
    private String age;
}
