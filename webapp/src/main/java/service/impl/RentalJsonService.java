package service.impl;

import dao.CustomerDAO;
import dao.RentalCompanyDAO;
import dto.*;
import exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RentalService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RentalJsonService implements RentalService {
    private RentalCompanyDAO rentalDAO;
    private CustomerDAO customerDAO;
    private ObjectFactory dtoFactory;

    private RentalJsonService(@Autowired  RentalCompanyDAO rentalDAO,
                              @Autowired  CustomerDAO customerDAO,
                              ObjectFactory dtoFactory) {
        this.rentalDAO = rentalDAO;
        this.customerDAO = customerDAO;
        this.dtoFactory = dtoFactory;
    }

    @Override
    public List<RentalCompany> getRentalCompanies() {
        List<RentalCompany> rentalCompanies = this.rentalDAO.getRentalCompanies();
        return rentalCompanies;
    }



    @Override
    public List<RentalCompany> getFreeBusesFromAllRentalCompanyBetweenDates(Date from, Date to) throws RentalCompanyNotFoundException {
        List<RentalCompany> rentalCompanies = this.rentalDAO.getRentalCompanies();
        List<RentalCompany> filteredRentalCompanies = new ArrayList<>();

        for(int i=0; i<rentalCompanies.size(); i++) {
            List<RentalBus> freeRentalBuses = new ArrayList<>();
            RentalCompany rentalCompany = rentalCompanies.get(i);
            List<Rental> rentals = this.rentalDAO.getRentalsBetweenDates(rentalCompany, from, to);

            for(RentalBus rentalBus : rentalCompany.getRentalBuses()) {
                int j;
                for (j=0; j<rentals.size(); j++) {
                    Rental rental = rentals.get(j);
                    Integer rentalBusId = rental.getRentalBusId();
                    if (rentalBusId != null) {
                        if (rentalBus.getId() == rentalBusId) {
                            break;
                        }
                    }
                }

                if(j == rentals.size()) {
                    freeRentalBuses.add(rentalBus);
                }
            }

            RentalCompany  newRentalCompany = dtoFactory.createRentalCompany();
            newRentalCompany.setId(rentalCompany.getId());
            newRentalCompany.setName(rentalCompany.getName());
            newRentalCompany.getRentalBuses().addAll(freeRentalBuses);
            filteredRentalCompanies.add(newRentalCompany);
        }
        return filteredRentalCompanies;
    }

    @Override
    public List<RentalCompany> getFreeCarsFromAllRentalCompanyBetweenDates(Date from, Date to) throws RentalCompanyNotFoundException {
        List<RentalCompany> rentalCompanies = this.rentalDAO.getRentalCompanies();
        List<RentalCompany> filteredRentalCompanies = new ArrayList<>();

        for(int i=0; i<rentalCompanies.size(); i++) {
            List<RentalCar> freeRentalCars = new ArrayList<>();
            RentalCompany rentalCompany = rentalCompanies.get(i);
            List<Rental> rentals = this.rentalDAO.getRentalsBetweenDates(rentalCompany, from, to);

            for(RentalCar rentalCar : rentalCompany.getRentalCars()) {
                int j;
                for (j=0; j<rentals.size(); j++) {
                    Rental rental = rentals.get(j);
                    Integer rentalCarId = rental.getRentalCarId();
                    if (rentalCarId != null) {
                        if (rentalCar.getId() == rentalCarId) {
                            break;
                        }
                    }
                }

                if(j == rentals.size()) {
                    freeRentalCars.add(rentalCar);
                }
            }

            RentalCompany  newRentalCompany = dtoFactory.createRentalCompany();
            newRentalCompany.setId(rentalCompany.getId());
            newRentalCompany.setName(rentalCompany.getName());
            newRentalCompany.getRentalCars().addAll(freeRentalCars);
            filteredRentalCompanies.add(newRentalCompany);
        }
        return filteredRentalCompanies;

    }

    @Override
    public void addRentalCompany(RentalCompany rentalCompany) {
        this.rentalDAO.addRentalCompany(rentalCompany);
    }

    @Override
    public void addRentalToRentalCompany(RentalCompany rentalCompany, Rental rental) throws RentalBusNotFoundException,
            RentalIntervalInvalidException, RentalCarNotFoundException, RentalCompanyNotFoundException, CustomerNotFoundException {
        Customer searchedCustomer = this.dtoFactory.createCustomer();
        searchedCustomer.setId(rental.getCustomerId());

        Customer customer = this.customerDAO.getCustomerById(searchedCustomer);
        if(customer!=null) {
            this.rentalDAO.addRental(rentalCompany, rental);
        } else {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public void modifyRental(RentalCompany rentalCompany, Rental rental) {

    }

    @Override
    public void addCarToRentalCompany(RentalCompany rentalCompany, Car car) throws RentalCompanyNotFoundException {
        RentalCar rentalCar = dtoFactory.createRentalCar();
        rentalCar.setCar(car);
        rentalCar.setIsReserved(false);

        this.rentalDAO.addRentalCar(rentalCompany, rentalCar);
    }

    @Override
    public void addBusToRentalCompany(RentalCompany rentalCompany, Bus bus) throws RentalCompanyNotFoundException {
        RentalBus rentalBus = dtoFactory.createRentalBus();
        rentalBus.setBus(bus);
        rentalBus.setIsReserved(false);

        this.rentalDAO.addRentalBus(rentalCompany, rentalBus);
    }
}