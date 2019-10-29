package ru.pet.studychat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pet.studychat.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/logout")
@RequiredArgsConstructor
public class LogoutController {
    private final UserService userService;

    @GetMapping
    public String getLoginPage(HttpServletRequest request) {
        String login = (String) request.getSession().getAttribute("login");
        userService.removeUserByLogin(login);
        return "redirect:/";
    }
}
