package omniaetern.axel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "permission")
public class PermissionDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String code;

    public String remark;

    public boolean deleted = false;

    public PermissionDO(String name, String code, String remark) {
        this.name = name;
        this.code = code;
        this.remark = remark;
    }

    public PermissionDO() {}
}
