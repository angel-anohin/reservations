package com.example.backend.repository;

import com.example.backend.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    Optional<Reservation> findByIdAndDeletedFalse(Long id);

    Page<Reservation> findAll(Pageable pageable);
}
