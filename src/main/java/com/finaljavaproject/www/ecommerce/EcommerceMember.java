package com.finaljavaproject.www.ecommerce;

import java.util.Scanner;

import com.finaljavaproject.www.controller.CartController;
import com.finaljavaproject.www.controller.MemberController;
import com.finaljavaproject.www.controller.ProductController;

public class EcommerceMember {
	private final ProductController productController;
	private final CartController cartController;
	private final MemberController memberController;
	private final Scanner scanner =  new Scanner(System.in);
	
	public EcommerceMember(ProductController productController, CartController cartController
			,MemberController memberController) {
		super();
		this.productController = productController;
		this.cartController = cartController;
		this.memberController = memberController;
	}
	
	public void run() {
		while(true) {
			System.out.println("\n===== [ 쇼핑몰 모드 ] =====");
			System.out.println("1. 상품 둘러보기");
			System.out.println("2. 내 장바구니 / 주문하기");
			System.out.println("0. 로그아웃 하기");
			System.out.print("선택");
			String choice = scanner.next();
			if(choice.equals("0")) {
				memberController.processLogout();
				break;
			}
			switch(choice) {
			case "1":
				productController.processRead();
				break;
			case "2":
				cartController.start();
				break;
			default:
				System.out.println("잘못된 선택입니다.");
				break;
			}
		}
		
	}
	
}
