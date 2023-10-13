package br.com.macedolg.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.macedolg.todolist.repo.iUserRepo;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private iUserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/users/tasks/")) {
            // Pegar a autenticação (usuario e senha)
            var auth = request.getHeader("Authorization");

            var authEncode = auth.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncode);

            var authString = new String(authDecode);

            String[] credencials = authString.split(":");
            String username = credencials[0];
            String password = credencials[1];

            // Validar usuário
            var userAuth = this.userRepo.findByUsername(username);
            if (userAuth == null) {
                response.sendError(401, "Usuario sem autorização");
            }
            // Verificar Senha
            else {
                var verifyPassword = BCrypt.verifyer().verify(password.toCharArray(), userAuth.getPassword());
                if (verifyPassword.verified) {
                    //Segue Viagem
                    request.setAttribute("idUser", userAuth.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "Senha Inválida");
                }
            }
          // Segue viagem
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
