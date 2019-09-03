package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.util.List;

public interface ShipRepo extends JpaRepository<Ship, Long> {

    List<Ship> findAll();

    Ship findAllById(Long id);




}
