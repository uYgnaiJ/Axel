package omniaetern.axel.admin.DTO;

import omniaetern.axel.model.RoleDO;

public class RoleDTO {
    public record RoleCreateRequest(String name, String code, String remark) {};
    public record RoleUpdateRequest(Long id, String name, String remark) {};
    public record RoleQueryRequest(Long id, String name, String code) {};
}
