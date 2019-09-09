package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {

    private final ShipRepository shipRepository;

    @Autowired
    public ShipServiceImpl(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }


    public List<Ship> getShips(
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
            List<Ship> shipList
            ) {



        if (name != null)
            shipList = getShipInfosByName(name, shipList);

        if (planet != null)
            shipList = getShipInfosByPlanet(planet, shipList);

        if (shipType != null)
            shipList = getShipInfosByShipType(shipType, shipList);

        if (after != null)
            shipList = getShipInfosByAfter(after, shipList);

        if (before != null)
            shipList = getShipInfosByBefore(before, shipList);

        if (isUsed != null)
            shipList = getShipInfosByIsUsed(isUsed, shipList);

        if (minSpeed != null)
            shipList = getShipInfosByMinSpeed(minSpeed, shipList);

        if (maxSpeed != null)
            shipList = getShipInfosByMaxSpeed(maxSpeed, shipList);

        if (minCrewSize != null)
            shipList = getShipInfosByMinCrewSize(minCrewSize, shipList);

        if (maxCrewSize != null)
            shipList = getShipInfosByMaxCrewSize(maxCrewSize, shipList);

        if (minRating != null)
            shipList = getShipInfosByMinRating(minRating, shipList);

        if (maxRating != null)
            shipList = getShipInfosByMaxRating(maxRating, shipList);


        return shipList;
    }







    private List<Ship> getShipInfosByName(String name, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getName().contains(name)) {
                result.add(ship);
            }
        }
        return result;
    }

    private List<Ship> getShipInfosByPlanet(String planet, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getPlanet().contains(planet)) {
                result.add(ship);
            }
        }
        return result;
    }

    private List<Ship> getShipInfosByShipType(ShipType type, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getShipType() == type) {
                result.add(ship);
            }
        }
        return result;
    }

    private List<Ship> getShipInfosByAfter(Long after, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getProdDate().getTime() >= after) {
                result.add(ship);
            }
        }
        return result;
    }

    private List<Ship> getShipInfosByBefore(Long before, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getProdDate().getTime() <= before) {
                result.add(ship);
            }
        }
        return result;
    }

    private List<Ship> getShipInfosByIsUsed(Boolean isUsed, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.isUsed() == isUsed) {
                result.add(ship);
            }
        }
        return result;
    }


    private List<Ship> getShipInfosByMinSpeed(Double minSpeed, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getSpeed() >= minSpeed) {
                result.add(ship);
            }
        }
        return result;
    }

    private List<Ship> getShipInfosByMaxSpeed(Double maxSpeed, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getSpeed() <= maxSpeed) {
                result.add(ship);
            }
        }
        return result;
    }

    private List<Ship> getShipInfosByMinCrewSize(Integer minCrewSize, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getCrewSize() >= minCrewSize) {
                result.add(ship);
            }
        }
        return result;
    }

    private List<Ship> getShipInfosByMaxCrewSize(Integer maxCrewSize, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getCrewSize() <= maxCrewSize) {
                result.add(ship);
            }
        }
        return result;
    }

    private List<Ship> getShipInfosByMinRating(Double minRating, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getRating() >= minRating) {
                result.add(ship);
            }
        }
        return result;
    }

    private List<Ship> getShipInfosByMaxRating(Double maxRating, List<Ship> ships) {
        List<Ship> result = new ArrayList<>();
        for (Ship ship : ships) {
            if (ship.getRating() <= maxRating) {
                result.add(ship);
            }
        }
        return result;
    }

    public List<Ship> getShipAndInfosByOrder(ShipOrder order) {

        List<Ship> ships = shipRepository.findAll();

        if (order == ShipOrder.ID) {
            ships.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
        } else if (order == ShipOrder.DATE) {

            ships.sort((o1, o2) -> {

                Calendar calendar1 = new GregorianCalendar();
                calendar1.setTime(o1.getProdDate());
                Integer year1 = calendar1.get(Calendar.YEAR);

                Calendar calendar2 = new GregorianCalendar();
                calendar2.setTime(o2.getProdDate());
                Integer year2 = calendar2.get(Calendar.YEAR);

                return year1 - year2;

            });

        } else if (order == ShipOrder.SPEED) {
            ships.sort((o1, o2) -> {
                if (o1.getSpeed() > o2.getSpeed())
                    return 1;
                else if (o1.getSpeed().equals(o2.getSpeed()))
                    return 0;
                else
                    return -1;
            });
        } else if (order == ShipOrder.RATING) {
            ships.sort((o1, o2) -> {
                if (o1.getRating() > o2.getRating())
                    return 1;
                else if (o1.getRating().equals(o2.getRating()))
                    return 0;
                else
                    return -1;
            });
        }

        return ships;
    }


    public List<Ship> getShipInfosByPage(Integer pageNumber, Integer pageSize, List<Ship> ships) {
        int skip = pageNumber * pageSize;
        List<Ship> result = new ArrayList<>();
        for (int i = skip; i < Math.min(skip + pageSize, ships.size()); i++) {
            result.add(ships.get(i));
        }
        return result;
    }

    @Override
    public List<Ship> getShipsAll() {
        return shipRepository.findAll();
    }

    @Override
    public Ship save(Ship ship) {
        return shipRepository.save(ship);
    }

    @Override
    public Ship findAllById(Long id) {
        return shipRepository.findAllById(id);
    }

    private Ship getShipInfosById(long id, List<Ship> ships) {
        return ships.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public Boolean testShipUpdate(Ship ship){


        if(ship.getName()!= null && ship.getName().isEmpty())
            return false;

        if(ship.getPlanet()!= null && ship.getPlanet().isEmpty())
            return false;

        if(ship.getCrewSize() != null && (ship.getCrewSize() < 1 || ship.getCrewSize() > 9999))
            return false;

        if(ship.getProdDate() != null ) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(ship.getProdDate());
            Integer year = calendar.get(Calendar.YEAR);
            if(year < 2800 || year > 3019)
                return false;
        }

        if (ship.getSpeed() != null && (ship.getSpeed() < 0.0 || ship.getSpeed() > 1.0 ))
            return false;

        return true;
    }

    @Override
    public void delete(Ship shipFromDb) {
        shipRepository.delete(shipFromDb);
    }

    @Override
    public Ship getRating(Ship ship) {
        double k;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(ship.getProdDate());

        Integer year = calendar.get(Calendar.YEAR);


        if (ship.isUsed())
            k = 0.5;
        else
            k = 1.0;

        Double rating = 80 * ship.getSpeed() * k / (3019 - year + 1);
        rating = Math.round(rating * 100.00 ) / 100.00;

        ship.setRating(rating);
        return ship;
    }


}
