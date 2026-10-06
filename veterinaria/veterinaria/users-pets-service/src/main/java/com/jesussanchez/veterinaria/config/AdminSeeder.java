package com.jesussanchez.veterinaria.config;

import com.jesussanchez.veterinaria.entity.Rol;
import com.jesussanchez.veterinaria.repository.UsuarioRepository;
import com.jesussanchez.veterinaria.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationRunner {

    private final UsuarioService usuarioService;
    private final UsuarioRepository repository;

    @Value("${app.admin.nombre:Administrador}")
    private String nombre;

    @Value("${app.admin.email:admin@veterinaria.com}")
    private String email;

    @Value("${app.admin.password:admin123}")
    private String password;

    @Override
    public void run(ApplicationArguments args) {
        if (!repository.existsByEmail(email.trim().toLowerCase())) {
            usuarioService.crear(nombre, null, email, password, Rol.ADMIN);
            log.info("Usuario administrador inicial creado correctamente.");
        }
    }
}