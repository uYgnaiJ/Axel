package omniaetern.axel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_role_relation")
public class UserRoleRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserDO user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    public RoleDO role;

    public boolean deleted = false;

    public UserRoleRelation() {}

    public UserRoleRelation(UserDO user, RoleDO role) {
        this.user = user;
        this.role = role;
    }
}
