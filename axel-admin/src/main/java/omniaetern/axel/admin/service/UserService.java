package omniaetern.axel.admin.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import jakarta.persistence.criteria.Predicate;
import omniaetern.axel.admin.DTO.UserDTO.*;
import omniaetern.axel.admin.repository.UserRepository;
import omniaetern.axel.common.exception.BusinessException;
import omniaetern.axel.common.password.PasswordHandler;
import omniaetern.axel.model.UserDO;
import omniaetern.axel.model.constant.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static omniaetern.axel.common.dto.DTOUtils.getNullPropertyNames;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(UserCreateRequest request) {
        if (getUserByName(request.name())!=null){
            throw new BusinessException(ErrorCode.USERNAME_DUPLICATE);
        }
        String passwordHash = PasswordHandler.hashPassword(request.password());
        return userRepository.save(new UserDO(
                request.name(), passwordHash, request.email(), request.mobile()
        )).id;
    }

    public void deleteUser(Long id){
        UserDO user = getUserById(id);
        user.deleted = true;
        user.deleteTime = Instant.ofEpochMilli(System.currentTimeMillis());
        userRepository.save(user);
    }

    public void updateUser(UserUpdateRequest request){
        UserDO user = getUserById(request.id());
        //BeanUtils.copyProperties(request, user, getNullPropertyNames(request));
        if (request.name() != null) {
            user.name = request.name();
        }
        if (request.email() != null) {
            user.email = request.email();
        }
        if (request.mobile() != null) {
            user.mobile = request.mobile();
        }
        userRepository.save(user);
    }

    public void updatePassword(UserUpdatePasswordRequest request){
        UserDO user = getUserById(request.id());
        if (PasswordHandler.verifyPassword(request.originalPassword(), user.passwordHash)){
            user.passwordHash = PasswordHandler.hashPassword(request.newPassword());
            userRepository.save(user);
        }else{
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH, "Original password is incorrect");
        }
    }

    public UserDO getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    public UserDO getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public UserInfo getUserInfo(Long id){
        UserDO user = getUserById(id);
        return new UserInfo(user);
    }

    public Page<UserInfo> getUserInfoInPage(UserSearchRequest request, Pageable pageable) {
        Specification<UserDO> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.id()!=null){
                predicates.add(cb.equal(root.get("id"), request.id()));
            }
            if (Strings.isNotBlank(request.name())){
                predicates.add(cb.equal(root.get("name"), request.name()));
            }
            if (Strings.isNotBlank(request.email())){
                predicates.add(cb.equal(root.get("email"), request.email()));
            }
            if (Strings.isNotBlank(request.mobile())){
                predicates.add(cb.equal(root.get("mobile"), request.mobile()));
            }
            if (request.departmentId()!=null){
                predicates.add(cb.equal(root.get("department").get("id"), request.departmentId()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return userRepository.findAll(spec, pageable).map(UserInfo::new);
    }
}
