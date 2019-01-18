package dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.RentalCompanyDAO;
import dto.*;
import exceptions.RentalBusNotFoundException;
import exceptions.RentalCarNotFoundException;
import exceptions.RentalCompanyNotFoundException;
import exceptions.RentalIntervalInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.Converter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@Repository
public class RentalCompanyJsonDAO implements RentalCompanyDAO {
    private File rentalJsonFile;
    private ObjectFactory dtoFactory;

    public RentalCompanyJsonDAO(@Autowired File rentalJsonFile, @Autowired ObjectFactory dtoFactory) {
        this.rentalJsonFile = rentalJsonFile;
        this.dtoFactory = dtoFactory;
    }

    @Override
    public void addRentalCompany(RentalCompany rentalCompany) {
        List<RentalCompany> rentalCompanies = this.readRentalCompaniesFromFile();

        if(rentalCompanies!=null && rentalCompanies.size()>0) {
            int maxId = rentalCompanies.get(0).getId();
            for (RentalCompany currentRentalCompany : rentalCompanies) {
                if (currentRentalCompany.getId() > maxId) {
                    maxId = currentRentalCompany.getId();
                }
            }
            rentalCompany.setId(maxId + 1);
        } else {
            rentalCompany.setId(0);
        }

        rentalCompanies.add(rentalCompany);
        this.writeRentalCompaniesToFile(rentalCompanies);
    }

    @Override
    public List<RentalCompany> getRentalCompanies() {
        List<RentalCompany> rentalCompanies = this.readRentalCompaniesFromFile();
        return rentalCompanies;
    }

    @Override
    public RentalCompany getRentalCompanyById(RentalCompany rentalCompany) {
        List<RentalCompany> rentalCompanies = this.readRentalCompaniesFromFile();

        RentalCompany searchedRentalCompany = this.findRentalCompanyById(rentalCompanies, rentalCompany);
        return searchedRentalCompany;
    }

    private RentalCompany findRentalCompanyById(List<RentalCompany> rentalCompanies, RentalCompany rentalCompany) {
        RentalCompany searchedRentalCompany = null;
        for (RentalCompany currentRentalCompany : rentalCompanies) {
            if (currentRentalCompany.getId() == rentalCompany.getId()) {
                searchedRentalCompany = currentRentalCompany;
                break;
            }
        }
        return searchedRentalCompany;
    }

    @Override
    public void addRentalCar(RentalCompany rentalCompany, RentalCar rentalCar) throws RentalCompanyNotFoundException {
        //TODO: not found exception
        List<RentalCompany> rentalCompanies = this.readRentalCompaniesFromFile();

        RentalCompany currentRentalCompany = this.findRentalCompanyById(rentalCompanies, rentalCompany);

        if (currentRentalCompany != null) {
            List<RentalCar> rentalCars = currentRentalCompany.getRentalCars();
            if(rentalCars!=null && rentalCars.size()>0) {
                int maxId = rentalCars.get(0).getId();
                for (RentalCar currentRentalCar : rentalCars) {
                    if (currentRentalCar.getId() > maxId) {
                        maxId = currentRentalCar.getId();
                    }
                }
                rentalCar.setId(maxId + 1);
            } else {
                rentalCar.setId(0);
            }

            currentRentalCompany.getRentalCars().add(rentalCar);
            this.writeRentalCompaniesToFile(rentalCompanies);
        } else {
            throw new RentalCompanyNotFoundException();
        }
    }

