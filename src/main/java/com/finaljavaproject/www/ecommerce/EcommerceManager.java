package com.finaljavaproject.www.ecommerce;

import java.util.Scanner;

import com.finaljavaproject.www.controller.CategoryController;
import com.finaljavaproject.www.controller.MemberController;
import com.finaljavaproject.www.controller.ProductController;

public class EcommerceManager {
	private final ProductController productController;
	private final CategoryController categoryController;
	private final MemberController memberController;
	private final Scanner scanner = new Scanner(System.in);
	
	public EcommerceManager(ProductController productController, CategoryController categoryController,
			MemberController memberController) {
		super();
		this.productController = productController;
		this.categoryController = categoryController;
		this.memberController = memberController;
	}
	
	public void run() {
		boolean isRunning = true;
		while(isRunning) {
			System.out.println("\n===== [ 관리자 모드 ] =====");
			System.out.println("1. 상품관리");
			System.out.println("2. 카테고리 관리");
			System.out.println("3. 회원정보 조회");
			System.out.println("4. 로그아웃");
			System.out.print("선택 : ");
			String choice = scanner.next();
			switch(choice) {
			case "1":
				productController.start();
				break;
			case "2":
				categoryController.start();
				break;
			case "3":
				memberController.processRead();
				break;
			case "4":
				memberController.processLogout();
				System.out.println("관리자 모드에서 나갑니다.");
				isRunning = false;
				break;
			default:
				System.out.println("잘못된 선택입니다.");
				break;
			}
		}
	}
	
	
	
	
}
/*
1. 다형성을 구현하기 위해 코드로 표현하는 과정에 있어서 전반적인 아이디어는 저로부터 나왔지만
코드가 정리되지 않는 기분이 들어 이에 관련하여 gemini 한테 질문을 함으로써 얻은 정보로 인터페이스, 추상 클래스등을 만들었습니다.
2. 데이터 저장, 삭제와 같은 행위들을 파일 기반으로 하는 것이 익숙하지 않아 이에 관해 gemini한테 질문을 함으로써 얻은 정보로 구현하였습니다
3. generic 문법이 기억 나지않아 gemini 한테 질문했습니다.
4. stream을 활용해 정렬하는 문법이 기억 나지 않아 gemini한테 질문했습니다.
5. debugging 하는 속도를 높이기 위해 gemini한테 질문을 하고 이를 바탕으로 오류를 해결해나갔습니다.
6. 일부 코드는 코드를 깔끔하게 작성할 수 있는 방법이 떠오르지 않아 gemini한테 질문함으로써 얻은 정보로 리펙토링 진행하였습니다.
7. 전자지갑으로 구축된 클라우드 서버와의 연계 방식을 gemini한테 질문했습니다. ex) oraclepki.jar, osdt_cer.jar, osdt_core.jar 파일 필요
* */