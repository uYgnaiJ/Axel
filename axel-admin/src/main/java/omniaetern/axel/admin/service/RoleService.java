package omniaetern.axel.admin.service;

import jakarta.persistence.criteria.Predicate;
import omniaetern.axel.admin.DTO.RoleDTO.*;
import omniaetern.axel.admin.repository.RoleRepository;
import omniaetern.axel.common.exception.BusinessException;
import omniaetern.axel.model.RoleDO;
import omniaetern.axel.model.constant.ErrorCode;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Long createRole(RoleCreateRequest request) {
        return roleRepository.save(new RoleDO(request.name(), request.code(), request.remark())).id;
    }

    public void updateRole(RoleUpdateRequest request){
        RoleDO role = roleRepository.findById(request.id()).orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));
        if (request.name()!=null){
            role.id = request.id();
        }
        if (Strings.isNotBlank(request.remark())){
            role.remark = request.remark();
        }
        if (Strings.isNotBlank(request.name())){
            role.name = request.name();
        }
        roleRepository.save(role);
    }

    public void deleteRole(Long id){
        RoleDO role = roleRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));
        role.deleted = true;
        roleRepository.save(role);
    }

    public Page<RoleDO> getRoleInPage(RoleQueryRequest request, Pageable pageable){
        Specification<RoleDO> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.name()!=null){
                predicates.add(builder.equal(root.get("name"), request.name()));
            }
            if (request.code()!=null){
                predicates.add(builder.equal(root.get("code"), request.code()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        return roleRepository.findAll(spec, pageable);
    }

    public RoleDO getRoleById(Long id){
        return roleRepository.findById(id).orElse(null);
    }
}
