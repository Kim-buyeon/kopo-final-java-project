package com.finaljavaproject.www.service;

import com.finaljavaproject.www.domain.Orders;
import com.finaljavaproject.www.repository.Repository;

public class OrderService extends AbstractService<Orders,String>{
    public OrderService(Repository<Orders, String> repository) {
        super(repository);
    }

    @Override
    public void create(Orders order){
        repository.save(order);
    }
}
