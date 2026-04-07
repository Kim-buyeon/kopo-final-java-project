package com.finaljavaproject.www.controller;

import com.finaljavaproject.www.domain.Category;
import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.domain.constant.MemberClassfication;
import com.finaljavaproject.www.service.AbstractService;
import com.finaljavaproject.www.service.CartService;
import com.finaljavaproject.www.service.CategoryService;
import com.finaljavaproject.www.service.MemberService;
import com.finaljavaproject.www.view.AbstractView;

public class CategoryController extends AbstractController<Category, String>{
    private static String currentUserId = MemberController.getSessionUserId();
    private MemberService memberService;
    public CategoryController(AbstractService<Category, String> service, AbstractView<Category, String> view) {
        super(service, view);
    }

    @Override
    public void processRead() {

    }

    public void processCreate(){
        Category newCategory = view.inputData();
        service.create(newCategory);
        view.renderMessage("카테고리가 생성되었습니다.");
    }

    public boolean canProcess(){
        Member member = memberService.getOne(currentUserId);
        if(!MemberClassfication.MAMANGER.equals(member.getMemberClassfication())){
            return true;
        }
        return  false;
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
                        Category newCategory = view.inputData();
                        ((CategoryService)service).update(newCategory);
                        break;
                    case "3":
                        String id = view.inputId();
                        ((CategoryService)service).remove(id);
                        break;
                    case "0":
                        view.renderMessage("프로그램을 종료합니다.");
                        isRunning = false;
                        break;
                    default:
                        view.renderMessage("잘못된 입력입니다.");
                }
            }
        }else{
            view.renderMessage("관리자만의 고유권한입니다.");
        }
    }
}
