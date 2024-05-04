package es.upm.dit.isst.mystayapi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import es.upm.dit.isst.mystayapi.model.Cliente;
import es.upm.dit.isst.mystayapi.repository.ClienteRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
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
                    DecodedJWT decodedJWT = userAuthenticationProvider.validateToken(authElements[1]);
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(decodedJWT.getClaim("role").asString());
                    List<SimpleGrantedAuthority> authorities = Collections.singletonList(authority);

                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null,"", authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        Optional<Cliente> cliente =  clienteRepository.findByDNI(decodedJWT.getSubject());
                    
                        if (cliente.isEmpty()){
                            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            httpServletResponse.getWriter().write("Invalid DNI");
                            return;
                        }
    
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(cliente.get(),"", authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

          
                    
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
