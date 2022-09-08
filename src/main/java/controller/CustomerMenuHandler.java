package controller;

import model.dao.CompanyDao;
import model.dao.CustomerDao;
import model.service.CompanyService;
import model.service.CustomerService;
import model.service.converter.CompanyConverter;
import model.service.converter.CustomerConverter;
import model.storage.CompanyStorage;
import model.storage.CustomerStorage;
import view.Output;

import java.util.ArrayList;
import java.util.List;


public class CustomerMenuHandler {
    private CustomerService customerService;
    private CustomerStorage customerStorage;
    private CustomerConverter customerConverter;

public CustomerMenuHandler(CustomerService customerService, CustomerStorage customerStorage, CustomerConverter customerConverter) {
    this.customerService = customerService;
    this.customerStorage = customerStorage;
    this.customerConverter = customerConverter;
}


    public void getAllNames() {
        List<CustomerDao> customerDaoList = customerStorage.findAll();
        List<String> result = new ArrayList<>();
        for (CustomerDao customerDao : customerDaoList) {
            result.add(String.format("%d. %s, репутация -  %s",
                    customerDao.getCustomer_id(),
                    customerDao.getCustomer_name(),
                    customerDao.getReputation()));
        }
        Output.getInstance().print(result);
    }

}
