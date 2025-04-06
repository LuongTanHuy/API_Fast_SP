package com.app.api.controller.mvc;

import com.app.api.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/web/")
public class ProductWebController {

    @Autowired
    private IProductService productInterface;

    @GetMapping("product")
    public ResponseEntity<?> storeProduct(@RequestHeader("Authorization") String authorizationHeader,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size) {
        return this.productInterface.storeProduct("",authorizationHeader, page, size) != null ?
                ResponseEntity.status(200).body(this.productInterface.storeProduct("",authorizationHeader, page, size)) :
                ResponseEntity.status(404).body(null);
    }

    @PostMapping("product/search")
    public ResponseEntity<?> searchProductInStore(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("search") String search) {

        return this.productInterface.searchProductInStore(authorizationHeader, search) != null ?
                ResponseEntity.status(200).body(this.productInterface.searchProductInStore(authorizationHeader, search)) :
                ResponseEntity.status(404).body(null);
    }

    @PostMapping("product/filter")
    public ResponseEntity<?> filterProduct(@RequestHeader("Authorization") String authorizationHeader,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size,
                                             @RequestParam("idCategory") Integer idCategory) {
        return this.productInterface.filterProduct(authorizationHeader, idCategory,page,size) != null ?
                ResponseEntity.status(200).body(this.productInterface.filterProduct(authorizationHeader, idCategory,page,size)) :
                ResponseEntity.status(404).body(null);
    }

    @PostMapping("product/add")
    public ResponseEntity<?> add(@RequestHeader("Authorization") String authorizationHeader,
                                        @RequestParam("idCategory") int idCategory,
                                        @RequestParam("name") String name,
                                        @RequestParam("price") double price,
                                        @RequestParam("file") MultipartFile file) {

        return this.productInterface.add(authorizationHeader,idCategory,name,price,file) == true ?
                ResponseEntity.status(200).body(null) : ResponseEntity.status(404).body(null);
    }

//    @PostMapping("product/update")
//    public ResponseEntity<?> update(@RequestHeader("Authorization") String authorizationHeader,
//                                           @RequestParam("idProduct") int idProduct,
//                                           @RequestParam("idCategory") int idCategory,
//                                           @RequestParam("name") String name,
//                                           @RequestParam("price") double price,
//                                           @RequestParam("file") MultipartFile file) {
//
//        return this.productInterface.update(authorizationHeader,idProduct,idCategory,name,price,file) == true ?
//                ResponseEntity.status(200).body(null) : ResponseEntity.status(404).body(null);
//    }

    @PostMapping("product/update")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String authorizationHeader,
                                    @RequestParam("idProduct") int idProduct,
                                    @RequestParam("idCategory") int idCategory,
                                    @RequestParam("name") String name,
                                    @RequestParam("price") double price,
                                    @RequestParam(value = "file", required = false) MultipartFile file) {
        return this.productInterface.update(authorizationHeader, idProduct, idCategory, name, price, file)
                ? ResponseEntity.status(200).body(null)
                : ResponseEntity.status(404).body(null);
    }


    @GetMapping("product/change-status/{idProduct}")
    public ResponseEntity<?> changeStatus(@PathVariable("idProduct") int idProduct) {
      return this.productInterface.changeStatus(idProduct) == true ?
                ResponseEntity.status(200).body(null) : ResponseEntity.status(404).body(null);

    }

}
