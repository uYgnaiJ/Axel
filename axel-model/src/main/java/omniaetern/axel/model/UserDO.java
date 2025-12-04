package omniaetern.axel.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
public class UserDO{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String password;

    public String email;

    public String mobile;

    @ManyToOne
    @JoinColumn(name = "department_id")
    public DepartmentDO departmentId;

    @CreationTimestamp
    @Column(updatable = false)
    public String createTime;

    @UpdateTimestamp
    public String updateTime;

    public boolean deleted;
}
