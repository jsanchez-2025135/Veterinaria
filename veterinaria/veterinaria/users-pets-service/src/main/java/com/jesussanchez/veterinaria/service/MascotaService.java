package com.jesussanchez.veterinaria.service;

import com.jesussanchez.veterinaria.entity.Mascota;
import com.jesussanchez.veterinaria.entity.Usuario;
import com.jesussanchez.veterinaria.exception.ApiException;
import com.jesussanchez.veterinaria.repository.MascotaRepository;
import com.jesussanchez.veterinaria.repository.UsuarioRepository;
import com.jesussanchez.veterinaria.request.MascotaRequest;
import com.jesussanchez.veterinaria.response.MascotaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;

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

        Mascota mascota = Mascota.builder()
                .nombre(request.getNombre())
                .especie(request.getEspecie())
                .raza(request.getRaza())
                .edad(request.getEdad())
                .cliente(cliente)
                .build();

        Mascota guardada = mascotaRepository.save(mascota);
        return mapearAResponse(guardada);
    }

    public MascotaResponse obtenerPorId(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Mascota no encontrada con ID: " + id));
        return mapearAResponse(mascota);
    }

    private MascotaResponse mapearAResponse(Mascota mascota) {
        return MascotaResponse.builder()
                .id(mascota.getId())
                .nombre(mascota.getNombre())
                .especie(mascota.getEspecie())
                .raza(mascota.getRaza())
                .edad(mascota.getEdad())
                .clienteId(mascota.getCliente().getId())
                .nombreCliente(mascota.getCliente().getNombre())
                .build();
    }
}