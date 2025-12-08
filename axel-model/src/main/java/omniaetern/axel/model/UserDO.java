package omniaetern.axel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "users")
public class UserDO{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String passwordHash;

    public String email;

    public String mobile;

    @ManyToOne
    @JoinColumn(name = "department_id")
    public DepartmentDO department;

    @CreationTimestamp
    @Column(updatable = false)
    public Instant createTime;

    @UpdateTimestamp
    public Instant updateTime;

    public boolean deleted = false;

    public Instant deleteTime;

    public UserDO(String name, String passwordHash, String email, String mobile) {
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = email;
        this.mobile = mobile;
    }

    public UserDO() {}
}
