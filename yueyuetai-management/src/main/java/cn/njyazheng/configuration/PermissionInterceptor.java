package cn.njyazheng.configuration;

import cn.njyazheng.service.system.PermissionService;
import cn.njyazheng.vo.PermissionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    PermissionService permissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        if (log.isDebugEnabled()) {
            log.debug("PermissionInterceptor intercept url:{}", uri);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<PermissionVo> permissionVos = permissionService.getOperatorPermission(authentication.getName(), uri);
        if (permissionVos != null && !permissionVos.isEmpty()) {
            List<PermissionVo> tabletoolbars = permissionVos.stream().filter(
                    p -> p.getType() != null && p.getType() == 2
            ).collect(Collectors.toList());
            List<PermissionVo> rowtoolbars = permissionVos.stream().filter(
                    p -> p.getType() != null && p.getType() != 2 && p.getType() != 5
            ).collect(Collectors.toList());
            request.setAttribute("tabletoolbars", tabletoolbars);
            request.setAttribute("rowtoolbars", rowtoolbars);
        }
        return true;
    }
}
