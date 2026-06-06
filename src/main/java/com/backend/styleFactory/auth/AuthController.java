package com.backend.styleFactory.auth;

import com.backend.styleFactory.model.RolUsuario;
import com.backend.styleFactory.model.Usuario;
import com.backend.styleFactory.repository.UsuarioRepository;
import com.backend.styleFactory.security.JwtUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador de autenticación.
 * Expone los endpoints públicos /auth/register y /auth/login.
 */
@RestController
@RequestMapping("/auth")
@SecurityRequirements
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Registra un nuevo usuario.
     * Verifica que el correo no esté en uso y guarda la contraseña codificada.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {

        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "El correo ya está registrado"));
        }

        Usuario usuario = new Usuario(
                request.getNombre(),
                request.getCorreo(),
                request.getTelefono() != null ? request.getTelefono() : "",
                passwordEncoder.encode(request.getContrasena()),
                RolUsuario.CLIENTE,
                true
        );

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of(
                "mensaje", "Usuario registrado exitosamente",
                "id", usuario.getId(),
                "correo", usuario.getCorreo(),
                "rol", usuario.getRol()
        ));
    }

    /**
     * Autentica al usuario y devuelve un token JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getContrasena()
                )
        );

        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = jwtUtil.generateToken(usuario);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "id", usuario.getId(),
                "correo", usuario.getCorreo(),
                "rol", usuario.getRol(),
                "nombre", usuario.getNombre()
        ));
    }
}