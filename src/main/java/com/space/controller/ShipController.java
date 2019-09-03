package com.space.controller;

import com.space.model.Ship;
import com.space.repository.ShipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/ships")
public class ShipController {

    private final ShipRepo shipRepo;

    @Autowired
    public ShipController(ShipRepo shipRepo) {
        this.shipRepo = shipRepo;
    }

    @GetMapping()
    public List<Ship> list() {
        return shipRepo.findAll();
    }

    @GetMapping("/count")
    public Map<String, Integer> getCount() {
        List<Ship> ships = shipRepo.findAll();

        return new HashMap<String, Integer>() {{
            put("count", ships.size());
        }};
    }


    /*
    Если корабль не найден в БД, необходимо ответить ошибкой с кодом 404.
    Если значение id не валидное, необходимо ответить ошибкой с кодом 400.
     */
    @GetMapping("/{id}")
    public Ship getShipId(@PathVariable("id") Long id) { return shipRepo.findAllById(id);}


    /*
    Мы не можем создать корабль, если:
    - длина значения параметра “name” или “planet” превышает размер соответствующего поля в БД (50 символов);
    - указаны не все параметры из Data Params (кроме isUsed);
    - значение параметра “name” или “planet” пустая строка;
    - скорость или размер команды находятся вне заданных пределов;
    - “prodDate”:[Long] < 0;
    - год производства находятся вне заданных пределов.
    В случае всего вышеперечисленного необходимо ответить ошибкой с кодом 400.
     */
    @PostMapping()
    public Ship create(@RequestBody Ship ship) {
        return shipRepo.save(ship);
    }


    /*
    Работает
    Если что отрефакторить updateData в Классе Ship
    Если корабль не найден в БД, необходимо ответить ошибкой с кодом 404.
    Если значение id не валидное, необходимо ответить ошибкой с кодом 400.
     */
    @PostMapping("/{id}")
    public Ship update(@PathVariable("id") Long id,
                       @RequestBody Ship ship) {
            Ship shipFromDB = shipRepo.findAllById(id);

            return shipRepo.save(shipFromDB.updateData(ship));

    }




    // Нужно ответить если корабль не найден 404 ошибкой, а если не валидное id 400 ошибкой НЕ ЗАБЫТЬ!!!
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Ship ship){
        shipRepo.delete(ship);
    }

}
