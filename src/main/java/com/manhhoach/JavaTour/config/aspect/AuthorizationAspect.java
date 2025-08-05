package com.manhhoach.JavaTour.config.aspect;

import com.manhhoach.JavaTour.config.annotations.IsAuthorized;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Objects;

@Component
@Aspect // đánh dấu là aspect => chứa các advice
public class AuthorizationAspect {

    // chỗ nào gọi annotation này thì sẽ gọi hàm before này
    @Before("@annotation(isAuthorized)")
    public void checkAuthorization(IsAuthorized isAuthorized) {
        String[] requiredAuthority = isAuthorized.value();
        if (Objects.isNull(requiredAuthority) || requiredAuthority.length == 0) {
            return;
        }
        String header = getAuthorizationHeader();
        if (header == null || !header.startsWith("Bearer ")) {
            throw new AuthorizationServiceException("Access denied");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new AuthorizationServiceException("Access denied");
        }
        var authorities = auth.getAuthorities().stream().map(e -> e.getAuthority()).toList();
        boolean isInvalid = Arrays.stream(requiredAuthority).anyMatch(x -> !authorities.contains(x));
        if (isInvalid) {
            throw new AuthorizationServiceException("Access denied");
        }
    }

    private String getAuthorizationHeader() {
        var req = RequestContextHolder.getRequestAttributes();
        if (req instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) req).getRequest().getHeader("Authorization");
        }
        return null;
    }
}
