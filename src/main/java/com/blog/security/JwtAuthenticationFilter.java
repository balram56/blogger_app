package com.blog.security;

import com.blog.exception.BlogApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //inject dependency
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //get JWT(token) from HttpRequest
        String token = getJWTfromRequest(request);

        //validate token
        try {
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)){

                //get username from token
                String username = tokenProvider.getUsernameFromJWT(token);

                //load user associated with token

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //set Spring security
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (BlogApiException e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request, response);
    }

    // Bearer <accessToken>
    private String getJWTfromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Autheriation");

        if (StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
