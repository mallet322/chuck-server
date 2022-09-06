package ru.elias.server.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.elias.server.dto.UserDto;
import ru.elias.server.model.Role;
import ru.elias.server.service.UserService;

@Controller
@RequiredArgsConstructor
public class RegController {

    private final UserService userService;

    @GetMapping("/registration")
    public String createUser(Model model, @ModelAttribute("user") UserDto userDto) {
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String create(@ModelAttribute UserDto userDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/registration";
        }
        userDto.setAuthority(Role.USER);
        userService.save(userDto);
        return "redirect:/login";
    }

}
