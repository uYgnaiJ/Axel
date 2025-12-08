package omniaetern.axel.admin.DTO;

public class UserRoleRelationDTO {
    public record UserRoleRelationQueryRequest(Long id, Long userId, Long roleId) {};
    public record UserRoleRelationCreateRequest(Long userId, Long roleId) {};
}
