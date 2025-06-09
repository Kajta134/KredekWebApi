package pl.itembase.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.itembase.demo.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    public List<UserDTO> findAll() {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userDTOS.add(userDTOMapper.apply(user));
        }
        return userDTOS;

    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return userDTOMapper.apply(user);
    }

    public UserDTO save(User user) {
        User save = userRepository.save(user);
        return userDTOMapper.apply(save);
    }

    public UserDTO update(Long id, User user) {
        return userRepository.findById(id).map(u -> {
            u.setLogin(user.getLogin());
            u.setPassword(user.getPassword());
            return userDTOMapper.apply(u);
        }).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserDTO patch(Long id, User user) {
        return userRepository.findById(id).map(u -> {
            if (user.getLogin() != null) u.setLogin(user.getLogin());
            if (user.getPassword() != null) u.setPassword(user.getPassword());
            return userDTOMapper.apply(u);
        }).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
}
