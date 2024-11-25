package hexlet.code.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;

@Component
public class UserUtils {
    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(getClass());

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email).get();
    }

    public boolean isAuthor(Long id) {
        var authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findById(id).orElse(null);

        // Логируем данные
        System.out.println("Authenticated user: " + authenticatedUserEmail);
        System.out.println("Target user ID: " + id);
        System.out.println("Target user: " + (user != null ? user.getEmail() : "User not found"));

        boolean result = user != null && user.getEmail().equals(authenticatedUserEmail);
        System.out.println("Authorization result: " + result);

        return result;
    }
}