    @Override
    public void addRentalBus(RentalCompany rentalCompany, RentalBus rentalBus) throws RentalCompanyNotFoundException {
        //TODO: not found exception
        List<RentalCompany> rentalCompanies = this.readRentalCompaniesFromFile();
        RentalCompany currentRentalCompany = this.findRentalCompanyById(rentalCompanies, rentalCompany);

        if (currentRentalCompany != null) {
            List<RentalBus> rentalBuses = currentRentalCompany.getRentalBuses();
            if(rentalBuses!=null && rentalBuses.size()>0) {
                int maxId = rentalBuses.get(0).getId();
                for (RentalBus currentRentalBus : rentalBuses) {
                    if (currentRentalBus.getId() > maxId) {
                        maxId = currentRentalBus.getId();
                    }
                }
                rentalBus.setId(maxId + 1);
            } else {
                rentalBus.setId(0);
            }
            currentRentalCompany.getRentalBuses().add(rentalBus);
            this.writeRentalCompaniesToFile(rentalCompanies);
        } else {
            throw new RentalCompanyNotFoundException();
        }
    }

    @Override
    public void addRental(RentalCompany rentalCompany, Rental rental) throws RentalCompanyNotFoundException,
            RentalBusNotFoundException, RentalCarNotFoundException, RentalIntervalInvalidException {
        List<RentalCompany> rentalCompanies = this.readRentalCompaniesFromFile();

        RentalCompany currentRentalCompany = this.findRentalCompanyById(rentalCompanies, rentalCompany);

        if (currentRentalCompany != null) {
            List<Rental> allRentals = currentRentalCompany.getRentals();

            if(allRentals.size()>0) {
                int maxId = allRentals.get(0).getId();
                for (Rental currentRental : allRentals) {
                    if (currentRental.getId() > maxId) {
                        maxId = currentRental.getId();
                    }
                }
                rental.setId(maxId + 1);
            } else {
                rental.setId(0);
            }

            if (rental.getRentalBusId() != null) {
                RentalBus searchedRentalBus = dtoFactory.createRentalBus();
                searchedRentalBus.setId(rental.getRentalBusId());
                RentalBus rentalBus = this.findRentalBusById(currentRentalCompany, searchedRentalBus);

                if (rentalBus != null) {
                    List<Rental> rentals = this.findRentalsBetweenDates(currentRentalCompany, rental.getRentalTime(),
                            rental.getExpirationDate());

                    int i;
                    for (i = 0; i < rentals.size(); i++) {
                        if (rentals.get(i).getRentalBusId() == rentalBus.getId()) {
                            break;
                        }
                    }

                    System.out.println(i + " " + rentals.size());
                    if (i == rentals.size()) {
                        currentRentalCompany.getRentals().add(rental);
                        this.writeRentalCompaniesToFile(rentalCompanies);
                    } else {
                        throw new RentalIntervalInvalidException();
                    }
                } else {
                    throw new RentalBusNotFoundException();
                }
            } else if (rental.getRentalCarId() != null) {
                RentalCar searchedRentalCar = dtoFactory.createRentalCar();
                searchedRentalCar.setId(rental.getRentalCarId());
                RentalCar rentalCar = this.findRentalCarById(currentRentalCompany, searchedRentalCar);

                if (rentalCar != null) {
                    List<Rental> rentals = this.findRentalsBetweenDates(currentRentalCompany, rental.getRentalTime(),
                            rental.getExpirationDate());
                    int i;
                    for (i = 0; i < rentals.size(); i++) {
                        if (rentals.get(i).getRentalCarId() == rentalCar.getId()) {
                            break;
                        }
                    }

                    if (i == rentals.size()) {
                        currentRentalCompany.getRentals().add(rental);
                        this.writeRentalCompaniesToFile(rentalCompanies);
                    } else {
                        throw new RentalIntervalInvalidException();
                    }
                } else {
                    throw new RentalCarNotFoundException();
                }
            }
        } else {
            throw new RentalCompanyNotFoundException();
        }
    }

    @Override
    public RentalBus getRentalBusById(RentalCompany rentalCompany, RentalBus rentalBus) {
        RentalCompany currentRentalCompany = this.getRentalCompanyById(rentalCompany);
        RentalBus searchedRentalBus = null;

        if (currentRentalCompany != null) {
            searchedRentalBus = this.findRentalBusById(currentRentalCompany, rentalBus);
        }

        return searchedRentalBus;
    }

