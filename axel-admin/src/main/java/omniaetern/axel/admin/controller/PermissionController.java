package omniaetern.axel.admin.controller;

import omniaetern.axel.admin.DTO.PermissionDTO.*;
import omniaetern.axel.admin.service.PermissionService;
import omniaetern.axel.common.http.SR;
import omniaetern.axel.model.PermissionDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
@Validated
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/create")
    public SR<Long> createPermission(@RequestBody PermissionCreateRequest request) {
        return SR.success(permissionService.createPermission(request));
    }

    @PutMapping("/update")
    public SR<?> updatePermission(PermissionUpdateRequest request){
        permissionService.updatePermission(request);
        return SR.success();
    }

    @DeleteMapping("/delete")
    public SR<?> deletePermission(Long id){
        permissionService.deletePermission(id);
        return SR.success();
    }

    @GetMapping("/page")
    public SR<Page<PermissionDO>> getPermissionsInPage(PermissionQueryRequest request, @PageableDefault Pageable pageable){
        return SR.success(permissionService.getPermissionsInPage(request, pageable));
    }
}
