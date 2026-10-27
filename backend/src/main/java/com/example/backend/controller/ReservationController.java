package com.example.backend.controller;

import com.example.backend.dto.ReservationSaveDto;
import com.example.backend.model.Reservation;
import com.example.backend.repository.ReservationRepository;
import com.example.backend.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://192.168.56.1:3002", "http://localhost:3002","http://127.0.0.1:3002"})
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationRepository reservationRepository;

    private ReservationService reservationService;

    public ReservationController(ReservationRepository reservationRepository, ReservationService reservationService) {
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @PostMapping("/add")
    public ResponseEntity<String>saveReservation(@Valid @RequestBody ReservationSaveDto reservationSaveDto) {
        reservationService.saveReservation(reservationSaveDto);
        return ResponseEntity.ok("Reservation saved");
    }

    @GetMapping("/all")
    public Iterable<Reservation> getAllReservations() {
        return reservationService.fetchReservationList();
    }

    @GetMapping("/allall")
    public Page<Reservation> getReservations(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return reservationService.fetchAllReservations(PageRequest.of(page, size));
    }
}
