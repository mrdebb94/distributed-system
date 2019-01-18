package service;

import dto.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomerById(Customer customer);
    void addCustomer(Customer customer);
}
