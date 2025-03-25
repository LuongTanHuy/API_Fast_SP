package com.app.api.controller.mvc;

import com.app.api.dto.CategoryDTO;
import com.app.api.service.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/web/")
public class CategoryWebController {

    @Autowired
    private ICategoryService categoryInterface;

    @PostMapping("category/add")
    public ResponseEntity<List<CategoryDTO>> add(@RequestParam("category") String category,
                                                 @RequestParam("idStore") Integer idStore,
                                                 @RequestParam("sale") Integer sale) {

        return ResponseEntity.status(200).body(this.categoryInterface.add(category, idStore, sale));
    }

    @PostMapping("category/update")
    public ResponseEntity<List<CategoryDTO>> update(@RequestParam("idCategory") Integer idCategory,
                                                    @RequestParam("category") String category,
                                                    @RequestParam("sale") Integer sale) {

        return ResponseEntity.status(200).body(this.categoryInterface.update(idCategory, category, sale));
    }

    @GetMapping("changeStatusCategory/{id}")
    public ResponseEntity<List<CategoryDTO>> changeStatus(@PathVariable("id") Integer id) {
        return ResponseEntity.status(200).body(this.categoryInterface.changeStatus(id));
    }

}
