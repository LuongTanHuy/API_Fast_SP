package com.app.api.controller.api;

import com.app.api.dto.ProductDTO;
import com.app.api.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v2/app/")
public class ProductController {

    @Autowired
    private IProductService productInterface;

    @GetMapping("product")
    public ResponseEntity<List<ProductDTO>> productList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        if (size > 100) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List<ProductDTO> products = productInterface.productList("mobileApp", page, size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("product/type/{idCategory}")
    public ResponseEntity<List<ProductDTO>> productOfTheSameType(@PathVariable("idCategory") int idCategory,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "20") int size){
        return ResponseEntity.ok().body(this.productInterface.productOfTheSameType("mobileApp",idCategory, page, size));
    }

    @GetMapping("product/store/{idStore}")
    public ResponseEntity<List<ProductDTO>> storeProduct(@PathVariable("idStore") String idStore,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int size){
        if (size > 100) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List<ProductDTO> products =  this.productInterface.storeProduct("mobileApp",idStore, page, size);
        return ResponseEntity.ok(products);
    }
}
