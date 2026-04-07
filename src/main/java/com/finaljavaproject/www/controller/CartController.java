package com.finaljavaproject.www.controller;

import com.finaljavaproject.www.domain.CartItem;
import com.finaljavaproject.www.domain.Order;
import com.finaljavaproject.www.domain.Product;
import com.finaljavaproject.www.service.CartService;
import com.finaljavaproject.www.service.OrderService;
import com.finaljavaproject.www.service.ProductService;
import com.finaljavaproject.www.view.CartView;

import java.util.List;

public class CartController extends AbstractController<CartItem, String> {
    private final ProductService productService;
    private final OrderService orderService;
    private static String productId = "";
    public CartController(CartService cartService, CartView view,
                          ProductService productService, OrderService orderService) {
        super(cartService, view);
        this.productService = productService;
        this.orderService = orderService;
    }

    @Override
    public void processRead() {
        //현재 로그인
        String currentUserId = MemberController.getSessionUserId();
        List<CartItem> cartItems = ((CartService)service).getMemberCart(currentUserId);
        view.renderList(cartItems);
    }

    @Override
    public void start() {
        String currentUserId = MemberController.getSessionUserId();
        boolean isRunning = true;
        while(isRunning){
            view.showMenu();
            String choice = view.inputSelection();
            boolean isAdding;
            List<CartItem> cartItems = null;

            switch (choice){
                case "1":
                    processRead();
                    break;
                case "2":
                    CartItem cartItem = view.inputData();
                    int quantity = cartItem.getQuantity();
                    if(quantity > 0){
                        ((CartService)service).addToCart(cartItem,currentUserId);
                    }else{
                        ((CartService)service).deleteToCart(cartItem,currentUserId);
                    }
                    break;
                case "3":
                    processDelete();
                    break;
                case "4":
                    view.renderMessage("장바구니에 들어있는 내역 전부 지웁니다.");
                    ((CartService)service).clear();
                case "5":
                    isAdding = false;
                    view.renderMessage("장바구니에 들어있는 물품 중 주문하고 싶은 품목의 아이디를 적으세요");
                    productId = ((CartView)view).inputProductId();
                    cartItems = ((CartService)service).getMemberCart(currentUserId);
                    CartItem cartItem1 = cartItems.stream().filter(cartItem2 -> cartItem2.getProductId().equalsIgnoreCase(productId))
                                    .findFirst().orElse(null);
                    order(isAdding,cartItem1,productId,currentUserId);
                    ((CartService) service).remove(cartItem1.getId());
                    break;
                case "6":
                    isAdding = false;
                    view.renderMessage("장바구니에 들어있는 모든 물품을 주문 처리 진행합니다");
                    cartItems = ((CartService)service).getMemberCart(currentUserId);
                    cartItems.stream().forEach(cartItem3 -> order(isAdding,currentUserId, cartItem3));
                    ((CartService)service).clear();
                    break;
                case "0":
                    view.renderMessage("프로그램을 종료합니다.");
                    isRunning = false;
                    break;
                default:
                    view.renderMessage("잘못된 입력입니다.");
                    break;

            }
        }

    }
    //주문하는 함수 두개 오버로딩
    public void order(boolean isAdding, String currentUserId, CartItem cart){
        String productId = ((CartView)view).inputProductId();
        productService.manageStock(productId, cart.getQuantity(), isAdding);
        Order newOrder = new Order(currentUserId, productId, cart.getQuantity());
        orderService.create(newOrder);
    }
    public void order(boolean isAdding, CartItem cart, String productId, String currentUserId){
        productService.manageStock(productId, cart.getQuantity(), isAdding);
        Order newOrder = new Order(currentUserId, productId, cart.getQuantity());
        orderService.create(newOrder);
    }



}
