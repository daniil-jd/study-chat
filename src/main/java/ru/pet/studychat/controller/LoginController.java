package ru.pet.studychat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pet.studychat.dto.UserRequestDto;
import ru.pet.studychat.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {
    private final UserService service;

    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("user", new UserRequestDto());
        return "login";
    }

    @PostMapping
    public String saveLogin(@ModelAttribute UserRequestDto userRequestDto, Model model, HttpServletRequest request) {
        boolean result = service.add(userRequestDto);
        if (!result) {
            model.addAttribute("error", true);
            model.addAttribute("user", new UserRequestDto());
            return "login";
        }
        request.getSession().setAttribute("login", userRequestDto.getLogin());
        return "redirect:/chat";
    }

}
