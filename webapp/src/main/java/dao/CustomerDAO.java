package dao;

import dto.Customer;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getCustomers();
    Customer getCustomerById(Customer customer);
    void addCustomer(Customer customer);
}
