package controller;

import dto.Customer;
import dto.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.CustomerService;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping(value = "/customers")
public class CustomerController {
    private CustomerService customerService;
    private ObjectFactory dtoFactory;

    public CustomerController(@Autowired CustomerService customerService, @Autowired ObjectFactory dtoFactory) {
        this.customerService = customerService;
        this.dtoFactory = dtoFactory;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> listCustomers() {
        List<Customer> customers = this.customerService.getCustomers();
        return customers;
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public Customer getCustomerById(@PathVariable("customerId") int customerId) {
        Customer customer = dtoFactory.createCustomer();
        customer.setId(customerId);
        return this.customerService.getCustomerById(customer);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void addCustomer(@RequestBody Customer customer) {
        this.customerService.addCustomer(customer);
    }
}