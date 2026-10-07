package com.jesussanchez.veterinaria.service;

import com.jesussanchez.veterinaria.entity.Especie;
import com.jesussanchez.veterinaria.entity.Mascota;
import com.jesussanchez.veterinaria.entity.Usuario;
import com.jesussanchez.veterinaria.exception.ApiException;
import com.jesussanchez.veterinaria.repository.MascotaRepository;
import com.jesussanchez.veterinaria.repository.UsuarioRepository;
import com.jesussanchez.veterinaria.request.MascotaRequest;
import com.jesussanchez.veterinaria.response.MascotaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;

    // Constructor manual para inyección de dependencias
    public MascotaService(MascotaRepository mascotaRepository, UsuarioRepository usuarioRepository) {
        this.mascotaRepository = mascotaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<MascotaResponse> obtenerPorCliente(String emailCliente) {
        Usuario cliente = usuarioRepository.findByEmail(emailCliente)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        return mascotaRepository.findByClienteId(cliente.getId()).stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public MascotaResponse registrarMascota(MascotaRequest request, String emailUsuarioAutenticado, boolean esAdmin) {
        Usuario cliente;

        if (esAdmin && request.getClienteId() != null) {
            cliente = usuarioRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El cliente especificado no existe"));
        } else {
            cliente = usuarioRepository.findByEmail(emailUsuarioAutenticado)
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Usuario autenticado no encontrado"));
        }

        // Creamos la mascota de forma manual (sin usar .builder())
        Mascota mascota = new Mascota();
        mascota.setNombre(request.getNombre());

        // Si request.getEspecie() es String, lo convertimos a Enum. Si es Especie, quita el valueOf().
        if (request.getEspecie() != null) {
            mascota.setEspecie(request.getEspecie());
        }

        mascota.setRaza(request.getRaza());
        mascota.setEdad(request.getEdad());
        mascota.setCliente(cliente);

        Mascota guardada = mascotaRepository.save(mascota);
        return mapearAResponse(guardada);
    }

    public MascotaResponse obtenerPorId(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Mascota no encontrada con ID: " + id));
        return mapearAResponse(mascota);
    }

    private MascotaResponse mapearAResponse(Mascota mascota) {
        // Mapeo manual hacia el Response (sin usar .builder())
        MascotaResponse response = new MascotaResponse();
        response.setId(mascota.getId());
        response.setNombre(mascota.getNombre());

        // Aquí corregimos el .name() con paréntesis y validación de nulos
        if (mascota.getEspecie() != null) {
            response.setEspecie(mascota.getEspecie().name());
        }

        response.setRaza(mascota.getRaza());
        response.setEdad(mascota.getEdad());

        if (mascota.getCliente() != null) {
            response.setClienteId(mascota.getCliente().getId());
            response.setNombreCliente(mascota.getCliente().getNombre());
        }

        return response;
    }


}