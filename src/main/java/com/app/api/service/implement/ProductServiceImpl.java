package com.app.api.service.implement;

import com.app.api.model.Category;
import com.app.api.model.Product;
import com.app.api.model.Store;
import com.app.api.repository.ICategoryRepository;
import com.app.api.repository.IOrderItemRepository;
import com.app.api.repository.IProductRepository;
import com.app.api.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final IOrderItemRepository orderItemRepository;
    public static final int SHOW_PRODUCT = 1;
    public static final int HIDDEN_PRODUCT = 0;

    @Autowired
    public ProductServiceImpl(IProductRepository productRepository, ICategoryRepository categoryRepository, IOrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.orderItemRepository = orderItemRepository;
    }

    private List<Product> listInformationProducts(List<Product> list_product){
        List<Product> resultProduct = new ArrayList<>();

        for(int i =0 ;i< list_product.size();i++) {
            Product productModel = new Product();

            // Store
            Store storeModel = new Store();
            storeModel.setId(list_product.get(i).getCategoryModel().getStoreModel().getId());
            storeModel.setImage(list_product.get(i).getCategoryModel().getStoreModel().getImage());
            storeModel.setName(list_product.get(i).getCategoryModel().getStoreModel().getName());
            storeModel.setAddress(list_product.get(i).getCategoryModel().getStoreModel().getAddress());
            storeModel.setEmail(list_product.get(i).getCategoryModel().getStoreModel().getEmail());
            storeModel.setPhone(list_product.get(i).getCategoryModel().getStoreModel().getPhone());

            // Category
            Category categoryModel = new Category();
            categoryModel.setId(list_product.get(i).getCategoryModel().getId());
            categoryModel.setCategory(list_product.get(i).getCategoryModel().getCategory());
            categoryModel.setSale(list_product.get(i).getCategoryModel().getSale());
            categoryModel.setStoreModel(storeModel);
            categoryModel.setStatus(list_product.get(i).getCategoryModel().getStatus());

            //Product
            productModel.setId(list_product.get(i).getId());
            productModel.setImage(list_product.get(i).getImage());
            productModel.setPrice(list_product.get(i).getPrice());
            productModel.setName(list_product.get(i).getName());
            productModel.setStatus(list_product.get(i).getStatus());

            Integer totalProductSold = this.orderItemRepository.totalProductSold(list_product.get(i).getId()) ;
            productModel.setTotalProductSold(totalProductSold == null ? 0: totalProductSold );

            Double totalRevenue = this.orderItemRepository.totalRevenue(list_product.get(i).getId()) ;
            productModel.setTotalRevenue(totalRevenue == null ? 0 : totalRevenue);

            productModel.setCategoryModel(categoryModel);

            resultProduct.add(i,productModel);
        }

        return resultProduct;
    }

    @Override
    public List<Product> listProducts() {
        List<Product> listProduct = this.productRepository.getProductSale();
        return this.listInformationProducts(listProduct);
    }

    @Override
    public Product getProductDetail(int id) {
        Product productModel = new Product();
        Store storeModel = new Store();
        storeModel.setId(this.productRepository.findById(id).get().getCategoryModel().getStoreModel().getId());
        storeModel.setName(this.productRepository.findById(id).get().getCategoryModel().getStoreModel().getName());
        storeModel.setPhone(this.productRepository.findById(id).get().getCategoryModel().getStoreModel().getPhone());

        Category categoryModel = new Category();
        categoryModel.setId(this.productRepository.findById(id).get().getCategoryModel().getId());
        categoryModel.setCategory(this.productRepository.findById(id).get().getCategoryModel().getCategory());
        categoryModel.setSale(this.productRepository.findById(id).get().getCategoryModel().getSale());
        categoryModel.setStoreModel(storeModel);
        categoryModel.setStatus(this.productRepository.findById(id).get().getCategoryModel().getStatus());


        productModel.setId(this.productRepository.findById(id).get().getId());
        productModel.setImage(this.productRepository.findById(id).get().getImage());
        productModel.setPrice(this.productRepository.findById(id).get().getPrice());
        productModel.setName(this.productRepository.findById(id).get().getName());
        productModel.setStatus(this.productRepository.findById(id).get().getStatus());
        productModel.setCategoryModel(categoryModel);
        return productModel;
    }

    public List<Product> productOfTheSameType(int idCategory){
        List<Product> listProduct = this.productRepository.productOfTheSaneType(idCategory);
        return this.listInformationProducts(listProduct);
    };

    public List<Product> productOfShop(int idStore){
        List<Product> listProduct = this.productRepository.productOfShop(idStore);
       return this.listInformationProducts(listProduct);
    }

    @Override
    public List<Product> searchProductOfTheSameType(int idStore, int idCategory){
        List<Product> listProduct = this.productRepository.searchProductOfTheSameType(idStore,idCategory);
       return this.listInformationProducts(listProduct);
    };

    @Override
    public List<Product> searchProductOfShop(int idStore, String search) {
        List<Product> listProduct = this.productRepository.searchProductOfShop(idStore,search);
       return this.listInformationProducts(listProduct);
    }

    @Override
    public boolean addProduct(Product productModel) {
        return this.productRepository.save(productModel).getId() > 0;
    }

    @Override
    public List<Category> categoryOfShop(int id_store) {
        List<Category> listCategoryModel = this.categoryRepository.getAllByIdStore(id_store);
        List<Category> resultCategoryModel = new ArrayList<>();
        for(Category object:listCategoryModel){
            Store newStoreModel = new Store();
            newStoreModel.setId(object.getStoreModel().getId());
            object.setStoreModel(newStoreModel);
            object.setProducts(null);
            resultCategoryModel.add(object);
        }
        return resultCategoryModel;
    }

    @Override
    public boolean updateProduct(Product productModel) {
        Optional<Product> getProductModel = this.productRepository.findById(productModel.getId());
        if (getProductModel.isPresent()) {
            Product updateProductModel = getProductModel.get();
            updateProductModel.setName(productModel.getName());
            if (productModel.getImage() != null) {
                updateProductModel.setImage(productModel.getImage());
            }
            updateProductModel.setPrice(productModel.getPrice());
            updateProductModel.setCategoryModel(productModel.getCategoryModel());

            return this.productRepository.save(updateProductModel).getId() > 0;
        }

        return false;
    }

    @Override
    public boolean changeStatusProduct(int idProduct) {
        Optional<Product> getProductModel = this.productRepository.findById(idProduct);

        if (getProductModel.isPresent()) {
            Product updateProductModel = getProductModel.get();
            if (updateProductModel.getStatus() == HIDDEN_PRODUCT) {
                updateProductModel.setStatus(SHOW_PRODUCT);
            } else {
                updateProductModel.setStatus(HIDDEN_PRODUCT);
            }

            return this.productRepository.save(updateProductModel).getId() > 0;
        }

        return false;
    }

}
