package dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CustomerDAO;
import dto.Customer;
import dto.ObjectFactory;
import dto.RentalApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class CustomerJsonDAO implements CustomerDAO {
    private File rentalJsonFile;
    private ObjectFactory dtoFactory;

    public CustomerJsonDAO(@Autowired File rentalJsonFile, @Autowired ObjectFactory dtoFactory) {
        this.rentalJsonFile = rentalJsonFile;
        this.dtoFactory = dtoFactory;
    }
    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = readCustomersFromFile();
        return customers;
    }

    @Override
    public Customer getCustomerById(Customer customer) {
        Customer searchedCustomer = null;
        List<Customer> customers = readCustomersFromFile();

        for (Customer currentCustomer:customers) {
            if(customer.getId() == currentCustomer.getId()) {
                searchedCustomer = currentCustomer;
                break;
            }
        }
        return searchedCustomer;
    }

    @Override
    public void addCustomer(Customer customer) {
        List<Customer> customers = this.readCustomersFromFile();
        customers.add(customer);
        this.writeCustomersToFile(customers);
    }

    private List<Customer> readCustomersFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        RentalApplication rentalApplication = null;
        List<Customer> customers = null;
        try {
            rentalApplication = objectMapper.readValue(this.rentalJsonFile, RentalApplication.class);
            customers = rentalApplication.getCustomers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private void writeCustomersToFile(List<Customer> customers) {
        ObjectMapper objectMapper = new ObjectMapper();
        RentalApplication rentalApplication = null;
        try {
            rentalApplication = objectMapper.readValue(this.rentalJsonFile, RentalApplication.class);
            rentalApplication.getCustomers().clear();
            rentalApplication.getCustomers().addAll(customers);

            objectMapper.writeValue(rentalJsonFile, rentalApplication);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
