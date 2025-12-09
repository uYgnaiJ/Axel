package omniaetern.axel.admin.DTO;

public class LoginDTO {
    public record LoginRequest (String name, String password){};
}
