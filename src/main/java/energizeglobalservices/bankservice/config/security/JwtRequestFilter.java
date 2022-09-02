package energizeglobalservices.bankservice.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import energizeglobalservices.bankservice.config.exception.ErrorContent;
import energizeglobalservices.bankservice.config.exception.InvalidTokenException;
import energizeglobalservices.bankservice.config.exception.TokenExpiredException;
import energizeglobalservices.bankservice.domain.ATMMachine;
import energizeglobalservices.bankservice.service.ATMMachineService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ATMMachineService atmService;
    private final MessageSource messageSource;

    private static String parseErrorMessage(String errorMessage) throws JsonProcessingException {
        String[] errorMessageItems = errorMessage.split("#");
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(ErrorContent.builder()
                .message(errorMessageItems[0])
                .code(Integer.parseInt(errorMessageItems[1]))
                .fields(Collections.emptyList())
                .build());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        log.info("JWT Request Filter -> " + request.getRequestURI());
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenProvider.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");

                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType("application/json");
                response.getOutputStream().write(parseErrorMessage(messageSource.getMessage(
                        InvalidTokenException.class.getName(),
                        null, Locale.getDefault()
                )).getBytes("UTF-8"));
                return;

            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.getOutputStream().write(parseErrorMessage(messageSource.getMessage(
                        TokenExpiredException.class.getName(),
                        null, Locale.getDefault()
                )).getBytes("UTF-8"));
                return;
            }

        } else {
            logger.warn("JWT Token does not begin with Bearer String (" + requestTokenHeader + ")");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            ATMMachine atm = this.atmService.loadUserByUsername(username);
            /*
            Todo: We are validating token duplicate
             */
            OnlineATM onlineUser = new OnlineATMImpl(atm.getId(), atm.getUsername(),
                    atm.getPassword(),atm.getAuthorities());
            if (jwtTokenProvider.validateToken(jwtToken, atm)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(onlineUser, null, onlineUser.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
        log.info("JWT Request Filter -> Done : " + response.getStatus());
    }
}
