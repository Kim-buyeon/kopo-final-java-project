package com.finaljavaproject.www.view;

import java.util.List;
import java.util.Scanner;

import com.finaljavaproject.www.domain.Product;
import com.finaljavaproject.www.domain.constant.ProductStatus;

public class ProductView extends AbstractView<Product, String> {

	private Scanner scanner = new Scanner(System.in);
	@Override
	public void showMenu() {
		super.printHeader("(관리자)상품 관리");
		super.renderMessage("1. 싱품 등록\t  2. 상품 수정 \t 3. 상품 삭제\t 4. 상품목록\t 0. 상품 관리 화면에서 나가기");
	}

	@Override
	public Product inputData() {
		Product newProduct = null;
		System.out.println("=== 상품 정보 입력 ===");
		System.out.print("상품 ID:");
		scanner.nextLine();
		String id = scanner.nextLine().trim();
		System.out.print("상품 명: ");
		String name = scanner.nextLine().trim();
		System.out.print("상세 설명: ");
		String description = scanner.nextLine().trim();
		System.out.println("가격:");
		int price = scanner.nextInt();
		System.out.print("재고 수량: ");
		int stock = scanner.nextInt();
		newProduct = new Product(id, name, description, price, stock, ProductStatus.NORMAL);
		return newProduct;
	}

	@Override
	public void renderDetail(Product item) {
		System.out.println("------------------------------------");
		super.renderMessage("상품  아이디 : " + item.getProductId());
		super.renderMessage("상품 명 : " + item.getName());
		super.renderMessage("상품 가격 : " + item.getPrice());
		super.renderMessage("상품 재고수 " + item.getStock());
		super.renderMessage("상품 판매가능 여부" + item.getProductStatus());
	}

	@Override
	public void renderList(List<Product> items) {
		if(items.isEmpty()) {
			super.renderMessage("등록된 상품이 없습니다.");
			return;
		}
		super.printHeader(null);
		items.stream().forEach(product -> renderDetail(product));
		System.out.println("------------------------------------");
	}

	@Override
	public String inputSelection() {
		System.out.print("메뉴 선택");
		String choice = scanner.next();
		return choice;
	}

	public int inputStock(){
		super.printHeader("재고 수량 변경");
		super.renderMessage("원하는 재고 변화량을 (+)/(-) 포함해서 입력해주세요");
		int stock = scanner.nextInt();
		return stock;
	}
	//정렬 기준을 뭐로 할지 입력하고 이를 리턴해주는 메서드
	public String inputSortStandard(){
		super.renderMessage("상품 정렬 기준 입력해주세요");
		String standard = scanner.next();
		return standard;
	}

}
