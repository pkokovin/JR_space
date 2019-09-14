package com.space.service;

import com.space.model.Ship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface ShipService {
    void addShip(Ship ship);
    void removeShip(long id);
    void updateShip(Ship ship);
    Optional<Ship> getShipById(long id);
    Page<Ship> listShips(Specification<Ship> specification, Pageable page);
 }
