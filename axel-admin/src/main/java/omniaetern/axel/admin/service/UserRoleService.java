package omniaetern.axel.admin.service;

import jakarta.persistence.criteria.Predicate;
import omniaetern.axel.admin.repository.UserRoleRepository;
import omniaetern.axel.common.exception.BusinessException;
import omniaetern.axel.model.RoleDO;
import omniaetern.axel.model.UserDO;
import omniaetern.axel.model.UserRoleRelation;
import omniaetern.axel.admin.DTO.UserRoleRelationDTO.*;
import omniaetern.axel.model.constant.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    private final UserService userService;

    private final RoleService roleService;

    public UserRoleService(UserRoleRepository userRoleRepository, UserService userService, RoleService roleService) {
        this.userRoleRepository = userRoleRepository;
        this.userService = userService;
        this.roleService = roleService;
    }

    public Page<UserRoleRelation> getUserRoleRelationInPage(UserRoleRelationQueryRequest request, Pageable pageable) {
        Specification<UserRoleRelation> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.id()!=null) {
                predicates.add(builder.equal(root.get("id"), request.id()));
            }
            if (request.roleId()!=null) {
                predicates.add(builder.equal(root.get("roleId"), request.roleId()));
            }
            if (request.userId()!=null) {
                predicates.add(builder.equal(root.get("userId"), request.userId()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        return userRoleRepository.findAll(spec, pageable);
    }

    public List<UserRoleRelation> getUserRoleRelationById(Long userId){
        return userRoleRepository.findByUserId(userId);
    }

    public Long createUserRoleRelation(UserRoleRelationCreateRequest request) {
        if (request.userId()==null ||  request.roleId()==null) {
            throw new IllegalArgumentException("User and Role Id can't be null");
        }

        UserDO user = userService.getUserById(request.userId());
        if (user==null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        RoleDO role = roleService.getRoleById(request.roleId());
        if (role==null) {
            throw new BusinessException(ErrorCode.ROLE_NOT_FOUND);
        }

        UserRoleRelation relation = new UserRoleRelation(user, role);
        return userRoleRepository.save(relation).id;
    }

    public void deleteUserRoleRelation(Long id){
        UserRoleRelation relation = userRoleRepository.findById(id).orElseThrow(()->new BusinessException(ErrorCode.USER_ROLE_RELATION_NOT_FOUND));
        relation.deleted = true;
        userRoleRepository.save(relation);
    }

    public List<RoleDO> getRolesByIds(List<Long> roleIds) {
        return userRoleRepository.findAllById(roleIds).stream().map(r -> r.role).collect(Collectors.toList());
    }
}
