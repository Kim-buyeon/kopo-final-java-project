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
                "\t 3. 선택 상품 삭제 \t 4.장바구니 전체 비우기 \t 5. 선택 상품 주문\t" +
                "전체 상품 주문 호출");

    }

    @Override
    public CartItem inputData() {
        CartItem cartItem = null;
        System.out.print("장바구니 아이디 입력");
        String id = scanner.next();
        System.out.print("상품 아이디 입력");
        String productId = scanner.next();
        System.out.print("추가하고 실은 장바구니 양을  입력헤주세요");
        int quantity = scanner.nextInt();
         cartItem = new CartItem(id,productId,quantity);
        return cartItem;
    }

    public String inputProductId(){
        System.out.print("상품 아이디 입력");
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
        items.stream().forEach(cart -> renderDetail(cart));
    }

    @Override
    public String inputSelection() {
        String choice = scanner.next();
        return choice;
    }




}
