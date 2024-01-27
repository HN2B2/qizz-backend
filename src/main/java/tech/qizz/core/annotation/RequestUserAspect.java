package tech.qizz.core.annotation;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import tech.qizz.core.entity.User;
import tech.qizz.core.exception.ForbiddenException;
import tech.qizz.core.exception.NotFoundException;
import tech.qizz.core.manageUser.UserRepository;

@Aspect
@Component
@RequiredArgsConstructor
public class RequestUserAspect {

    private final UserRepository userRepository;

    @Around("@annotation(requestUser) && args(user,..)")
    public Object around(ProceedingJoinPoint joinPoint, RequestUser requestUser, User user)
        throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            Optional<User> dbUser = userRepository.findByEmail(userDetails.getUsername());
            if (dbUser.isEmpty()) {
                throw new NotFoundException("User not found");
            }
            user = dbUser.get();
            return joinPoint.proceed();
        } else {
            throw new ForbiddenException("User not authenticated");
        }
    }
}
