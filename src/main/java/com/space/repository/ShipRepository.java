package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {

    List<Ship> findAll();

    Ship findAllById(Long id);

    List<Ship> findAllByOrderByIdAsc();

    List<Ship> findAllByOrderBySpeedAsc();

    List<Ship> findAllByOrderByProdDateAsc();

    List<Ship> findAllByOrderByRatingAsc();



}
