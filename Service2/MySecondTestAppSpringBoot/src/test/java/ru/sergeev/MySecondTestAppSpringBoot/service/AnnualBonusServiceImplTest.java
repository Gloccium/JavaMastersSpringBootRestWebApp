package ru.sergeev.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.sergeev.MySecondTestAppSpringBoot.model.Positions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnualBonusServiceImplTest {

    private final AnnualBonusServiceImpl service = new AnnualBonusServiceImpl();

    @Test
    void testCalculateAnnualBonus() {
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        double result = service.calculate(position, salary, bonus, workDays);
        int daysInYear = java.time.Year.isLeap(java.time.Year.now().getValue()) ? 366 : 365;
        double expected = salary * bonus * daysInYear * position.getPositionCoefficient() / workDays;

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testCalculateQuarterBonus_ForManager() {
        Positions position = Positions.CTO;
        double bonus = 3.0;
        int workDays = 250;
        double salary = 200000.00;

        double result = service.calculateQuarterBonus(position, salary, bonus, workDays);
        int daysInYear = java.time.Year.isLeap(java.time.Year.now().getValue()) ? 366 : 365;
        int daysInQuarter = daysInYear / 4;
        double expected = salary * bonus * daysInQuarter * position.getPositionCoefficient() / workDays;

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testCalculateQuarterBonus_ForNonManager_ShouldThrowException() {
        Positions position = Positions.DEV;

        assertThrows(IllegalArgumentException.class, () ->
                service.calculateQuarterBonus(position, 100000, 1.5, 250)
        );
    }
}