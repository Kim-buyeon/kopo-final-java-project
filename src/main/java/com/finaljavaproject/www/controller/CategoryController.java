package com.finaljavaproject.www.controller;

import com.finaljavaproject.www.domain.Category;
import com.finaljavaproject.www.domain.Member;
import com.finaljavaproject.www.domain.constant.MemberClassfication;
import com.finaljavaproject.www.service.CategoryService;
import com.finaljavaproject.www.service.MemberService;
import com.finaljavaproject.www.view.CategoryView;

public class CategoryController extends AbstractController<Category, String>{
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final CategoryView categoryView;
    
    public CategoryController(CategoryService service, CategoryView view, MemberService memberService) {
        super(service, view);
        this.categoryService = service;
        this.categoryView = view;
        this.memberService = memberService;
    }

    public void processCreate(){
        Category newCategory = categoryView.inputData();
        if(newCategory != null) {
	        categoryService.create(newCategory);
	        categoryView.renderMessage("카테고리가 생성되었습니다.");        
        }
    }
    
    public void processUpdate() {
	    String id = categoryView.inputId();
	    System.out.println("[debug] 입력받은 아이디" + id);
	    Category existing = categoryService.getOne(id);
	    if(existing == null) {
		    categoryView.renderMessage("해당 ID의 카테고리를 찾을 수 없습니다.");
		    return;
	    }
	    Category updatedData = categoryView.inputData();
	    if(updatedData != null) {
		    updatedData.setCategoryId(id);
		    categoryService.update(updatedData);
		    categoryView.renderMessage("카테고리 정보가 수정되었습니다.");
	    }
    }
    
    

    public boolean canProcess(String userId){
	    if(userId == null)return false;
	    Member member = memberService.getOne(userId);
	    return member != null && member.getMemberClassfication() == MemberClassfication.MANAGER;
    }


    @Override
    public void start() {
	String currentUserId = MemberController.getSessionUserId();
        boolean canProcess = canProcess(currentUserId);
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
                        processUpdate();
                        break;
                    case "3":
                        String id = categoryView.inputId();
                        categoryService.remove(id);
                        break;
                    case "0":
                        categoryView.renderMessage("카테고리 메뉴를 나갑니다.");
                        isRunning = false;
                        break;
                    default:
                  	  categoryView.renderMessage("잘못된 입력입니다.");
                }
            }
        }else{
	        categoryView.renderMessage("관리자 권한이 필요합니다.");
        }
    }



	@Override
	public void processRead() {
		// TODO Auto-generated method stub
		
	}
}
