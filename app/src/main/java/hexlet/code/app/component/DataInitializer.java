package hexlet.code.app.component;

import hexlet.code.app.dto.UserCreateDTO;
import hexlet.code.app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import hexlet.code.app.repository.UserRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var userData = new UserCreateDTO();
        userData.setEmail("hexlet@example.com");
        String hashedPassword = passwordEncoder.encode("qwerty");
        userData.setPasswordDigest(hashedPassword);
        var user = userMapper.map(userData);
        userRepository.save(user);
    }
}