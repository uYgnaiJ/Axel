package omniaetern.axel.admin.service;

import jakarta.validation.constraints.NotNull;
import omniaetern.axel.admin.DTO.DepartmentDTO.*;
import omniaetern.axel.admin.repository.DepartmentRepository;
import omniaetern.axel.model.DepartmentDO;
import omniaetern.axel.model.constant.DepartmentStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static omniaetern.axel.common.dto.DTOUtils.getNullPropertyNames;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Long createDepartment(DepartmentCreateRequest request) {
        return departmentRepository.save(new DepartmentDO(request.name(), getDepartmentById(request.parentId()))).id;
    }

    public DepartmentDO getDepartmentById(Long id){
        return departmentRepository.findById(id).orElse(null);
    }

    public DepartmentDO getDepartmentByName(String name) {return departmentRepository.findDepartmentDOByName(name);}

    public Page<DepartmentDO> getRootDepartments(Pageable pageable) {
        Specification<DepartmentDO> spec = (root, query,cb) -> {
            return cb.isNull(root.get("parentId"));
        };
        return departmentRepository.findAll(spec, pageable);
    }

    public Page<DepartmentDO> getChildrenDepartments(Long id, Pageable pageable) {
        Specification<DepartmentDO> spec = (root, query, cb) -> {
            return cb.equal(root.get("parent").get("id"), id);
        };
        return departmentRepository.findAll(spec, pageable);
    }

    public void deleteDepartment(@NotNull Long id) {
        DepartmentDO department = getDepartmentById(id);
        department.status = DepartmentStatus.DELETED;
        departmentRepository.save(department);
    }

    public void updateDepartment(DepartmentUpdateRequest request) {
        DepartmentDO department = getDepartmentById(request.id());
        //BeanUtils.copyProperties(request, department, getNullPropertyNames(request));
        if (request.name() != null) {
            department.name = request.name();
        }
        if (request.parentId() != null) {
            department.parent = getDepartmentById(request.parentId());
        }
        departmentRepository.save(department);
    }
}
