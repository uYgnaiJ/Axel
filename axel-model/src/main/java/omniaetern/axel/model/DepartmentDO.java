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

    public Long parentId;

    public DepartmentStatus status;
}
