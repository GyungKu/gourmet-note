package com.gk.my_secret_review.common.filter;

import com.gk.my_secret_review.user.vo.LoginUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        LoginUser login = (LoginUser) session.getAttribute("login");
        if (login != null) {
            setAuthenticate(login);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthenticate(LoginUser loginUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(createAuthentication(loginUser));
    }

    private Authentication createAuthentication(LoginUser loginUser) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(loginUser.role().getAuthority()));
        return new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
    }
}
