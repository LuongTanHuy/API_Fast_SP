package com.app.api.service.interfaces;

import com.app.api.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    public List<CategoryDTO> listCategory(Integer id_store);
    public List<CategoryDTO> add(String category, Integer idStore, Integer sale);
    public List<CategoryDTO> changeStatus(Integer idCategory);
    public List<CategoryDTO> update(Integer idCategory, String category, Integer sale);
}
