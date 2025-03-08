package com.app.api.controller.api;

import com.app.api.model.Category;
import com.app.api.model.Product;
import com.app.api.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class ProductController {

    @Autowired
    private IProductService productInterface;

    @GetMapping("product")
    public List<Product> listProducts(){
        return this.productInterface.listProducts();
    }

    @GetMapping("product-detail/{idProduct}")
    public Product getProductDetail(@PathVariable("idProduct") int idProduct){
        return this.productInterface.getProductDetail(idProduct);
    }

    @GetMapping("product-of-the-same-type/{idCategory}")
    public ResponseEntity<List<Product>> productOfTheSameType(@PathVariable("idCategory") int idCategory){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(this.productInterface.productOfTheSameType(idCategory));
    }

    @GetMapping("category-of-shop-api/{idStore}")
    public List<Category> categoryOfShop(@PathVariable("idStore") int idStore){
        return this.productInterface.categoryOfShop(idStore);
    }

    @GetMapping("product-of-shop-api/{idStore}")
    public List<Product> productOfShop(@PathVariable("idStore") int idStore){
        return this.productInterface.productOfShop(idStore);
    }
}
