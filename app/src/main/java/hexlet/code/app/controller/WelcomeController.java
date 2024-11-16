package hexlet.code.app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to Spring";
    }
}
