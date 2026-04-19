package com.finaljavaproject.www;

import java.util.Scanner;

import com.finaljavaproject.www.controller.CartController;
import com.finaljavaproject.www.controller.CategoryController;
import com.finaljavaproject.www.controller.MemberController;
import com.finaljavaproject.www.controller.ProductController;
import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.domain.constant.MemberClassfication;
import com.finaljavaproject.www.ecommerce.EcommerceManager;
import com.finaljavaproject.www.ecommerce.EcommerceMember;
import com.finaljavaproject.www.repository.CartItemRepository;
import com.finaljavaproject.www.repository.CategoryRepository;
import com.finaljavaproject.www.repository.MemberRepository;
import com.finaljavaproject.www.repository.OrderRepository;
import com.finaljavaproject.www.repository.ProductRepository;
import com.finaljavaproject.www.service.CartService;
import com.finaljavaproject.www.service.CategoryService;
import com.finaljavaproject.www.service.MemberService;
import com.finaljavaproject.www.service.OrderService;
import com.finaljavaproject.www.service.ProductService;
import com.finaljavaproject.www.util.JdbcUtil;
import com.finaljavaproject.www.view.CartView;
import com.finaljavaproject.www.view.CategoryView;
import com.finaljavaproject.www.view.MemberView;
import com.finaljavaproject.www.view.ProductView;

public class Main {
    public static void main(String[] args) {
	    	JdbcUtil.setupDatabase("schema.sql");
	    	MemberRepository memberRepo = new MemberRepository();
	        ProductRepository productRepo = new ProductRepository();
	        CategoryRepository categoryRepo = new CategoryRepository();
	        CartItemRepository cartRepo = new CartItemRepository();
	        OrderRepository orderRepo = new OrderRepository();

	        // 2. Service 초기화
	        MemberService memberService = new MemberService(memberRepo);
	        ProductService productService = new ProductService(productRepo);
	        CategoryService categoryService = new CategoryService(categoryRepo);
	        CartService cartService = new CartService(cartRepo);
	        OrderService orderService = new OrderService(orderRepo);

	        // 3. View 및 Controller 초기화
	        MemberView memberView = new MemberView();
	        MemberController memberController = new MemberController(memberService, memberView);

	        ProductView productView = new ProductView();
	        ProductController productController = new ProductController(productService, productView, memberService);

	        CategoryView categoryView = new CategoryView(categoryService);
	        CategoryController categoryController = new CategoryController(categoryService, categoryView, memberService);

	        CartView cartView = new CartView();
	        CartController cartController = new CartController(cartService, cartView, productService, orderService);

	        // 4. 메인 루프 전용 도구
	        Scanner scanner = new Scanner(System.in);
	        EcommerceManager ecommerceManager = new EcommerceManager(productController, categoryController, memberController);
	        EcommerceMember ecommerceMember = new EcommerceMember(productController, cartController,memberController);
	        while(true) {
		        String currentId = MemberController.getSessionUserId();
		        if(currentId == null) {
			        System.out.println("\n********** [ 쇼핑몰 ] **********");
			        System.out.println("1. 회원가입 2. 로그인 0. 프로그램 종료");
			        System.out.print("선택");
			        String choice = scanner.next();
			        if(choice.equals("0")) {
				        System.out.println("프로그램 종료합니다.");
				        break;
			        }
			        switch(choice) {
			        case "1":
				        memberController.processCreate();
				        break;
			        case "2":
				        memberController.processLogin();
				        break;
				 default:
					 System.out.println("올바르지 않은 입력입니다.");
					 break;
			        }
		        }else {
			        Member user = memberService.getOne(currentId);
			        if (user.getMemberClassfication() == MemberClassfication.MANAGER) {
			                    ecommerceManager.run();
			         } else {
			                    ecommerceMember.run();
			          }
		        }
	        }
    }
}