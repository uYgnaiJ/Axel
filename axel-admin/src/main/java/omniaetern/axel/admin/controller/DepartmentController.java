package omniaetern.axel.admin.controller;

import omniaetern.axel.admin.DTO.DepartmentDTO.*;
import omniaetern.axel.admin.service.DepartmentService;
import omniaetern.axel.common.http.SR;
import omniaetern.axel.model.DepartmentDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/department")
@RestController
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/create")
    public SR<Long> createDepartment(@RequestBody DepartmentCreateRequest request){
        return SR.success(departmentService.createDepartment(request));
    }

    @GetMapping("/page/root")
    public SR<Page<DepartmentDO>> getRootDepartments(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return SR.success(departmentService.getRootDepartments(pageable));
    }

    @GetMapping("/page/children")
    public SR<Page<DepartmentDO>> getChildrenDepartments(@NotNull Long id,
                                                         @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return SR.success(departmentService.getChildrenDepartments(id, pageable));
    }

    @DeleteMapping("/delete")
    public SR<?> deleteDepartment(@NotNull Long id){
        departmentService.deleteDepartment(id);
        return SR.success();
    }

    @PutMapping("/update")
    public SR<?> updateDepartment(DepartmentUpdateRequest request){
        departmentService.updateDepartment(request);
        return SR.success();
    }
}
