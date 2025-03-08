package com.app.api.service.implement;

import com.app.api.model.Category;
import com.app.api.model.Store;
import com.app.api.repository.ICategoryRepository;
import com.app.api.service.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<Category> getAllByIdStore(int id_store) {
        List<Category> listCategory = this.categoryRepository.getAllByIdStore(id_store);
        List<Category> result_Category = new ArrayList<>();
        for(int i =0 ;i< listCategory.size();i++) {
            Store newStoreModel = new Store();
            newStoreModel.setId(listCategory.get(i).getStoreModel().getId());
            Category newCategoryModel = new Category();
            newCategoryModel.setId(listCategory.get(i).getId());
            newCategoryModel.setCategory(listCategory.get(i).getCategory());
            newCategoryModel.setStoreModel(newStoreModel);
            newCategoryModel.setStatus(listCategory.get(i).getStatus());
            newCategoryModel.setSale(listCategory.get(i).getSale());
            result_Category.add(newCategoryModel);
        }
        return result_Category;
    }

    @Override
    public boolean add(Category categoryModel) {
        this.categoryRepository.save(categoryModel);
        return true;
    }

    @Override
    public boolean changeStatus(int id_category) {
        Optional<Category> getCategoryModel = this.categoryRepository.findById(id_category);
        if(getCategoryModel.isPresent()){
            if(getCategoryModel.get().getStatus() == 0){
               Category update = getCategoryModel.get();
               update.setStatus(1);
                this.categoryRepository.save(update);
            }else{
                Category update = getCategoryModel.get();
                update.setStatus(0);
                this.categoryRepository.save(update);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Category categoryModel) {
        Optional<Category> getCategory = this.categoryRepository.findById(categoryModel.getId());
        if(getCategory.isPresent()){
            Category update = getCategory.get();
            update.setCategory(categoryModel.getCategory());
            update.setSale(categoryModel.getSale());
            this.categoryRepository.save(update);
            return true;
        }
        return false;
    }


}
