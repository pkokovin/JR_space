package com.space.model;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;

public class ShipSpecification implements Specification<Ship> {
    private String name;
    private String planet;
    private ShipType shipType;
    private Date after;
    private Date before;
    private Boolean isUsed;
    private Double minSpeed;
    private Double maxSpeed;
    private Integer minCrewSize;
    private Integer maxCrewSize;
    private Double minRating;
    private Double maxRating;

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public void setAfter(Date after) {
        this.after = after;
    }

    public void setBefore(Date before) {
        this.before = before;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public void setMinSpeed(Double minSpeed) {
        this.minSpeed = minSpeed;
    }

    public void setMaxSpeed(Double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setMinCrewSize(Integer minCrewSize) {
        this.minCrewSize = minCrewSize;
    }

    public void setMaxCrewSize(Integer maxCrewSize) {
        this.maxCrewSize = maxCrewSize;
    }

    public void setMinRating(Double minRating) {
        this.minRating = minRating;
    }

    public void setMaxRating(Double maxRating) {
        this.maxRating = maxRating;
    }

    @Override
    public Specification<Ship> and(Specification<Ship> other) {
        return null;
    }

    @Override
    public Specification<Ship> or(Specification<Ship> other) {
        return null;
    }

    @Override
    public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (name != null && !StringUtils.isEmpty(name)) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + name + "%"));
        }
        if (planet !=null && !StringUtils.isEmpty(planet)) {
            predicates.add(cb.like(cb.lower(root.get("planet")), "%" + planet + "%"));
        }
        if (shipType !=null) {
            predicates.add(cb.equal(root.<ShipType>get(("shipType")), shipType));
        }
        if (after != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("prodDate"), after));
        }
        if (before != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("prodDate"), before));
        }
        if (isUsed != null) {
            predicates.add(cb.equal(root.get("isUsed"), isUsed));
        }
        if (minSpeed != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("speed"), minSpeed));
        }
        if (maxSpeed != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("speed"), maxSpeed));
        }
        if (minCrewSize != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("crewSize"), minCrewSize));
        }
        if (maxCrewSize != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("crewSize"), maxCrewSize));
        }
        if (minRating != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("rating"), minRating));
        }
        if (maxRating != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("rating"), maxRating));
        }
        return predicates.size() <= 0 ? null : cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
