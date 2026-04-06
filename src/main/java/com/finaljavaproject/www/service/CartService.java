package com.finaljavaproject.www.service;

import java.util.List;
import java.util.UUID;

import com.finaljavaproject.www.domain.CartItem;
import com.finaljavaproject.www.repository.Repository;

public class CartService extends AbstractService<CartItem, String> {
	    
	    public CartService(Repository<CartItem, String> repository) {
	        super(repository);
	    }

	    public List<CartItem> getMemberCart(String memberId) {
	        return repository.findAll().stream()
	                .filter(item -> item.getMemberId().equals(memberId))
	                .toList();
	    }

	    public void addToCart(String memberId, String productId, int count) {
	        CartItem existing = repository.findAll().stream()
	                .filter(i -> i.getMemberId().equals(memberId) && i.getProductId().equals(productId))
	                .findFirst().orElse(null);

	        if (existing != null) {
	            existing.setQuantity(existing.getQuantity() + count);
	            update(existing);
	        } else {
	            create(new CartItem(UUID.randomUUID().toString(), memberId, productId, count));
	        }
	    }
	}
