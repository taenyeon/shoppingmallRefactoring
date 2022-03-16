package com.shop.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.shop.myapp.dto.MemberSession;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info(this.getClass().toString());
        //  handler 종류 확인
        // 우리가 관심 있는 것은 Controller 에 있는 메서드이므로 HandlerMethod 타입인지 체크
        if (!(handler instanceof HandlerMethod)) {
            // return true 이면  Controller 에 있는 메서드가 아니므로, 그대로 컨트롤러로 진행
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // @Auth 받아오기

        // class 에 할당한 어노테이션 체크
        Auth classAuth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
        // method 에 할당한 어노테이션 체크
        Auth methodAuth = handlerMethod.getMethodAnnotation(Auth.class);
        Auth auth;

        if (classAuth == null) {
            // 클래스에 어노테이션이 없으면 메소드 어노테이션 참고.
            auth = methodAuth;
        } else {
            if (methodAuth != null) {
                // 클래스와 메소드가 둘 다 있을경우, 메소드 어노테이션 참고.
                auth = methodAuth;
            } else {
                // 메소드 어노테이션이 없으면 클래스 어노테이션 참고.
                auth = classAuth;
            }
        }
        // <-- auth x -->

        // method 에 @Auth 가 없는 경우, 즉 인증이 필요 없는 요청.
        if (auth == null) {
            return true;
        }
        // <-- auth o -->

        // 5. @Auth가 있는 경우이므로, 세션이 있는지 체크
        HttpSession session = request.getSession();
        if (session == null) {
            // 로그인 화면으로 이동
            response.sendRedirect(request.getContextPath() + "/members/login");
            return false;
        }

        // 6. 세션이 존재하면 유효한 유저인지 확인
        MemberSession authUser = (MemberSession) session.getAttribute("member");
        if (authUser == null) {
            response.sendRedirect(request.getContextPath() + "/members/login");
            return false;
        }

        // 7. 권한 비교
        boolean isAuth = false;
        for (String s : authUser.getMemberLevel()){
            Auth.Role userRole = Auth.Role.valueOf(s);
            if (userRole == auth.role()){
                isAuth = true;
            }
        }
        if (!isAuth){
            throw new IllegalStateException("해당 권한이 없습니다.");
        }

        // 8. 접근허가
        return true;
    }


}
