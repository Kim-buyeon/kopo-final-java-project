package com.finaljavaproject.www.view;

import com.finaljavaproject.www.domain.Category;
import com.finaljavaproject.www.service.CartService;
import com.finaljavaproject.www.service.CategoryService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CategoryView extends AbstractView<Category,String> {

    private CategoryService categoryService;
    private Scanner scanner = new Scanner(System.in);
    
    public CategoryView(CategoryService categoryService) {
	        this.categoryService = categoryService;
     }
    @Override
    public void showMenu() {
        super.printHeader("카테고리 관리");
        super.renderMessage("1. 카테고리 생성 \t 2. 카테고리 수정 \t 3.카테코리 삭제\t 0. 카테고리 화면 나가기");
    }

    @Override
    public Category inputData() {
        Category newCategory = new Category();
        newCategory.setCategoryId(UUID.randomUUID().toString());
        System.out.print("1. 대분류 \t 2. 중분류");
        String type = scanner.next();
        System.out.print("이름 : ");
        String name = scanner.next();
        newCategory.setName(name);
        
        System.out.print("정렬 순서(숫자) : ");
        String sortOrder = scanner.next();
        newCategory.setSortOrder(sortOrder);
        if(type.equals("1")){
            newCategory.setParentId(null);
        } else if (type.equals("2")) {
            System.out.print("상위 대분류를 선택하세요");
            List<Category> mainCategories = categoryService.getMainCategories();
            if (mainCategories.isEmpty()) {
                     super.renderMessage("등록된 대분류가 없습니다. 대분류를 먼저 생성하세요.");
                     return null;
             }
            
            for (int i = 0; i < mainCategories.size(); i++) {
                     System.out.println((i + 1) + ". " + mainCategories.get(i).getName());
                     System.out.println((i + 1) + ". " + mainCategories.get(i).getCategoryId());
             }
            
            System.out.print("상위 대분류 번호 선택: ");
            int choice = scanner.nextInt();
            System.out.println("[debug]부모카테고리 명 :  " +  mainCategories.get(choice-1).getName());
            System.out.println("[debug]부모카테고리 아이디 :  " +  mainCategories.get(choice-1).getCategoryId());
            newCategory.setParentId(mainCategories.get(choice-1).getCategoryId());
        }	
        return newCategory;
    }
    

    @Override
    public void renderDetail(Category item) {

    }

    @Override
    public void renderList(List<Category> items) {

    }

    @Override
    public String inputSelection() {
	System.out.print("메뉴 선택: ");
        String choice = scanner.next();
        return choice;
    }
}
