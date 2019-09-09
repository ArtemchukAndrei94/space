package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface ShipService {

    List<Ship> getShips(String name,
                        String planet,
                        ShipType shipType,
                        Long after,
                        Long before,
                        Boolean isUsed,
                        Double minSpeed,
                        Double maxSpeed,
                        Integer minCrewSize,
                        Integer maxCrewSize,
                        Double minRating,
                        Double maxRating,
                        List<Ship> shipList);

    List<Ship> getShipAndInfosByOrder(ShipOrder order);

    List<Ship> getShipInfosByPage(Integer pageNumber, Integer pageSize, List<Ship> shipList);

    List<Ship> getShipsAll();

    Ship save(Ship ship);

    Ship findAllById(Long id);

    Boolean testShipUpdate(Ship ship);

    void delete(Ship shipFromDb);

    Ship getRating(Ship ship);
}
