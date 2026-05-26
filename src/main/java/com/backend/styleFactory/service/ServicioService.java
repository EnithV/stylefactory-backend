package com.backend.styleFactory.service;

import com.backend.styleFactory.DTO.ServiceResponseDTO;
import com.backend.styleFactory.model.Servicio;
import com.backend.styleFactory.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioService {

    @Autowired
    private final ServicioRepository serviceRepository;

    public ServicioService(ServicioRepository serviceRepository){
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceResponseDTO> findAll(){
        return serviceRepository.findAll()
                .stream().map(ServiceResponseDTO::desde)
                .collect(Collectors.toList());
    }

    public ServiceResponseDTO findById(Long id){
        Servicio servicio = serviceRepository.findById(id).orElse(null);
        if (servicio == null) return null;
        return ServiceResponseDTO.desde(servicio);
    }

    //public ServiceResponseDTO save(ServiceResponseDTO dto){
       // Usuario usuario = usuarioRepository.findById((dto.getUsuarioId()).orElse(null)
    //}

//    public ServiceResponseDTO update(Long id, ServiceResponseDTO dto){
//        Servicio existente = serviceRepository.findById(id).orElse(null);
//        if (existente == null) return null;
//        existente.setNombre(dto.getNombre());
//        existente.setDescripcion(dto.getDescripcion());
//        existente.setUrlImagen(dto.getUrlImagen());
//        existente.setPrecio(dto.getPrecio());
//        existente.setTipoServico(dto.getTipoServico());
//        return serviceRepository.save(existente);
//    }

    public void delete(Long id){
        serviceRepository.deleteById(id);
    }
}
