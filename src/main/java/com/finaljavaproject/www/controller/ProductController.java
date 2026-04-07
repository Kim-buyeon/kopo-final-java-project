package com.finaljavaproject.www.controller;

import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.domain.Product;
import com.finaljavaproject.www.domain.constant.MemberClassfication;
import com.finaljavaproject.www.service.AbstractService;
import com.finaljavaproject.www.service.MemberService;
import com.finaljavaproject.www.service.ProductService;
import com.finaljavaproject.www.view.AbstractView;
import com.finaljavaproject.www.view.ProductView;

import java.util.Comparator;
import java.util.List;

public class ProductController extends AbstractController<Product, String> {

	private static String currentUserId = MemberController.getSessionUserId();
	private MemberService memberService;
	public ProductController(ProductService service, ProductView view) {
		super(service, view);
	}

	//상품에 카테고리 삽입 미구현으로 인해 가격으로 정렬해서 보여주거나 정렬하지 않고 보여주는 함수
	@Override
	public void processRead() {
		List<Product> sortedList = null;
		List<Product> productList = ((ProductService)service).getAll();
		String standard = ((ProductView)view).inputSortStandard();
		if(standard.equals("가격")){
			sortedList = productList.stream().
						sorted(Comparator.comparingInt(Product::getPrice)).toList();
			view.renderList(sortedList);
		}else{
			view.renderList(productList);
		}
	}

	@Override
	public void start() {
		boolean canProcess = canProcess();
		if(canProcess){
			boolean isRunning = true;
			while(isRunning){
				view.showMenu();

				String choice = view.inputSelection();
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
						view.renderMessage("프로그램을 종료합니다.");
						isRunning = false;
						break;
					default:
						view.renderMessage("잘못된 입력입니다");
				}
			}
		}else{
			view.renderMessage("관리자만의 고유 권한입니다.");
		}
	}

	public void processStock(){
		String id = view.inputId();
		boolean isAdding = true;
		int stock = ((ProductView)view).inputStock();
		isAdding = stock < 0 ? false : true;
		((ProductService)service).manageStock(id,stock,isAdding);
	}

	public boolean canProcess(){
		Member member = memberService.getOne(currentUserId);
		if(!MemberClassfication.MAMANGER.equals(member.getMemberClassfication())){
			return true;
		}
		return  false;
	}

	
	
	
}
