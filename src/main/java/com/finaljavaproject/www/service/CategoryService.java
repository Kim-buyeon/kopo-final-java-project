package com.finaljavaproject.www.service;

import com.finaljavaproject.www.domain.Category;
import com.finaljavaproject.www.repository.Repository;

import java.util.Comparator;
import java.util.List;

public class CategoryService extends AbstractService<Category, String>{

    public CategoryService(Repository<Category, String> repository) {
        super(repository);
    }

    public void create(Category category){
        repository.save(category);
    }

    public void update(Category category){
        repository.save(category);
    }

    //카테고리 삭제하는 메서드 하위 카테고리가 있는 대분류이거나  해당하는 카테고리 없는 경우를 제외하고 카테고리 삭제
    public void remove(String id){
        List<Category> categories = getAllCategoriesOrdered();
        Category category = categories.stream().filter(c -> c.getCategoryId().equalsIgnoreCase(id))
                .findFirst().orElse(null);
        if(category == null){
            System.out.println("해당 카테고리를 찾을 수 없습니다.");
            return;
        }
        
        if(category.getParent() == null){
            boolean hasChildren = categories.stream()
                            .anyMatch(c ->c.getParent()!= null && c.getParent().getCategoryId().equalsIgnoreCase(id));
            if(hasChildren){
                System.out.println("하위 카테고리가 있는 대분류는 지울 수 없습니다.");
            }
            repository.deleteById(id);

        } else{
            repository.deleteById(id);
            System.out.println("카테고리를 지웠습니다.");
        }
    }

    //모든 카테고리 대, 중 분류 구분없이 가져오는 메서드
    public List<Category> getAllCategoriesOrdered(){
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Category::getSortOrder))
                .toList();
    }

    //모든 중 분류들을 가져오는 메서드
    public List<Category> getChildren(String parentId) {
        return repository.findAll().stream()
                .filter(c -> c.getParent() != null && c.getParent().getCategoryId().equals(parentId))
                .toList();
    }

    //모든 대 분류들을 가져오는 메서드
    public List<Category> getMainCategories() {
        return repository.findAll().stream()
                .filter(c -> c.getParent().getCategoryId() == null)
                .sorted(Comparator.comparing(Category::getSortOrder))
                .toList();
    }


}
