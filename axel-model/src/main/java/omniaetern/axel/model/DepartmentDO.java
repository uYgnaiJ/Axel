package omniaetern.axel.model;

import jakarta.persistence.*;
import omniaetern.axel.model.constant.DepartmentStatus;

@Entity
@Table(name = "department")
public class DepartmentDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public DepartmentDO parent;

    public DepartmentStatus status;

    public DepartmentDO(String name, DepartmentDO parent) {
        this.name = name;
        this.parent = parent;
        this.status = DepartmentStatus.IN_USE;
    }

    public DepartmentDO() {}
}
