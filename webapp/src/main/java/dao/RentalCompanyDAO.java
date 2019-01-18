package dao;

import dto.Rental;
import dto.RentalBus;
import dto.RentalCar;
import dto.RentalCompany;
import exceptions.RentalBusNotFoundException;
import exceptions.RentalCarNotFoundException;
import exceptions.RentalCompanyNotFoundException;
import exceptions.RentalIntervalInvalidException;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;

public interface RentalCompanyDAO {
    void addRentalCompany(RentalCompany rentalCompany);
    List<RentalCompany> getRentalCompanies();
    RentalCompany getRentalCompanyById(RentalCompany rentalCompanyId);
    void addRentalCar(RentalCompany company, RentalCar rentalCar) throws RentalCompanyNotFoundException;
    void addRentalBus(RentalCompany company, RentalBus rentalBus) throws RentalCompanyNotFoundException;
    void addRental(RentalCompany company, Rental rental) throws RentalCompanyNotFoundException,
            RentalBusNotFoundException, RentalCarNotFoundException, RentalIntervalInvalidException;
    RentalBus getRentalBusById(RentalCompany company, RentalBus rentalBus) throws RentalCompanyNotFoundException;
    RentalCar getRentalCarById(RentalCompany company, RentalCar rentalCar) throws RentalCompanyNotFoundException;
    List<Rental> getRentalsBetweenDates(RentalCompany company, Date startDate,  Date endDate) throws RentalCompanyNotFoundException;
}
