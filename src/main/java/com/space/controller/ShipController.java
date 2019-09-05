package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class ShipController {

    private final ShipRepo shipRepo;

    @Autowired
    public ShipController(ShipRepo shipRepo) {
        this.shipRepo = shipRepo;
    }

    @RequestMapping(value = "/ships", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Ship> list(
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

        List<Ship> shipList = shipRepo.findAll();


        shipList = getShipInfosByOrder(order, shipList);

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




        shipList = getShipInfosByPage(pageNumber, pageSize, shipList);

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
        List<Ship> shipList = shipRepo.findAll();

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
                                Double k;
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


                                return new ResponseEntity<Ship>(shipRepo.save(ship), HttpStatus.OK);
                            }








        return new ResponseEntity<Ship>(HttpStatus.BAD_REQUEST);
    }


    /*
    Если корабль не найден в БД, необходимо ответить ошибкой с кодом 404.
    Если значение id не валидное, необходимо ответить ошибкой с кодом 400.
     */
    @RequestMapping(value = "/ships/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getShipId(@PathVariable("id")  Long id) {



        if (id <= 0)
            return new ResponseEntity<Ship>(HttpStatus.BAD_REQUEST);

        List<Ship> ships = shipRepo.findAll();

//        if (ships.size() < id)
//            return new ResponseEntity<Ship>(HttpStatus.BAD_REQUEST);

        Ship ship = shipRepo.findAllById(id);
        if (ship == null)
            return new ResponseEntity<Ship>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Ship>(ship, HttpStatus.OK);
    }


    /*
    Работает
    Если что отрефакторить updateData в Классе Ship
    Если корабль не найден в БД, необходимо ответить ошибкой с кодом 404.
    Если значение id не валидное, необходимо ответить ошибкой с кодом 400.
     */
    @RequestMapping(value = "/ships/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                       @RequestBody(required = false) Ship ship) {



        return new ResponseEntity<Ship>( HttpStatus.OK);

    }


   // READY
    @RequestMapping(value = "/ships/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        if (id <= 0)
            return new ResponseEntity<Ship>(HttpStatus.BAD_REQUEST);

        Ship shipFromDb = shipRepo.findAllById(id);

        if (shipFromDb == null)
            return new ResponseEntity<Ship>(HttpStatus.NOT_FOUND);

        shipRepo.delete(shipFromDb);
        return new ResponseEntity<Ship>(HttpStatus.OK);

    }


    private List<Ship> getListShipsByOrderWithFilter(String name,
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
                                                     ShipOrder order,
                                                     Integer pageNumber,
                                                     Integer pageSize) {

        List<Ship> ships;
        switch (order) {
            case ID:
                ships = shipRepo.findAllByOrderByIdAsc();
                break;
            case SPEED:
                ships = shipRepo.findAllByOrderBySpeedAsc();
                break;
            case DATE:
                ships = shipRepo.findAllByOrderByProdDateAsc();
                break;
            case RATING:
                ships = shipRepo.findAllByOrderByRatingAsc();
                break;

            default:
                ships = shipRepo.findAll();
        }

        return ships;
    }

    /*private List<Ship> filterShips(String name,
                                   String planet,
                                   ShipType shipType,
                                   Long after, Long before,
                                   Boolean isUsed,
                                   Double minSpeed, Double maxSpeed,
                                   Integer minCrewSize, Integer maxCrewSize,
                                   Double minRating, Double maxRating,
                                   List<Ship> ships) {

        List<Ship> filterShips = ships;

        Long millis2800 = 26192235600000L;
        Long millis3019 = 33103198800000L;


        if (name != null && !name.equals(""))
            filterShips = filterShips.stream().filter((ship -> ship.getName().contains(name))).collect(Collectors.toList());


        if (planet != null && !planet.equals(""))
            filterShips = filterShips.stream().filter((ship -> ship.getPlanet().contains(planet))).collect(Collectors.toList());


        if (shipType != null) {
            switch (shipType) {
                case MILITARY:
                    filterShips = filterShips.stream().filter(ship -> ship.getShipType() == ShipType.MILITARY).collect(Collectors.toList());
                    break;
                case MERCHANT:
                    filterShips = filterShips.stream().filter(ship -> ship.getShipType() == ShipType.MERCHANT).collect(Collectors.toList());
                    break;
                case TRANSPORT:
                    filterShips = filterShips.stream().filter(ship -> ship.getShipType() == ShipType.TRANSPORT).collect(Collectors.toList());
                    break;

            }
        }




        if (after >= millis2800  && before <= millis3019)
            filterShips = filterShips.stream().filter((ship -> ship.getProdDate().getTime() >= after && ship.getProdDate().getTime() <= before)).collect(Collectors.toList());


        if (isUsed != null){
            if (isUsed)
                filterShips = filterShips.stream().filter((ship -> ship.isUsed())).collect(Collectors.toList());
            else
                filterShips = filterShips.stream().filter((ship -> !ship.isUsed())).collect(Collectors.toList());
        }


        if (minSpeed >= 0.01 && maxSpeed <= 0.99)
            filterShips = filterShips.stream().filter((ship -> ship.getSpeed() >= minSpeed && ship.getSpeed() <= maxSpeed)).collect(Collectors.toList());

        if (minCrewSize >= 1 && maxCrewSize <= 9999)
            filterShips = filterShips.stream().filter((ship -> ship.getCrewSize() >= minCrewSize && ship.getCrewSize() <= maxCrewSize)).collect(Collectors.toList());


        filterShips = filterShips.stream().filter((ship -> ship.getRating() >= minRating && ship.getRating() <= maxRating)).collect(Collectors.toList());

        return filterShips;

    }*/


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

    public List<Ship> getShipInfosByAfter(Long after, List<Ship> ships) {
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

    private List<Ship> getShipInfosByOrder(ShipOrder order, List<Ship> ships) {
        if (order == ShipOrder.ID) {
            ships.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
        } else if (order == ShipOrder.DATE) {
            //Старая строка
            /*ships.sort((o1, o2) -> (int) (o1.getProdDate().getTime() - o2.getProdDate().getTime()));*/

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


    private List<Ship> getShipInfosByPage(Integer pageNumber, Integer pageSize, List<Ship> ships) {
        int skip = pageNumber * pageSize;
        List<Ship> result = new ArrayList<>();
        for (int i = skip; i < Math.min(skip + pageSize, ships.size()); i++) {
            result.add(ships.get(i));
        }
        return result;
    }

    private Ship getShipInfosById(long id, List<Ship> ships) {
        return ships.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

}
