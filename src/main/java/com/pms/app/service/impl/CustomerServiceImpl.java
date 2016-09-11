package com.pms.app.service.impl;

import com.pms.app.domain.Currency;
import com.pms.app.domain.Customers;
import com.pms.app.repo.CurrencyRepository;
import com.pms.app.repo.CustomerRepository;
import com.pms.app.schema.CustomerDto;
import com.pms.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final CurrencyRepository currencyRepository;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CurrencyRepository currencyRepository) {
        this.customerRepository = customerRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Customers> getCustomers() {
        return  customerRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Customers addCustomer(CustomerDto customerDto) {


        Customers parent = null;
        if (customerDto.getParentId() != null) {
            parent = customerRepository.findOne(customerDto.getParentId());
            if (parent == null) {
                throw new RuntimeException("No appropriate parent available");
            }
        }
        Customers duplicate = customerRepository.findByNameAndParentId(customerDto.getName(), customerDto.getParentId());
        if (duplicate != null) {
            throw new RuntimeException("Customer with same name already exists");
        }

        Currency currency = currencyRepository.findOne(customerDto.getCurrencyId());
        if (currency == null) {
            throw new RuntimeException("Currency is not available");
        }
        Customers newCustomer = new Customers();
        newCustomer.setName(customerDto.getName());
        newCustomer.setCurrency(currency);
        newCustomer.setParent(parent);
        return customerRepository.save(newCustomer);
    }

    @Override
    public Customers getCustomer(Long id) {
        Customers customers = customerRepository.findOne(id);
        if (customers == null) {
            throw new RuntimeException("No customer found");
        }
        return customers;
    }

    @Override
    public Customers updateCustomer(Long id, CustomerDto customerDto) {
        Customers existingCustomers = customerRepository.findOne(id);
        if (existingCustomers == null) {
            throw new RuntimeException("No customer found");
        }
        Customers parent = null;
        if (customerDto.getParentId() != null) {
            parent = customerRepository.findOne(customerDto.getParentId());
            if (parent == null) {
                throw new RuntimeException("No appropriate parent available");
            }
        }
        Customers duplicate = customerRepository.findByNameAndParentId(customerDto.getName(), customerDto.getParentId());
        if (duplicate != null && duplicate.getId() != id) {
            throw new RuntimeException("Customer with same name already exists");
        }
        Currency currency = currencyRepository.findOne(customerDto.getCurrencyId());
        if (currency == null) {
            throw new RuntimeException("Currency is not available");
        }
        existingCustomers.setName(customerDto.getName());
        existingCustomers.setCurrency(currency);
        existingCustomers.setParent(parent);
        return customerRepository.save(existingCustomers);
    }

    @Override
    public List<Customers> findParentCustomers() {
        return customerRepository.findParentCustomers();
    }

    @Override
    public List<Customers> getCustomerByParentId(Long parentId) {
        return customerRepository.findCustomerByParentId(parentId);
    }
}
