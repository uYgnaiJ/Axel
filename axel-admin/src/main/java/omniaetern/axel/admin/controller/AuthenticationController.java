package omniaetern.axel.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import omniaetern.axel.admin.DTO.LoginDTO;
import omniaetern.axel.admin.service.UserService;
import omniaetern.axel.common.http.SR;
import omniaetern.axel.model.UserDO;
import omniaetern.axel.model.constant.ErrorCode;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public SR<?> login(@Valid @RequestBody LoginDTO.LoginRequest request,
                       HttpServletRequest httpRequest) {
        try {
            // 1. 认证
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(request.name(), request.password());
            Authentication auth = authenticationManager.authenticate(token);

            // 2. 设置安全上下文
            HttpSession session = httpRequest.getSession(true);
            SecurityContextHolder.getContext().setAuthentication(auth);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // 3. 返回结果
            Map<String, String> result = new HashMap<>();
            result.put("message", "Log In Success");
            result.put("sessionId", session.getId());

            return SR.success(result);

        } catch (BadCredentialsException e) {
            return SR.error(ErrorCode.LOGIN_FAILURE, "name or password is incorrect");
        }
    }

    @PostMapping("/logout")
    public SR<?> logoutTest() {
        return SR.success("Dummy logout endpoint reached");
    }
}
