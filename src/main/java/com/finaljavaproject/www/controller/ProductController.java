package com.finaljavaproject.www.controller;

import java.util.Comparator;
import java.util.List;

import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.domain.Product;
import com.finaljavaproject.www.domain.constant.MemberClassfication;
import com.finaljavaproject.www.service.MemberService;
import com.finaljavaproject.www.service.ProductService;
import com.finaljavaproject.www.view.ProductView;

public class ProductController extends AbstractController<Product, String> {
	
	private final MemberService memberService;
	private final ProductService productService;
	private final ProductView productView;
	public ProductController(ProductService service, ProductView view, MemberService memberService) {
		super(service, view);
		this.memberService = memberService;
		this.productService = service;
		this.productView = view;
	}

	//상품에 카테고리 삽입 미구현으로 인해 가격으로 정렬해서 보여주거나 정렬하지 않고 보여주는 함수
	@Override
	public void processRead() {
		List<Product> sortedList = null;
		List<Product> productList = productService.getAll();
		
		if (productList.isEmpty()) {
		            productView.renderMessage("등록된 상품이 없습니다.");
		            return;
		}
		
		String standard = productView.inputSortStandard();
		if(standard.equals("가격")){
			sortedList = productList.stream().
						sorted(Comparator.comparingInt(Product::getPrice)).toList();
			productView.renderList(sortedList);
		}else{
			productView.renderList(productList);
		}
	}

	@Override
	public void start() {
		String currentUserId = MemberController.getSessionUserId();
		boolean canProcess = canProcess(currentUserId);
		if(canProcess){
			boolean isRunning = true;
			while(isRunning){
				productView.showMenu();

				String choice = productView.inputSelection();
				switch (choice){
					case "1":
						processCreate();
						break;
					case "2":
						processStock();
						break;
					case "3":
						processDelete();
						break;
					case "4":
						processRead();
						break;
					case "0":
						productView.renderMessage("상품 관리 메뉴룰 나갑니다. ");
						isRunning = false;
						break;
					default:
						productView.renderMessage("잘못된 입력입니다");
				}
			}
		}else{
			productView.renderMessage("관리자 권한이 필요합니다.");
		}
	}

	public void processStock(){
		String id = view.inputId();
		int inputAmount = productView.inputStock();
		boolean isAdding = inputAmount > 0;
		int absoluteAmount = Math.abs(inputAmount);
		productService.manageStock(id, absoluteAmount, isAdding);
	}

	public boolean canProcess(String userId) {
		        if (userId == null) return false;
		        Member member = memberService.getOne(userId);
		        return member != null && member.getMemberClassfication() == MemberClassfication.MANAGER;
     }

	
	
	
}
