package pl.itembase.demo.user;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {

        if (user == null) return null;
        return new UserDTO(
                user.getId(),
                user.getLogin(),
                user.getPassword()
        );
    }
}
