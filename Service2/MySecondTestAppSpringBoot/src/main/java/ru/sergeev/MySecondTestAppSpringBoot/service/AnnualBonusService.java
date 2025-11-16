package ru.sergeev.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.sergeev.MySecondTestAppSpringBoot.model.Positions;

import java.time.Year;

@Service
public interface AnnualBonusService {


    double calculate(Positions position, double salary, double bonus, int workDays);


    double calculateQuarterBonus(Positions position, double salary, double bonus, int workDays);
}