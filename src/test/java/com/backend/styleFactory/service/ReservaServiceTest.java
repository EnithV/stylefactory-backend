package com.backend.styleFactory.service;

import com.backend.styleFactory.DTO.ReservaRequestDTO;
import com.backend.styleFactory.DTO.ReservaResponseDTO;
import com.backend.styleFactory.model.*;
import com.backend.styleFactory.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para {@link ReservaService} utilizando Mockito.
 * Verifica la creación de reservas cuando las entidades existen
 * y el lanzamiento de excepciones cuando alguna no se encuentra.
 */
@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock private ReservaRepository reservaRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private EmpleadoRepository empleadoRepository;
    @Mock private ServicioRepository servicioRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Usuario usuario;
    private Empleado empleado;
    private Servicio servicio;
    private ReservaRequestDTO requestDTO;

    /**
     * Inicializa los objetos de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        usuario = new Usuario("Juan", "juan@mail.com", "123456789", "pass", RolUsuario.CLIENTE, true);
        usuario.setId(1L);

        empleado = new Empleado(usuario, "Corte", true, "url_foto");
        empleado.setId(1L);

        servicio = new Servicio("Corte Clásico", "Corte con tijera", "url_imagen", 20.0, "Corte", 45, true);
        servicio.setIdServicio(1L);

        requestDTO = new ReservaRequestDTO();
        requestDTO.setFecha(LocalDate.now().plusDays(1));
        requestDTO.setHora(LocalTime.of(10, 0));
        requestDTO.setEstado("PENDIENTE");
        requestDTO.setUsuarioId(1L);
        requestDTO.setEmpleadoId(1L);
        requestDTO.setServicioId(1L);
    }

    /**
     * Prueba que se pueda crear una reserva cuando todas las entidades relacionadas existen.
     */
    @Test
    void save_DeberiaCrearReserva_CuandoEntidadesExisten() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));
        when(reservaRepository.findByEmpleado_IdAndFecha(1L, requestDTO.getFecha()))
                .thenReturn(Collections.emptyList());
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> {
            Reserva r = invocation.getArgument(0);
            r.setId(1L);
            return r;
        });

        ReservaResponseDTO response = reservaService.save(requestDTO);

        assertNotNull(response);
        assertEquals("PENDIENTE", response.getEstado());
        assertEquals("Juan", response.getNombreUsuario());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    /**
     * Prueba que se lance una excepción cuando el usuario asociado no existe.
     */
    @Test
    void save_DeberiaLanzarExcepcion_CuandoUsuarioNoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservaService.save(requestDTO));
        assertTrue(exception.getMessage().contains("Usuario no encontrado"));
    }

    /**
     * Prueba que se rechace una reserva si el servicio no termina antes de las 8 p.m.
     */
    @Test
    void save_DeberiaLanzarExcepcion_CuandoServicioExcedeCierreAtencion() {
        servicio.setDuracionMinutos(150);
        requestDTO.setHora(LocalTime.of(18, 0));

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservaService.save(requestDTO));
        assertTrue(exception.getMessage().contains("8:00 p.m."));
    }

    /**
     * Prueba que se acepte una reserva cuando la duración cabe antes del cierre.
     */
    @Test
    void save_DeberiaCrearReserva_CuandoDuracionCabeEnHorarioAtencion() {
        servicio.setDuracionMinutos(120);
        requestDTO.setHora(LocalTime.of(18, 0));

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));
        when(reservaRepository.findByEmpleado_IdAndFecha(1L, requestDTO.getFecha()))
                .thenReturn(Collections.emptyList());
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> {
            Reserva r = invocation.getArgument(0);
            r.setId(1L);
            return r;
        });

        ReservaResponseDTO response = reservaService.save(requestDTO);

        assertNotNull(response);
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    void save_DeberiaLanzarExcepcion_CuandoHaySolapamientoConOtraReserva() {
        Reserva existente = new Reserva(
                requestDTO.getFecha(),
                LocalTime.of(10, 0),
                "CONFIRMADA",
                usuario,
                empleado,
                servicio);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));
        when(reservaRepository.findByEmpleado_IdAndFecha(1L, requestDTO.getFecha()))
                .thenReturn(Collections.singletonList(existente));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservaService.save(requestDTO));
        assertTrue(exception.getMessage().contains("ya tiene una reserva"));
    }
}