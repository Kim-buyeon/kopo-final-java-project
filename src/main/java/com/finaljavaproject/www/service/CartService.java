package com.finaljavaproject.www.service;

import java.util.List;
import java.util.UUID;

import com.finaljavaproject.www.domain.CartItem;
import com.finaljavaproject.www.repository.Repository;

public class CartService extends AbstractService<CartItem, String> {
	    
	    public CartService(Repository<CartItem, String> repository) {
	        super(repository);
	    }

	//해당하는 멤버의 장바구니 목록을 가져오는 메서드
	    public List<CartItem> getMemberCart(String memberId) {
	        return repository.findAll().stream()
	                .filter(item -> item.getMemberId().equals(memberId))
	                .toList();
	    }

		//장바구니 물건 추가하는 메서드 이미 있으면 수량만 늘리고 그게 아니라면 객체를 생성
	    public void addToCart(CartItem cartItem, String memberId) {
			String productId = cartItem.getProductId();
			int count = cartItem.getQuantity();
	        CartItem existing = repository.findAll().stream()
	                .filter(i -> i.getMemberId().equals(memberId) && i.getProductId().equals(productId))
	                .findFirst().orElse(null);

	        if (existing != null) {
	            existing.setQuantity(existing.getQuantity() + count);
	            update(existing);
	        } else {
	            create(cartItem);
	        }
	    }

	//장바구니에 물건을 빼는 메서드 있으면 수량 줄이다. 줄인 후 수량이 0이하가 되면 장바구니에서 내역 삭제하는 메서드
		public void deleteToCart(CartItem cartItem, String memberId){
			String productId = cartItem.getProductId();
			int count = cartItem.getQuantity();
			CartItem existing = repository.findAll().stream()
					.filter(i -> i.getMemberId().equals(memberId) && i.getProductId().equals(productId))
					.findFirst().orElse(null);
			if(existing != null){
				existing.setQuantity(existing.getQuantity() - count);
				if(existing.getQuantity() <= 0){
					remove(productId);
				}
			}

		}

		public void clear(){
			List<CartItem> carts = repository.findAll();
			carts.clear();
		}


	}
