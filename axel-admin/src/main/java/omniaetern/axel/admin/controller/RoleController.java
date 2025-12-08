package omniaetern.axel.admin.controller;

import jakarta.validation.constraints.NotNull;
import omniaetern.axel.admin.DTO.RoleDTO.*;
import omniaetern.axel.admin.service.RoleService;
import omniaetern.axel.common.http.SR;
import omniaetern.axel.model.RoleDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@Validated
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public SR<Long> createRole(@RequestBody RoleCreateRequest request) {
        return SR.success(roleService.createRole(request));
    }

    @PutMapping("/update")
    public SR<?> updateRole(@RequestBody RoleUpdateRequest request) {
        roleService.updateRole(request);
        return SR.success();
    }

    @DeleteMapping("/delete")
    public SR<?> deleteRole(@RequestParam @NotNull Long id) {
        roleService.deleteRole(id);
        return SR.success();
    }

    @GetMapping("/page")
    public SR<Page<RoleDO>> getRolesInPage(RoleQueryRequest request, @PageableDefault Pageable pageable) {
        return SR.success(roleService.getRoleInPage(request, pageable));
    }

}
