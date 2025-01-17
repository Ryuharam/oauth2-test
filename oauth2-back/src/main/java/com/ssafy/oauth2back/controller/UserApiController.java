package com.ssafy.oauth2back.controller;

import com.ssafy.oauth2back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

}
