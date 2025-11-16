package ru.sergeev.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.sergeev.MySecondTestAppSpringBoot.model.Positions;

import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {


    @Override
    public double calculate(Positions position, double salary, double bonus, int workDays) {
        int currentYear = Year.now().getValue();
        int daysInYear = Year.isLeap(currentYear) ? 366 : 365;

        return salary * bonus * daysInYear * position.getPositionCoefficient() / workDays;
    }


    @Override
    public double calculateQuarterBonus(Positions position, double salary, double bonus, int workDays) {
        if (!position.isManager()) {
            throw new IllegalArgumentException("Квартальная премия доступна только для управленцев!");
        }

        int currentYear = Year.now().getValue();
        int daysInYear = Year.isLeap(currentYear) ? 366 : 365;
        int daysInQuarter = daysInYear / 4;

        return salary * bonus * daysInQuarter * position.getPositionCoefficient() / workDays;
    }
}