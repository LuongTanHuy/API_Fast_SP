package com.app.api.controller.mvc;

import com.app.api.dto.CategoryDTO;
import com.app.api.service.implement.TokenServiceImpl;
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


    @Autowired
    private TokenServiceImpl tokenService;

    @GetMapping("category")
    public ResponseEntity<List<CategoryDTO>> getListCategory(@RequestHeader("Authorization") String token) {
        Integer idStore = tokenService.validateTokenAndGetId(token);

        if (idStore == null || idStore == 0) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(categoryInterface.listCategory(idStore));
    }

    @PostMapping("category/add")
    public ResponseEntity<List<CategoryDTO>> add(@RequestHeader("Authorization") String idStore,
                                                 @RequestParam("category") String category,
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
