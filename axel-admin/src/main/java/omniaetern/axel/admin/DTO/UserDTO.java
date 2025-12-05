package omniaetern.axel.admin.DTO;

import omniaetern.axel.model.DepartmentDO;
import omniaetern.axel.model.UserDO;

public class UserDTO {
    public record UserCreateRequest(String name, String password, String email, String mobile){}
    public record UserUpdateRequest(Long id, String name, String email, String mobile){}
    public record UserUpdatePasswordRequest(Long id, String originalPassword, String newPassword){}

    public record UserInfo(Long id, String name, String email, String mobile, String departmentName){
        public UserInfo (UserDO user){
            this(user.id, user.name, user.email, user.mobile, user.department.name);
        }
    }

    public record UserSearchRequest(Long id, String name, String email, String mobile, Long departmentId){}
}
