package es.upm.dit.isst.mystayapi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;

import es.upm.dit.isst.mystayapi.model.Cliente;
import es.upm.dit.isst.mystayapi.repository.ClienteRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final ClienteRepository clienteRepository;
    private final UserAuthenticationProvider userAuthenticationProvider = new UserAuthenticationProvider();

    public JwtAuthFilter(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] authElements = header.split(" ");

            if (authElements.length == 2
                    && "Bearer".equals(authElements[0])) {
                try {
                    String dni = userAuthenticationProvider.validateToken(authElements[1]);
                    Optional<Cliente> cliente =  clienteRepository.findByDNI(dni);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(cliente,null, Collections.emptyList());
                    
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JWTVerificationException e) {
                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpServletResponse.getWriter().write("Invalid JWT Token");
                    return;
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    httpServletResponse.getWriter().write("Error ocurred");
                    throw e;
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
