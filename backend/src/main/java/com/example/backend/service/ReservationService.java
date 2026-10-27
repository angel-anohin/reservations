package com.example.backend.service;

import com.example.backend.dto.ReservationGetAllResponseDto;
import com.example.backend.dto.ReservationSaveDto;
import com.example.backend.dto.ReservationUpdateDto;
import com.example.backend.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationService {

    void saveReservation(ReservationSaveDto reservationSaveDto);

    List<Reservation> fetchReservationList();

    Page<Reservation> fetchAllReservations(Pageable pageable);

    String updateReservation(ReservationUpdateDto reservationUpdateDto, Integer id);

    ReservationGetAllResponseDto findReservationById(Integer id);

    void softDelete(Long id);
}
