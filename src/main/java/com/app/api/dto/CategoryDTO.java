package com.app.api.dto;

import com.app.api.model.Category;
import lombok.Data;

@Data
public class CategoryDTO {

    private int id;
    private String category;
    private int sale;
    private StoreDTO storeModel;
    private int status;

    public CategoryDTO() {
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.category = category.getCategory();
        this.sale = category.getSale();
        this.storeModel = new StoreDTO(category.getStoreModel());
        this.status = category.getStatus();
    }
}
