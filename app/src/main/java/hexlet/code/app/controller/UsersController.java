package hexlet.code.app.controller;

import hexlet.code.app.dto.UsersDTO.UserCreateDTO;
import hexlet.code.app.dto.UsersDTO.UserDTO;
import hexlet.code.app.dto.UsersDTO.UserUpdateDTO;

import hexlet.code.app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDTO>> index() {
        var result = userService.findAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("isAuthenticated()")
    public UserDTO show(@PathVariable long id) {
        return userService.findById(id);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserCreateDTO userData) {
        return userService.save(userData);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@userUtils.isAuthor(#id)")
    UserDTO update(@RequestBody UserUpdateDTO userData, @PathVariable Long id) {
        return userService.updateById(userData, id);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@userUtils.isAuthor(#id)")
    public void destroy(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
