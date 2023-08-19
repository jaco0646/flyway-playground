package playground.users;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @GetMapping
    Collection<User> findUsers(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value) {
        return userService.findUsers(field, value);
    }
}
