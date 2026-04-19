package com.finaljavaproject.www.controller;

import com.finaljavaproject.www.domain.CartItem;
import com.finaljavaproject.www.domain.Orders;
import com.finaljavaproject.www.domain.Product;
import com.finaljavaproject.www.service.CartService;
import com.finaljavaproject.www.service.OrderService;
import com.finaljavaproject.www.service.ProductService;
import com.finaljavaproject.www.view.CartView;

import java.util.List;

public class CartController extends AbstractController<CartItem, String> {
    private final ProductService productService;
    private final OrderService orderService;
    private final CartService cartService;
    private final CartView cartView;
    public CartController(CartService cartService, CartView view,
                          ProductService productService, OrderService orderService) {
        super(cartService, view);
        this.cartService = cartService;
        this.cartView = view;
        this.productService = productService;
        this.orderService = orderService;
    }

    @Override
    public void processRead() {
        //현재 로그인한 사람의 장바구니 목록 가져온다.
        String currentUserId = MemberController.getSessionUserId();
        List<CartItem> cartItems = cartService.getMemberCart(currentUserId);
        cartView.renderList(cartItems);
    }

    @Override
    public void start() {
        String currentUserId = MemberController.getSessionUserId();
        boolean isRunning = true;
        while(isRunning){
	   cartView.showMenu();
            String choice = cartView.inputSelection();
            boolean isAdding;
            List<CartItem> cartItems = null;

            switch (choice){
                case "1":
                    processRead();
                    break;
                case "2":
                    CartItem item = cartView.inputData();
                    item.setMemberId(currentUserId);
                    int quantity = item.getQuantity();
                    if(quantity > 0){
                  	  cartService.addToCart(item, currentUserId);
                    }else{
                  	  cartService.deleteToCart(item, currentUserId);
                    }
                    break;
                case "3":
         	       processDelete();
                    break;
                case "4":
         	  cartView.renderMessage("장바구니에 들어있는 내역 전부 지웁니다.");
                    cartService.clear(currentUserId);
                case "5":
                    isAdding = false;
                    cartView.renderMessage("장바구니에 들어있는 물품 중 주문하고 싶은 품목의 아이디를 적으세요");
                    String productId = cartView.inputProductId();
                    cartItems = cartService.getMemberCart(currentUserId);
                    CartItem cartItem = cartItems.stream().filter(c -> c.getProductId().equalsIgnoreCase(productId))
                                    .findFirst().orElse(null);
                    if(cartItem != null) {
                  	  processOrder(currentUserId, cartItem);
                  	  cartService.remove(cartItem.getId());
                    }else {
                  	  cartView.renderMessage("장바구니에 해당 상품이 없습니다.");
                    }
                    break;
                case "6":
                    isAdding = false;
                    view.renderMessage("장바구니에 들어있는 모든 물품을 주문 처리 진행합니다");
                    cartItems = cartService.getMemberCart(currentUserId);
                    cartItems.stream().forEach(c -> processOrder(currentUserId, c));
                    cartService.clear(currentUserId);
                    break;
                case "0":
                    view.renderMessage("장바구니 화면에서 나갑니다.");
                    isRunning = false;
                    break;
                default:
                    view.renderMessage("잘못된 입력입니다.");
                    break;

            }
        }

    }
    
    public void processOrder(String userId, CartItem cart) {
	        productService.manageStock(cart.getProductId(), cart.getQuantity(), false);
	        Orders newOrder = new Orders(userId, cart.getProductId(), cart.getQuantity());
	        orderService.create(newOrder);
	        cartView.renderMessage("주문 완료: " + cart.getProductId() + " (" + cart.getQuantity() + "개)");
   }



}
