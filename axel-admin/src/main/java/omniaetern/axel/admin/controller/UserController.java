package omniaetern.axel.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import omniaetern.axel.admin.DTO.UserDTO.*;
import omniaetern.axel.admin.service.UserService;
import omniaetern.axel.common.http.SR;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
@Tag(name = "User Administration", description = "User related apis")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public SR<UserInfo> getUserInfo(@RequestParam Long id) {
        return SR.success(userService.getUserInfo(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Create User", description = "Create new user in Axel")
    public SR<?> createNewUser(@RequestBody UserCreateRequest request){
        return SR.success(userService.createUser(request));
    }

    @GetMapping("/page")
    public SR<Page<UserInfo>> getUsers(UserSearchRequest request, @PageableDefault Pageable pageable
    ){
        return SR.success(userService.getUserInfoInPage(request, pageable));
    }

    @DeleteMapping("/delete")
    public SR<?> deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return SR.success();
    }

    @PutMapping("/update")
    public SR<?> updateUser(@RequestBody UserUpdateRequest request){
        userService.updateUser(request);
        return SR.success();
    }
}
