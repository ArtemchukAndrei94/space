package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rest")
public class ShipController {

    private final ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @RequestMapping(value = "/ships", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Ship> getShipsList(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "planet", required = false) String planet,
            @RequestParam(name = "shipType", required = false) ShipType shipType,
            @RequestParam(name = "after", required = false) Long after,
            @RequestParam(name = "before", required = false) Long before,
            @RequestParam(name = "isUsed", required = false) Boolean isUsed,
            @RequestParam(name = "minSpeed", required = false) Double minSpeed,
            @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(name = "minRating", required = false) Double minRating,
            @RequestParam(name = "maxRating", required = false) Double maxRating,
            @RequestParam(name = "order", required = false, defaultValue = "ID") ShipOrder order,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize

    ) {

        List<Ship> shipList = shipService.getShipAndInfosByOrder(order);

        shipList = shipService.getShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, shipList);

        shipList = shipService.getShipInfosByPage(pageNumber, pageSize, shipList);

        return  shipList;
    }



    @RequestMapping(value = "/ships/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Integer getCount(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "planet", required = false) String planet,
            @RequestParam(name = "shipType", required = false) ShipType shipType,
            @RequestParam(name = "after", required = false) Long after,
            @RequestParam(name = "before", required = false) Long before,
            @RequestParam(name = "isUsed", required = false) Boolean isUsed,
            @RequestParam(name = "minSpeed", required = false) Double minSpeed,
            @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(name = "minRating", required = false) Double minRating,
            @RequestParam(name = "maxRating", required = false) Double maxRating
    ) {

        List<Ship> shipList = shipService.getShipsAll();
        shipList = shipService.getShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, shipList);

        return  shipList.size();

    }


    @RequestMapping(value = "/ships", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> create(@RequestBody Ship ship) {

        if (ship.isUsed() == null)
            ship.setIsUsed(false);

        if (ship.getSpeed() != null)
            ship.setSpeed(Math.round(ship.getSpeed() * 100.00 ) / 100.00);

        if (ship.getName() != null && ship.getPlanet() != null && ship.getProdDate() != null  && ship.getSpeed() != null && ship.getCrewSize() != null)
            if (ship.getName().length() <= 50 && !ship.getName().equals(""))
                if (ship.getPlanet().length() <= 50 && !ship.getPlanet().equals(""))
                    if (ship.getCrewSize() >= 1 && ship.getCrewSize() <= 9999)
                        if (ship.getSpeed() >= 0.01 && ship.getSpeed() <= 0.99)
                            if (ship.getProdDate().getTime() >= 0 && ship.getProdDate().getTime() >= 26192235600000L && ship.getProdDate().getTime() <= 33103198800000L) {

                                ship = shipService.getRating(ship);

                                return new ResponseEntity<Ship>(shipService.save(ship), HttpStatus.OK);
                            }


        return new ResponseEntity<Ship>(HttpStatus.BAD_REQUEST);
    }



    @RequestMapping(value = "/ships/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getShipId(@PathVariable("id")  Long id) {

        if (id <= 0)
            return new ResponseEntity<Ship>(HttpStatus.BAD_REQUEST);

        List<Ship> ships = shipService.getShipsAll();

        Ship ship = shipService.findAllById(id);
        if (ship == null)
            return new ResponseEntity<Ship>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Ship>(ship, HttpStatus.OK);
    }



    @RequestMapping(value = "/ships/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                       @RequestBody(required = false) Ship ship) {

        List<Ship> shipList = shipService.getShipsAll();

        if (shipList.size() < id)
            return new ResponseEntity<Ship>(HttpStatus.NOT_FOUND);


        if(id == null || id <= 0  || !shipService.testShipUpdate(ship))
            return new ResponseEntity<Ship>(HttpStatus.BAD_REQUEST);


        Ship shipFromDb = shipService.findAllById(id);

        if (shipFromDb == null)
            return new ResponseEntity<Ship>(HttpStatus.NOT_FOUND);

        shipFromDb.setId(id);

        if (ship.getName() != null)
            shipFromDb.setName(ship.getName());

        if (ship.getPlanet() != null)
            shipFromDb.setPlanet(ship.getPlanet());

        if (ship.getShipType() != null)
            shipFromDb.setShipType(ship.getShipType());

        if (ship.getProdDate() != null)
            shipFromDb.setProdDate(ship.getProdDate());

        if (ship.getName() != null)
            shipFromDb.setName(ship.getName());

        if (ship.isUsed() != null)
            shipFromDb.setIsUsed(ship.isUsed());

        if (ship.getSpeed() != null)
            shipFromDb.setSpeed(ship.getSpeed());

        if (ship.getCrewSize() != null)
            shipFromDb.setCrewSize(ship.getCrewSize());


        shipFromDb = shipService.getRating(shipFromDb);

        ship = shipService.save(shipFromDb);


        return new ResponseEntity<Ship>(ship, HttpStatus.OK);

    }


   
    @RequestMapping(value = "/ships/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        if (id <= 0)
            return new ResponseEntity<Ship>(HttpStatus.BAD_REQUEST);

        Ship shipFromDb = shipService.findAllById(id);

        if (shipFromDb == null)
            return new ResponseEntity<Ship>(HttpStatus.NOT_FOUND);

        shipService.delete(shipFromDb);
        return new ResponseEntity<Ship>(HttpStatus.OK);

    }




}
