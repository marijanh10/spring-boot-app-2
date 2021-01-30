package hranj.marijan.springbootapp.services.impl;

import hranj.marijan.springbootapp.dto.UserDto;
import hranj.marijan.springbootapp.exeptions.UserAlreadyExistsException;
import hranj.marijan.springbootapp.model.User;
import hranj.marijan.springbootapp.repository.UserRepository;
import hranj.marijan.springbootapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addNewUser(UserDto userDto) throws UserAlreadyExistsException {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user != null) {
            throw new UserAlreadyExistsException("An user with the email:" + userDto.getEmail() + " already exists!");
        }
        user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUsers(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUsers(List<User> users) {
        userRepository.saveAll(users);
    }

}
