package com.space.helpers;


import com.space.exceptions.ShipNotFoundException;
import com.space.exceptions.ValidationException;
import com.space.model.ShipType;
import com.space.service.ShipService;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public final class Validation {
    private Validation() {
    }

    public static void positiveLongNumberRequired(String s) {
        Long id;
        try {
            id = Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw new ValidationException();
        }
        if (Long.parseLong(s) <= 0) throw new ValidationException();
    }

    public static void nameRequired(String name) {
        if (name == null) throw new ValidationException();
        if (name.length() > 50 || name.isEmpty()) throw new ValidationException();
    }

    public static void planetRequired(String planet) {
        if (planet == null) throw new ValidationException();
        if (planet.length() > 50 || planet.isEmpty()) throw new ValidationException();
    }

    public static void shipTypeRequired(ShipType shipType) {
        if (shipType == null) throw new ValidationException();
    }

    public static void speedRequired(Double speed) {
        if (speed == null) throw new ValidationException();
        BigDecimal btw = BigDecimal.valueOf(speed).setScale(2, BigDecimal.ROUND_HALF_UP);
        if (btw.doubleValue() < 0.01 || btw.doubleValue() > 0.99 ) throw new ValidationException();
    }

    public static void crewSizeRequired (Integer crewSize) {
        if (crewSize == null) throw new ValidationException();
        if (crewSize < 1 || crewSize > 9999) throw new ValidationException();
    }

    public static void dateRequired (Long prodDate) {
        if (prodDate == null) throw new ValidationException();
        if (prodDate < 0) throw new ValidationException();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(prodDate));
        if (calendar.get(Calendar.YEAR) < 2800 || calendar.get(Calendar.YEAR) > 3019) throw new ValidationException();
    }

    public static void shipRequired (Long id, ShipService service) {
        if (!service.getShipById(id).isPresent()) throw new ShipNotFoundException();
    }

}