    private RentalBus findRentalBusById(RentalCompany rentalCompany, RentalBus rentalBus) {
        List<RentalBus> rentalBuses = rentalCompany.getRentalBuses();
        RentalBus searchedRentalBus = null;
        for (RentalBus currentRentalBus : rentalBuses) {
            if (currentRentalBus.getId() == rentalBus.getId()) {
                searchedRentalBus = currentRentalBus;
                break;
            }
        }
        return searchedRentalBus;
    }

    @Override
    public RentalCar getRentalCarById(RentalCompany rentalCompany, RentalCar rentalCar) {
        RentalCompany currentRentalCompany = this.getRentalCompanyById(rentalCompany);
        RentalCar searchedRentalCar = null;

        if (currentRentalCompany != null) {
            searchedRentalCar = this.findRentalCarById(currentRentalCompany, rentalCar);
        }

        return searchedRentalCar;
    }

    private RentalCar findRentalCarById(RentalCompany rentalCompany, RentalCar rentalCar) {
        List<RentalCar> rentalCars = rentalCompany.getRentalCars();
        RentalCar searchedRentalCar = null;
        for (RentalCar currentRentalCar : rentalCars) {
            if (currentRentalCar.getId() == rentalCar.getId()) {
                searchedRentalCar = currentRentalCar;
                break;
            }
        }
        return searchedRentalCar;
    }


    @Override
    public List<Rental> getRentalsBetweenDates(RentalCompany rentalCompany, Date startDate, Date endDate) throws RentalCompanyNotFoundException {
        RentalCompany currentRentalCompany = this.getRentalCompanyById(rentalCompany);

        List<Rental> rentals = null;
        if(currentRentalCompany!=null) {
            XMLGregorianCalendar convertedStartDate = Converter.DateToGregorianCalendar(startDate);
            XMLGregorianCalendar convertedEndDate = Converter.DateToGregorianCalendar(endDate);

            rentals = this.findRentalsBetweenDates(currentRentalCompany, convertedStartDate, convertedEndDate);
        } else {
            throw new RentalCompanyNotFoundException();
        }
        return rentals;
    }

    private List<Rental> findRentalsBetweenDates(RentalCompany rentalCompany,
                                                 XMLGregorianCalendar startDate, XMLGregorianCalendar endDate) {
        List<Rental> rentals = new ArrayList<>();

        for (Rental rental : rentalCompany.getRentals()) {
            if ((rental.getRentalTime().compare(startDate) >= 0 && endDate.compare(rental.getRentalTime()) >= 0)
                    || (rental.getExpirationDate().compare(startDate) >= 0 && endDate.compare(rental.getExpirationDate()) >= 0)
                    || (startDate.compare(rental.getRentalTime()) >= 0 && rental.getExpirationDate().compare(startDate) >= 0)
                    || (endDate.compare(rental.getRentalTime()) >= 0 && rental.getExpirationDate().compare(endDate) >= 0)) {
                rentals.add(rental);
            }
        }
        return rentals;
    }

    private List<RentalCompany> readRentalCompaniesFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        RentalApplication rentalApplication = null;
        List<RentalCompany> rentalCompanies = null;
        try {
            rentalApplication = objectMapper.readValue(this.rentalJsonFile, RentalApplication.class);
            rentalCompanies = rentalApplication.getRentalCompanies();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rentalCompanies;
    }

    private void writeRentalCompaniesToFile(List<RentalCompany> rentalCompanies) {
        ObjectMapper objectMapper = new ObjectMapper();
        RentalApplication rentalApplication = null;
        try {
            rentalApplication = objectMapper.readValue(this.rentalJsonFile, RentalApplication.class);
            rentalApplication.getRentalCompanies().clear();
            rentalApplication.getRentalCompanies().addAll(rentalCompanies);
            objectMapper.writeValue(rentalJsonFile, rentalApplication);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}