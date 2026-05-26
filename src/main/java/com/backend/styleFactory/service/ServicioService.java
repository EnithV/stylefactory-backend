package com.backend.styleFactory.service;

import com.backend.styleFactory.DTO.ServicioResponseDTO;
import com.backend.styleFactory.model.Servicio;
import com.backend.styleFactory.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioService {

    @Autowired
    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository){
        this.servicioRepository = servicioRepository;
    }

    public List<ServicioResponseDTO> findAll(){
        return servicioRepository.findAll()
                .stream().map(ServicioResponseDTO::desde)
                .collect(Collectors.toList());
    }

    public ServicioResponseDTO findById(Long id){
        Servicio servicio = servicioRepository.findById(id).orElse(null);
        if (servicio == null) return null;
        return ServicioResponseDTO.desde(servicio);
    }

    public ServicioResponseDTO save(ServicioResponseDTO dto){
            if (servicioRepository.existsByNombre(dto.getNombre())){
                throw new RuntimeException("Ya existe un servicio con ese nombre");
            }
            Servicio servicio = new Servicio(
                    dto.getNombre(),
                    dto.getDescripcion(),
                    dto.getUrlImagen(),
                    dto.getPrecio(),
                    dto.getTipoServicio(),
                    true
            );
        return ServicioResponseDTO.desde(servicioRepository.save(servicio));
    }

    public ServicioResponseDTO update(Long id, ServicioResponseDTO dto){
        Servicio existente = servicioRepository.findById(id).orElse(null);
        if (existente == null) return null;
        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setUrlImagen(dto.getUrlImagen());
        existente.setPrecio(dto.getPrecio());
        existente.setTipoServicio(dto.getTipoServicio());
        existente.setEstado(dto.isEstado());
        return ServicioResponseDTO.desde(servicioRepository.save(existente));
    }

    public void delete(Long id){
        servicioRepository.deleteById(id);
    }
}
