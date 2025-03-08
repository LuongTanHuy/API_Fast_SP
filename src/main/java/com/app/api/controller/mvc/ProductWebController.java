package com.app.api.controller.mvc;

import com.app.api.model.Category;
import com.app.api.model.Product;
import com.app.api.model.Store;
import com.app.api.service.implement.FileStorageServiceImpl;
import com.app.api.service.implement.TokenServiceImpl;
import com.app.api.service.interfaces.IAccountService;
import com.app.api.service.interfaces.ICategoryService;
import com.app.api.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller("webServerProductController")
@RequestMapping("/FastFood/")
public class ProductWebController {

    @Autowired
    private IProductService productInterface;
    @Autowired
    private IAccountService accountInterface;
    @Autowired
    private ICategoryService categoryInterface;
    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @GetMapping("product")
    public String PageProduct(Model model, @CookieValue("FastFood") String token) {
        int idStore = this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId();

        model.addAttribute("account", this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))));
        model.addAttribute("title", "Món Ăn");
        model.addAttribute("listProduct", this.productInterface.productOfShop(idStore));
        model.addAttribute("allCategory", this.categoryInterface.getAllByIdStore(idStore));
        model.addAttribute("listCategory", this.productInterface.categoryOfShop(idStore));
        model.addAttribute("file_html", "/components/productBody");
        model.addAttribute("component", "productBody");
        return "pages/index";
    }

    @PostMapping("searchProduct")
    public String searchProduct(Model model,
                                @CookieValue("FastFood") String token,
                                @RequestParam("search") String search) {

        model.addAttribute("account", this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))));
        model.addAttribute("title", "Món Ăn");
        model.addAttribute("listProduct", this.productInterface.searchProductOfShop(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId(), search));
        model.addAttribute("allCategory", this.categoryInterface.getAllByIdStore(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId()));
        model.addAttribute("listCategory", this.productInterface.categoryOfShop(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId()));
        model.addAttribute("file_html", "/components/productBody");
        model.addAttribute("component", "productBody");
        return "pages/index";
    }

    @PostMapping("searchProductOfTheSameType")
    public String searchProductOfTheSameType(Model model,
                                @CookieValue("FastFood") String token,
                                @RequestParam("id_category") int id_category) {

        model.addAttribute("account", this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))));
        model.addAttribute("title", "Món Ăn");
        model.addAttribute("listProduct", this.productInterface.searchProductOfTheSameType(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId(), id_category));
        model.addAttribute("allCategory", this.categoryInterface.getAllByIdStore(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId()));
        model.addAttribute("listCategory", this.productInterface.categoryOfShop(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId()));
        model.addAttribute("file_html", "/components/productBody");
        model.addAttribute("component", "productBody");
        return "pages/index";
    }

    @GetMapping("changeStatusProduct/{id}")
    public ResponseEntity<?> changeStatusProduct(@PathVariable("id") int id) {
        this.productInterface.changeStatusProduct(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/product");
        return ResponseEntity.status(302).headers(headers).build();
    }

    @GetMapping("changeStatusCategory/{id}")
    public ResponseEntity<?> changeStatusCategory(@PathVariable("id") int id) {
        this.categoryInterface.changeStatus(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/product");
        return ResponseEntity.status(302).headers(headers).build();
    }

    @PostMapping("addCategory")
    public ResponseEntity<?> addCategory(@RequestParam("category") String category,
                                         @RequestParam("id_store") int id_store,
                                         @RequestParam("sale") int sale) {
        Category categoryModel = new Category();
        categoryModel.setCategory(category);
        categoryModel.setSale(sale);
        Store store = new Store();
        store.setId(id_store);
        categoryModel.setStoreModel(store);
        this.categoryInterface.add(categoryModel);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/product");
        return ResponseEntity.status(302).headers(headers).build();
    }

    @PostMapping("addProduct")
    public ResponseEntity<?> addProduct(@CookieValue("FastFood") String token,
                                        @RequestParam("id_category") int id_category,
                                        @RequestParam("name") String name,
                                        @RequestParam("price") double price,
                                        @RequestParam("file") MultipartFile file) {
//        Store
        Store storeModel = new Store();
        storeModel.setId(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId());

//        category
        Category categoryModel = new Category();
        categoryModel.setId(id_category);
        categoryModel.setStoreModel(storeModel);

//        product
        Product productModel = new Product();
        productModel.setName(name);
        productModel.setPrice(price);
        productModel.setCategoryModel(categoryModel);
        productModel.setStatus(0);

//        image
        productModel.setImage(this.fileStorageService.storeFile(file));
//        save
        this.productInterface.addProduct(productModel);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/product");
        return ResponseEntity.status(302).headers(headers).build();
    }

    @PostMapping("updateProduct")
    public ResponseEntity<?> updateProduct(@CookieValue("FastFood") String token,
                                           @RequestParam("id_product") int id_product,
                                           @RequestParam("id_category") int id_category,
                                           @RequestParam("name") String name,
                                           @RequestParam("price") double price,
                                           @RequestParam("file") MultipartFile file) {

        // store
        Store storeModel = new Store();
        storeModel.setId(this.accountInterface.getAccountDetailForWeb(Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token))).getStoreModel().getId());

        // category
        Category categoryModel = new Category();
        categoryModel.setId(id_category);
        categoryModel.setStoreModel(storeModel);

        // product
        Product productModel = new Product();
        productModel.setId(id_product);
        productModel.setName(name);
        productModel.setPrice(price);
        productModel.setCategoryModel(categoryModel);

        // image
        if (!file.isEmpty()) {
            String fileName = this.fileStorageService.storeFile(file);
            if (fileName != null) {
                productModel.setImage(fileName);
            }
        }

        // save
        this.productInterface.updateProduct(productModel);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/FastFood/product");
        return ResponseEntity.status(302).headers(headers).build();
    }

            @PostMapping("updateCategory")
            public ResponseEntity<?> updateProduct( @RequestParam("id_category") int id_category,
                                                   @RequestParam("category") String category,
                                                    @RequestParam("sale") int sale ){

                Category updateCategoryModel = new Category();
                updateCategoryModel.setId(id_category);
                updateCategoryModel.setCategory(category);
                updateCategoryModel.setSale(sale);
                this.categoryInterface.update(updateCategoryModel);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Location", "/FastFood/product");
                return ResponseEntity.status(302).headers(headers).build();
    }
}
