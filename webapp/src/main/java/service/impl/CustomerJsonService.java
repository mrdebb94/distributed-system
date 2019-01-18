package service.impl;

import dao.CustomerDAO;
import dto.Customer;
import dto.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.CustomerService;

import java.util.List;

@Service
public class CustomerJsonService implements CustomerService {
    private CustomerDAO customerDAO;
    private ObjectFactory dtoFactory;

    private CustomerJsonService(@Autowired CustomerDAO customerDAO, ObjectFactory dtoFactory) {
        this.customerDAO = customerDAO;
        this.dtoFactory = dtoFactory;
    }

    @Override
    public List<Customer> getCustomers() {
        return this.customerDAO.getCustomers();
    }

    @Override
    public Customer getCustomerById(Customer customer) {
        return this.customerDAO.getCustomerById(customer);
    }

    @Override
    public void addCustomer(Customer customer) {
        this.customerDAO.addCustomer(customer);
    }
}