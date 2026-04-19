package com.finaljavaproject.www.view;

import com.finaljavaproject.www.domain.CartItem;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CartView extends AbstractView<CartItem, String> {
    private Scanner scanner = new Scanner(System.in);
    @Override
    public void showMenu() {
        super.printHeader("장바구니 관리");
        super.renderMessage("1. 장바구니 목록 조회 \t 2. 수량 변경 " +
                "\t 3. 선택 장바구니 삭제 \t 4.장바구니 전체 비우기 \t 5. 선택 상품 주문\t" +
                "6. 전체 상품 주문 호출 \t 0. 내 장바구니/주문하기 화면에서 나가기");

    }

    @Override
    public CartItem inputData() {
        CartItem cartItem = null;
        System.out.print("상품 아이디 입력");
        String productId = scanner.next();
        System.out.print("추가하고 실은 장바구니 양을  입력해주세요");
        int quantity = scanner.nextInt();
         cartItem = new CartItem(UUID.randomUUID().toString() ,null , productId, quantity);
        return cartItem;
    }

    public String inputProductId(){
        System.out.print("대상 상품 아이디 입력");
        String productId = scanner.next();
        return productId;
    }

    @Override
    public void renderDetail(CartItem item) {
        super.renderMessage("멤버 아이디 : " + item.getMemberId());
        super.renderMessage("상품 아이디 : "+ item.getProductId());
        super.renderMessage("수량 : " + item.getQuantity());
    }

    @Override
    public void renderList(List<CartItem> items) {
	   if (items.isEmpty()) {
		super.renderMessage("장바구니가 비어 있습니다.");
		return;
             }
	   super.printHeader("나의 장바구니 목록");
	   items.stream().forEach(cart -> renderDetail(cart));
	   System.out.println("------------------------------------");
    }

    @Override
    public String inputSelection() {
        System.out.print("메뉴 선택: ");
        String choice = scanner.next();
        return choice;
    }




}
