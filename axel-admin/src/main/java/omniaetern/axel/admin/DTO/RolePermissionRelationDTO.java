package omniaetern.axel.admin.DTO;

public class RolePermissionRelationDTO {
    public record RolePermissionRelationQueryRequest(Long id, Long roleId, Long permissionId) {};
    public record RolePermissionRelationCreateRequest(Long roleId, Long permissionId) {};
}
