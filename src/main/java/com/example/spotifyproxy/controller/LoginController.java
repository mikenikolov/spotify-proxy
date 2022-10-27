package com.example.spotifyproxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/v1")
public class LoginController {

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/spotify");
    }
}
