package omniaetern.axel.admin.controller;

import omniaetern.axel.admin.DTO.UserRoleRelationDTO.*;
import omniaetern.axel.admin.service.UserRoleService;
import omniaetern.axel.common.http.SR;
import omniaetern.axel.model.UserRoleRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-role")
@Validated
public class UserRoleController {
    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PreAuthorize("hasRole('AXEL_ADMIN')")
    @GetMapping("/page")
    public SR<Page<UserRoleRelation>> getUserRoleRelationInPage(UserRoleRelationQueryRequest request,
                                                                @PageableDefault Pageable pageable){
        return SR.success(userRoleService.getUserRoleRelationInPage(request, pageable));
    }

    @PostMapping("/create")
    public SR<Long> createUserRole(@RequestBody @Validated UserRoleRelationCreateRequest request){
        return SR.success(userRoleService.createUserRoleRelation(request));
    }

    @DeleteMapping("/delete")
    public SR<?> deleteUserRole(@RequestParam Long id){
        userRoleService.deleteUserRoleRelation(id);
        return SR.success();
    }
}
