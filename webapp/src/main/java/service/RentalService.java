package service;

import dto.Bus;
import dto.Car;
import dto.Rental;
import dto.RentalCompany;
import exceptions.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface RentalService {
    List<RentalCompany> getRentalCompanies();


    List<RentalCompany> getFreeBusesFromAllRentalCompanyBetweenDates(Date from, Date to) throws RentalCompanyNotFoundException;
    List<RentalCompany> getFreeCarsFromAllRentalCompanyBetweenDates(Date from, Date to) throws RentalCompanyNotFoundException;


    void addRentalCompany(RentalCompany rentalCompany);
    void addRentalToRentalCompany(RentalCompany rentalCompany, Rental rental) throws RentalBusNotFoundException, RentalIntervalInvalidException, RentalCarNotFoundException, RentalCompanyNotFoundException, CustomerNotFoundException;

    void modifyRental(RentalCompany rentalCompany, Rental rental);

    void addCarToRentalCompany(RentalCompany rentalCompany, Car car) throws RentalCompanyNotFoundException;
    void addBusToRentalCompany(RentalCompany rentalCompany, Bus bus) throws RentalCompanyNotFoundException;
}