package com.example.destinationbucketlist.config;

import com.example.destinationbucketlist.service.JwtUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final List<RequestMatcher> requestMatchers =
            Arrays.asList(new RequestMatcher[]{new AntPathRequestMatcher("/publicDestinations", HttpMethod.GET.name()),
                    new AntPathRequestMatcher("/api/auth/login", HttpMethod.POST.name()),
                    new AntPathRequestMatcher("/api/auth/register/confirm/**", HttpMethod.GET.name()),
                    new AntPathRequestMatcher("/api/auth/register", HttpMethod.POST.name()),
            });

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {

            response.setStatus(HttpServletResponse.SC_OK);

            return;
        }

        if (requestMatchers.stream().anyMatch(p -> p.matches(request))) {
            // ... this filter should run
        } else {
            try {
                String jwt = parseJwt(request);
                if (jwt != null) {
                    if (jwtTokenUtil.validateJwtToken(jwt)) {
                        String username = jwtTokenUtil.getUserNameFromJwtToken(jwt);

                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        // return proper error so that frontend can invalidate token and redirect to login
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token invalid");

                        return;
                    }
                } else {
                    logger.error("Missing JWT token");

                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing JWT Token");

                    return;
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication: {}", e);
            }
        }

        chain.doFilter(request, response);
    }

}
