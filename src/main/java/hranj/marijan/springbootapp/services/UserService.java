package hranj.marijan.springbootapp.services;

import hranj.marijan.springbootapp.dto.UserDto;
import hranj.marijan.springbootapp.exeptions.UserAlreadyExistsException;
import hranj.marijan.springbootapp.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    User findUser(String email);

    void addNewUser(UserDto userDto) throws UserAlreadyExistsException;

    void saveUsers(User user);

    @Transactional(rollbackFor = Exception.class)
    void saveUsers(List<User> users);
}
