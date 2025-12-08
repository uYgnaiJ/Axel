package omniaetern.axel.admin.service;

import jakarta.persistence.criteria.Predicate;
import omniaetern.axel.admin.DTO.RolePermissionRelationDTO.*;
import omniaetern.axel.admin.repository.RolePermissionRepository;
import omniaetern.axel.common.exception.BusinessException;
import omniaetern.axel.model.PermissionDO;
import omniaetern.axel.model.RoleDO;
import omniaetern.axel.model.RolePermissionRelation;
import omniaetern.axel.model.constant.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;

    private final RoleService roleService;

    private final PermissionService permissionService;

    public RolePermissionService(RolePermissionRepository rolePermissionRepository, RoleService roleService, PermissionService permissionService) {
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    public Page<RolePermissionRelation> getRolePermissionRelationInPage(RolePermissionRelationQueryRequest request, Pageable pageable) {
        Specification<RolePermissionRelation> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.id()!=null){
                predicates.add(builder.equal(root.get("id"), request.id()));
            }
            if (request.roleId()!=null){
                predicates.add(builder.equal(root.get("roleId"), request.roleId()));
            }
            if (request.permissionId()!=null){
                predicates.add(builder.equal(root.get("permissionId"), request.permissionId()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        return rolePermissionRepository.findAll(spec, pageable);
    }

    public Long createRolePermissionRelation(RolePermissionRelationCreateRequest request) {
        if (request.roleId()==null || request.permissionId()==null) {
            throw new IllegalArgumentException("roleId and permissionId can't be null");
        }

        RoleDO role = roleService.getRoleById(request.roleId());
        if (role == null){
            throw new BusinessException(ErrorCode.ROLE_NOT_FOUND);
        }

        PermissionDO permission = permissionService.getPermissionById(request.permissionId());
        if (permission == null){
            throw new BusinessException(ErrorCode.PERMISSION_NOT_FOUND);
        }

        RolePermissionRelation relation = new RolePermissionRelation(role, permission);
        return rolePermissionRepository.save(relation).id;
    }

    public void deleteRolePermissionRelation(Long id){
        RolePermissionRelation relation = rolePermissionRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.ROLE_PERMISSION_RELATION_NOT_FOUND));
        relation.deleted = true;
        rolePermissionRepository.save(relation);
    }
}
