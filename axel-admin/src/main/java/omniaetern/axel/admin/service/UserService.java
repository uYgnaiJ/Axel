package omniaetern.axel.admin.service;

import omniaetern.axel.admin.DTO.UserCreateRequest;
import omniaetern.axel.admin.repository.UserRepository;
import omniaetern.axel.model.UserDO;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(UserCreateRequest request) {


        UserDO user = new UserDO();
        return null;
    }
}
