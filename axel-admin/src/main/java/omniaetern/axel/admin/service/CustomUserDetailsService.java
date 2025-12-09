package omniaetern.axel.admin.service;

import omniaetern.axel.model.PermissionDO;
import omniaetern.axel.model.RoleDO;
import omniaetern.axel.model.UserDO;
import omniaetern.axel.model.UserRoleRelation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RolePermissionService rolePermissionService;

    public CustomUserDetailsService(UserService userService, UserRoleService userRoleService, RolePermissionService rolePermissionService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.rolePermissionService = rolePermissionService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO user = userService.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Name or password error");
        }

        if (user.deleted) {
            throw new UsernameNotFoundException("Account Deleted");
        }

        List<UserRoleRelation> userRoleRelations = userRoleService.getUserRoleRelationById(user.id);
        if (CollectionUtils.isEmpty(userRoleRelations)) {
            return buildUserDetails(user, Collections.emptyList(), Collections.emptySet());
        }

        List<Long> roleIds = userRoleRelations.stream().map(relation -> relation.role.id)
                .collect(Collectors.toList());

        List<RoleDO> roles = userRoleService.getRolesByIds(roleIds);
        if (CollectionUtils.isEmpty(roles)) {
            return buildUserDetails(user, Collections.emptyList(), Collections.emptySet());
        }

        Set<PermissionDO> permissions = rolePermissionService.getPermissionsByRoleIds(roleIds);

        Set<GrantedAuthority> authorities = new HashSet<>();

        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.code)));

        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.code)));

        return buildUserDetails(user, roles, authorities);
    }

    private UserDetails buildUserDetails(UserDO user, List<RoleDO> roles, Collection<? extends GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(
                user.name,
                user.passwordHash,
                authorities
        );
    }
}