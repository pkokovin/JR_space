package com.space.service.impl;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;

    @Override
    public void addShip(Ship ship) {
        shipRepository.save(ship);
    }

    @Override
    public void removeShip(long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public void updateShip(Ship ship) {
        shipRepository.save(ship);
    }

    @Override
    public Optional<Ship> getShipById(long id) {
        return shipRepository.findById(id);
    }

    @Override
    public Page<Ship> listShips(Specification<Ship> specification, Pageable page) {
        return shipRepository.findAll(specification, page);
    }
}
