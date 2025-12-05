package omniaetern.axel.admin.DTO;

public class DepartmentDTO {
    public record DepartmentCreateRequest(String name, Long parentId){}
    public record DepartmentUpdateRequest(Long id, String name, Long parentId){}
}
