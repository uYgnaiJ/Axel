package omniaetern.axel.admin.service;

import jakarta.persistence.criteria.Predicate;
import omniaetern.axel.admin.DTO.PermissionDTO;
import omniaetern.axel.admin.repository.PermissionRepository;
import omniaetern.axel.common.exception.BusinessException;
import omniaetern.axel.model.PermissionDO;
import omniaetern.axel.model.constant.ErrorCode;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Long createPermission(PermissionDTO.PermissionCreateRequest request) {
        PermissionDO permission = new PermissionDO(request.name(), request.code(), request.remark());
        return permissionRepository.save(permission).id;
    }

    public void updatePermission(PermissionDTO.PermissionUpdateRequest request) {
        PermissionDO permission = permissionRepository.findById(request.id()).orElse(null);
        if (permission == null) {
            throw new BusinessException(ErrorCode.PERMISSION_NOT_FOUND);
        }
        if (Strings.isNotBlank(request.name())){
            permission.name = request.name();
        }
        if (Strings.isNotBlank(request.remark())){
            permission.remark = request.remark();
        }
        permissionRepository.save(permission);
    }

    public void deletePermission(Long id) {
        PermissionDO permission = permissionRepository.findById(id).orElse(null);
        if (permission == null) {
            throw new BusinessException(ErrorCode.PERMISSION_NOT_FOUND);
        }
        permission.deleted = true;
        permissionRepository.save(permission);
    }

    public Page<PermissionDO> getPermissionsInPage(PermissionDTO.PermissionQueryRequest request, Pageable pageable) {
        Specification<PermissionDO> spec = (root, query, builder) ->{
            List<Predicate> predicates =  new ArrayList<>();
            if (request.id()!=null){
                predicates.add(builder.equal(root.get("id"), request.id()));
            }
            if (request.code()!=null){
                predicates.add(builder.equal(root.get("code"), request.code()));
            }
            if (request.name()!=null){
                predicates.add(builder.equal(root.get("name"), request.name()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        return permissionRepository.findAll(spec, pageable);
    }

    public PermissionDO getPermissionById(Long id){
        return permissionRepository.findById(id).orElse(null);
    }
}
