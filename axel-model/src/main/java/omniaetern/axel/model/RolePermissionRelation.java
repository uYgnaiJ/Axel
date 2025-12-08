package omniaetern.axel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role_permission_relation")
public class RolePermissionRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    public RoleDO role;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    public PermissionDO permission;

    public boolean deleted;

    public RolePermissionRelation(RoleDO role, PermissionDO permission) {
        this.role = role;
        this.permission = permission;
    }

    public RolePermissionRelation() {}
}
