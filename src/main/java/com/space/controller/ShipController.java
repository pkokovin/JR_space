package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipSpecification;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import static com.space.helpers.Validation.*;

@RestController
@RequestMapping(value = "/rest" )
public class ShipController {
    private final ShipService service;

    public ShipController(ShipService service) {
        this.service = service;
    }

    @GetMapping(value = "/ships")
    public ResponseEntity<List<Ship>> shipList( @RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "planet", required = false) String planet,
                                                @RequestParam(value = "shipType", required = false) ShipType shipType,
                                                @RequestParam(value = "after", required = false) Date after,
                                                @RequestParam(value = "before", required = false) Date before,
                                                @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                                @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                                @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                                @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                                @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                                @RequestParam(value = "minRating", required = false) Double minRating,
                                                @RequestParam(value = "maxRating", required = false) Double maxRating,
                                                @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
                                                @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize,
                                                ShipSpecification shipSpecification) {
        Pageable page = new PageRequest(pageNumber, pageSize, Sort.Direction.ASC, order.getFieldName());
        return new ResponseEntity<>(service.listShips(setSpec(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, shipSpecification), page).getContent(), HttpStatus.OK);
    }



    @GetMapping(value = "/ships/count")
    public ResponseEntity<Integer> shipsCount(  @RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "planet", required = false) String planet,
                                                @RequestParam(value = "shipType", required = false) ShipType shipType,
                                                @RequestParam(value = "after", required = false) Date after,
                                                @RequestParam(value = "before", required = false) Date before,
                                                @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                                @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                                @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                                @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                                @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                                @RequestParam(value = "minRating", required = false) Double minRating,
                                                @RequestParam(value = "maxRating", required = false) Double maxRating,
                                                @RequestParam(value = "order", required = false, defaultValue = "ID") ShipOrder order,
                                                @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize,
                                                ShipSpecification specification) {
        Pageable page = new PageRequest(pageNumber, pageSize, Sort.Direction.ASC, order.getFieldName());
        return new ResponseEntity<> ((int) service.listShips(setSpec(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, specification), page).getTotalElements(), HttpStatus.OK);
    }

    @GetMapping(value = "/ships/{id}")
    public ResponseEntity<Ship> getShipById(@PathVariable String id) {
        positiveLongNumberRequired(id);
        shipRequired(Long.valueOf(id), service);
        return new ResponseEntity<>(service.getShipById(Long.valueOf(id)).get(), HttpStatus.OK);
    }

    @PostMapping(value = "/ships")
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        nameRequired(ship.getName());
        planetRequired(ship.getPlanet());
        shipTypeRequired(ship.getShipType());
        dateRequired(ship.getProdDate().getTime());
        speedRequired(ship.getSpeed());
        crewSizeRequired(ship.getCrewSize());
        if (ship.isUsed() == null) ship.setUsed(false);
        ship.setRating(ship.calcRating(ship.getProdDate(), ship.isUsed(), ship.getSpeed()));
        service.addShip(ship);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @DeleteMapping(value = "/ships/{id}")
    public ResponseEntity<Void>  deleteShip(@PathVariable String id) {
        positiveLongNumberRequired(id);
        shipRequired(Long.valueOf(id), service);
        service.removeShip(Long.valueOf(id));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/ships/{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable String id, @RequestBody(required = false) Ship ship) {
        positiveLongNumberRequired(id);
        shipRequired(Long.valueOf(id), service);
        Ship currentShip = service.getShipById(Long.valueOf(id)).get();
            if (ship != null) {
            if (ship.getName() != null) {
                nameRequired(ship.getName());
                currentShip.setName(ship.getName());
            }
            if (ship.getPlanet() != null) {
                planetRequired(ship.getPlanet());
                currentShip.setPlanet(ship.getPlanet());
            }
            if (ship.getShipType() != null) {
                shipTypeRequired(ship.getShipType());
                currentShip.setShipType(ship.getShipType());
            }
            if (ship.getProdDate() != null) {
                dateRequired(ship.getProdDate().getTime());
                currentShip.setProdDate(ship.getProdDate());
            }
            if (ship.isUsed() != null) {
                currentShip.setUsed(ship.isUsed());
            }
            if (ship.getSpeed() != null) {
                speedRequired(ship.getSpeed());
                currentShip.setSpeed(ship.getSpeed());
            }
            if (ship.getCrewSize() != null) {
                crewSizeRequired(ship.getCrewSize());
                currentShip.setCrewSize(ship.getCrewSize());
            }
                currentShip.setRating(currentShip.calcRating(currentShip.getProdDate(), currentShip.isUsed(), currentShip.getSpeed()));
                service.updateShip(currentShip);
        }
            return new ResponseEntity<>(currentShip, HttpStatus.OK);
    }

    private ShipSpecification setSpec(String name, String planet, ShipType shipType, Date after, Date before, Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipSpecification shipSpecification) {
        if (name != null) shipSpecification.setName(name);
        if (planet != null) shipSpecification.setPlanet(planet);
        if (shipType !=null) shipSpecification.setShipType(shipType);
        if (after != null) shipSpecification.setAfter(after);
        if (before != null) shipSpecification.setBefore(before);
        if (isUsed != null) shipSpecification.setUsed(isUsed);
        if (minSpeed != null) shipSpecification.setMinSpeed(minSpeed);
        if (maxSpeed != null) shipSpecification.setMaxSpeed(maxSpeed);
        if (minCrewSize != null) shipSpecification.setMinCrewSize(minCrewSize);
        if (maxCrewSize != null) shipSpecification.setMaxCrewSize(maxCrewSize);
        if (minRating != null) shipSpecification.setMinRating(minRating);
        if (maxRating !=null) shipSpecification.setMaxRating(maxRating);
        return shipSpecification;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            public void setAsText(String value) {
                setValue(new Date(Long.valueOf(value)));
            }
        });
    }

}
