package com.finaljavaproject.www.controller;

import com.finaljavaproject.www.domain.CartItem;
import com.finaljavaproject.www.domain.Product;
import com.finaljavaproject.www.service.CartService;
import com.finaljavaproject.www.service.ProductService;

public class CartController extends AbstractController<CartItem, String> {
    private final ProductService productService; // 상품 정보를 가져오기 위해 필요

    public CartController(CartService cartService, CartView view, ProductService productService) {
        super(cartService, view);
        this.productService = productService;
    }

    @Override
    public void processRead() {
        String currentMemberId = MemberController getSessionUser().get// 로그인된 ID
        List<CartItem> cartList = ((CartService)service).getMemberCart(currentMemberId);
        
        view.renderMessage("--- 내 장바구니 목록 ---");
        for (CartItem item : cartList) {
            Product p = productService.findById(item.getProductId());
            view.renderMessage(String.format("상품명: %s | 수량: %d | 가격: %d", 
                p.getName(), item.getQuantity(), p.getPrice() * item.getQuantity()));
        }
    }
}
