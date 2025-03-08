package com.app.api.service.interfaces;

import com.app.api.model.Category;

import java.util.List;

public interface ICategoryService {
    public List<Category> getAllByIdStore(int id_store);
    public boolean add(Category categoryModel);
    public boolean changeStatus(int id_category);
    public boolean update(Category categoryModel);
}
