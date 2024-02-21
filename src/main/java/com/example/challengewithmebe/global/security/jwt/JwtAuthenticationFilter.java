package com.example.challengewithmebe.global.security.jwt;

import com.example.challengewithmebe.global.exception.auth.NotValidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtProvider.resolveToken((HttpServletRequest) request);
        if(token != null && jwtProvider.validateTokenExpirations(token)){
            Authentication authentication = jwtProvider.getAutentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }else throw new NotValidTokenException();
        chain.doFilter(request, response);
    }
}
