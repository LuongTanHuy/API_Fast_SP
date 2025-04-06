package com.app.api.service.implement;

import com.app.api.dto.CategoryDTO;
import com.app.api.model.Category;
import com.app.api.model.Store;
import com.app.api.repository.ICategoryRepository;
import com.app.api.service.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final  ICategoryRepository categoryRepository;
    private final TokenServiceImpl tokenService;
    private static final Integer SHOW = 0;
    private static final Integer HIDDEN = 1;
    @Override
//    @Cacheable(value = "categories", key = "#idStore")

    public List<CategoryDTO> listCategory(Integer idStore) {
        try {
            List<Category> listCategory = this.categoryRepository.getAllByIdStore(idStore);
            return listCategory.stream().map(CategoryDTO::new).collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println("CategoryServiceImpl - Error get listCategory: {}"+ e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<CategoryDTO> changeStatus(Integer idCategory) {
        Optional<Category> getCategoryModel = this.categoryRepository.findById(idCategory);
        if(getCategoryModel.isPresent()){
            if(getCategoryModel.get().getStatus() == SHOW){
               Category update = getCategoryModel.get();
               update.setStatus(HIDDEN);
               this.categoryRepository.save(update);
            }else{
                Category update = getCategoryModel.get();
                update.setStatus(SHOW);
                this.categoryRepository.save(update);
            }
            return this.listCategory(getCategoryModel.get().getStoreModel().getId());
        }
        return null;
    }

    @Override
    public List<CategoryDTO> add(String category, String idStore, Integer sale) {

        Category categoryModel = new Category();
        categoryModel.setCategory(category);
        categoryModel.setSale(sale);
        Store store = new Store();
        store.setId(this.tokenService.validateTokenAndGetId(idStore));
        categoryModel.setStoreModel(store);

        this.categoryRepository.save(categoryModel);

        return this.listCategory(this.tokenService.validateTokenAndGetId(idStore));
    }

    @Override
    public List<CategoryDTO> update(Integer idCategory, String category, Integer sale) {

        Optional<Category> getCategory = this.categoryRepository.findById(idCategory);
        if(getCategory.isPresent()){
            Category update = getCategory.get();
            update.setCategory(category);
            update.setSale(sale);
            this.categoryRepository.save(update);
            return this.listCategory(update.getStoreModel().getId());
        }
        return null;
    }

}
