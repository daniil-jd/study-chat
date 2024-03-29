package ru.pet.studychat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pet.studychat.dto.UserRequestDto;
import ru.pet.studychat.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final UserService userService;

    @GetMapping
    public String doChat(HttpServletRequest request, Model model) {
        String login = (String) request.getSession().getAttribute("login");
        Optional<UserRequestDto> userOptional = userService.getUserByLogin(login);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
        } else {
            return "redirect:/";
        }

        return "chat1";
    }
}
