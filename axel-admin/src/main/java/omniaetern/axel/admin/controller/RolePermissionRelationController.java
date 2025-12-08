package omniaetern.axel.admin.controller;

import jakarta.validation.constraints.NotNull;
import omniaetern.axel.admin.DTO.RolePermissionRelationDTO.*;
import omniaetern.axel.admin.service.RolePermissionService;
import omniaetern.axel.common.http.SR;
import omniaetern.axel.model.RolePermissionRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role-permission")
@Validated
public class RolePermissionRelationController {
    private final RolePermissionService rolePermissionService;

    public RolePermissionRelationController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping("/page")
    public SR<Page<RolePermissionRelation>> getRolePermissionRelationInPage(RolePermissionRelationQueryRequest request, @PageableDefault Pageable pageable){
        return SR.success(rolePermissionService.getRolePermissionRelationInPage(request, pageable));
    }

    @PostMapping("/create")
    public SR<Long> createRolePermissionRelation(@RequestBody RolePermissionRelationCreateRequest request){
        return SR.success(rolePermissionService.createRolePermissionRelation(request));
    }

    @DeleteMapping("/delete")
    public SR<?> deleteRolePermissionRelation(@RequestParam @NotNull Long id){
        rolePermissionService.deleteRolePermissionRelation(id);
        return SR.success();
    }
}
