package omniaetern.axel.admin.DTO;

public class PermissionDTO {
    public record PermissionCreateRequest(String name, String code, String remark){};
    public record PermissionUpdateRequest(Long id, String name, String remark){};
    public record PermissionQueryRequest(Long id, String name, String code){};
}
