package com.example.backend.service;

import com.example.backend.dto.ReservationGetAllResponseDto;
import com.example.backend.dto.ReservationSaveDto;
import com.example.backend.dto.ReservationUpdateDto;
import com.example.backend.dto.UserRequest;
import com.example.backend.model.Reservation;
import com.example.backend.model.User;
import com.example.backend.repository.ReservationRepository;
import com.example.backend.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;

    private EmailService emailService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.emailService = emailService;
    }
    @Override
    public void saveReservation(@RequestBody ReservationSaveDto reservationSaveDto) {
        Reservation reservation = new Reservation();
        reservation.setName(reservationSaveDto.getName());
        reservation.setEmail(reservationSaveDto.getEmail());
        reservation.setMobileNumber(reservationSaveDto.getMobileNumber());
        reservation.setPeopleCount(reservationSaveDto.getPeopleCount());
        reservation.setDateTime(reservationSaveDto.getDateTime());
        reservation.setAdditionalInformation(reservationSaveDto.getAdditionalInformation());
        reservationRepository.save(reservation);


        try {
            emailService.sendReservationEmail(
                    reservation.getEmail(),
                    reservation.getName(),
                    reservation.getDateTime().toString(),
                    reservation.getId()
            );
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Reservation> fetchReservationList() {
        return (List<Reservation>)reservationRepository.findAll();
    }

    @Override
    public Page<Reservation> fetchAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    @Override
    public String updateReservation(ReservationUpdateDto reservationUpdateDto, Integer id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setName(reservationUpdateDto.getName());
        reservation.setEmail(reservationUpdateDto.getEmail());
        reservation.setMobileNumber(reservationUpdateDto.getMobileNumber());
        reservation.setPeopleCount(reservationUpdateDto.getPeopleCount());
        reservation.setDateTime(reservationUpdateDto.getDateTime());
        reservation.setAdditionalInformation(reservationUpdateDto.getAdditionalInformation());
        reservation.setUpdatedBy(reservationUpdateDto.getUpdatedBy());
        reservation.setDeleted(false);
        reservationRepository.save(reservation);
        return "Reservation updated successfully";
    }

    @Override
    public ReservationGetAllResponseDto findReservationById(Integer id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        return new ReservationGetAllResponseDto(reservation.getId(),
                reservation.getName(),
                reservation.getEmail(),
                reservation.getMobileNumber(),
                reservation.getPeopleCount(),
                reservation.getDateTime(),
                reservation.getAdditionalInformation(),
                reservation.getUpdatedBy(),
                reservation.getDeletedBy(),
                reservation.isDeleted());
    }

    @Override
    public void softDelete(Long id) {
        Reservation reservation = reservationRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setDeleted(true);
        reservationRepository.save(reservation);
    }


}
