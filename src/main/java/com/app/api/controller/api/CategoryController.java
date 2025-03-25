package com.app.api.controller.api;

import com.app.api.dto.CategoryDTO;
import com.app.api.service.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/app/")
public class CategoryController {

    @Autowired
    private ICategoryService categoryInterface;

    @GetMapping("category/list")
    public ResponseEntity<List<CategoryDTO>> listCategory(@RequestParam("idStore") Integer idStore) {
        List<CategoryDTO> categories = categoryInterface.listCategory(idStore);
        return ResponseEntity.ok(categories);
    }

}
