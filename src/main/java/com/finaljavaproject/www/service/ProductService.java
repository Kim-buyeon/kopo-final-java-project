package com.finaljavaproject.www.service;

import com.finaljavaproject.www.domain.Product;
import com.finaljavaproject.www.domain.constant.ProductStatus;
import com.finaljavaproject.www.repository.Repository;

public class ProductService extends AbstractService<Product, String>{

	public ProductService(Repository<Product, String> repository) {
		super(repository);
	}
	
	@Override
	public void create(Product product) {
		repository.save(product);
	}
	
	@Override
	public void update(Product product) {
		repository.save(product);
	}
	
	@Override
	public void remove(String id) {
		repository.deleteById(id);
	}

	//물품의 재고수를 관리해주는 메서드 추가하냐 삭제하냐에 따라 다르게 동작한다.
	public void manageStock(String id, int stock, boolean isAdding) {
		Product product = repository.findById(id);
		if(product == null)return;
		if(isAdding) {
			product.addStock(stock);
		}else {
			product.removeStock(stock);
		}
		update(product);
		if (product.getProductStatus() == ProductStatus.SUSPENSION) {
			      System.out.println("알림: [" + product.getName() + "] 상품의 재고가 부족하여 판매 중지 상태로 변경되었습니다.");
		} else {
			      System.out.println("정상 처리되었습니다. 현재 재고: " + product.getStock());
		}
	}




}
