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
    @Override
    public void showMenu() {
        super.printHeader("카테고리 관리");
        super.renderMessage("1. 카테고리 생성 \t 2. 카테고리 수정 \t 3.카테코리 삭제");
    }

    @Override
    public Category inputData() {
        Category newCategory = null;
        System.out.print("1. 대분류 \t 2. 중분류");
        String type = scanner.next();
        System.out.print("이름 : ");
        String name = scanner.next();
        if(type.equals("1")){
            newCategory = new Category();
            newCategory.setParent(null);
            newCategory.setName(name);
            newCategory.setCategoryId(UUID.randomUUID().toString());
        } else if (type.equals("2")) {
            System.out.print("상위 대분류를 선택하세요");
            List<Category> mainCategories = categoryService.getMainCategories();
            for(int i = 0; i< mainCategories.size();i++){
                super.renderMessage((i+1) + ". " + mainCategories.get(i).getName());
            }
            int choice = scanner.nextInt();
            newCategory.setCategoryId(UUID.randomUUID().toString());
            newCategory.setParent(mainCategories.get(choice-1));
            newCategory.setName(name);
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
        String choice = scanner.next();
        return choice;
    }
}
