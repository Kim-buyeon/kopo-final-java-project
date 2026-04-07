package com.finaljavaproject.www.service;

import com.finaljavaproject.www.domain.Order;
import com.finaljavaproject.www.repository.Repository;

public class OrderService extends AbstractService<Order,String>{
    public OrderService(Repository<Order, String> repository) {
        super(repository);
    }

    @Override
    public void create(Order order){
        repository.save(order);
    }
}
