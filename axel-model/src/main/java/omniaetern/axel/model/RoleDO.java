package omniaetern.axel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class RoleDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    public String code;

    public String remark;

    public boolean deleted;

    public RoleDO() {}

    public RoleDO(String name, String code, String remark) {
        this.name = name;
        this.code = code;
        this.remark = remark;
    }
}
