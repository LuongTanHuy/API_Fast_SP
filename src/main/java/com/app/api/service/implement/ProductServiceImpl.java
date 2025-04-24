package com.app.api.service.implement;

import com.app.api.dto.ProductDTO;
import com.app.api.model.Category;
import com.app.api.model.Product;
import com.app.api.model.Store;
import com.app.api.repository.IOrderItemRepository;
import com.app.api.repository.IProductRepository;
import com.app.api.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final IOrderItemRepository orderItemRepository;
    private final TokenServiceImpl tokenService;
    private final FileStorageServiceImpl fileStorageService;
    public static final int SHOW_PRODUCT = 1;
    public static final int HIDDEN_PRODUCT = 0;

    private List<ProductDTO> convertToDTO(List<Product> productList, String type) {
        if (productList.isEmpty()) return Collections.emptyList();

        if ("mobileApp".equals(type)) {
            return productList.stream().map(ProductDTO::new).collect(Collectors.toList());
        }

        List<Integer> productIds = productList.stream().map(Product::getId).collect(Collectors.toList());

        Map<Integer, Integer> totalProductSoldMap = orderItemRepository.totalProductSold(productIds)
                .stream()
                .collect(Collectors.toMap(row -> (Integer) row[0], row -> row[1] != null ? ((Number) row[1]).intValue() : 0));

        Map<Integer, Double> totalRevenueMap = orderItemRepository.totalRevenue(productIds)
                .stream()
                .collect(Collectors.toMap(row -> (Integer) row[0], row -> row[1] != null ? ((Number) row[1]).doubleValue() : 0.0));

        return productList.stream().map(product -> {
            ProductDTO productDTO = new ProductDTO(product);

            productDTO.setTotalProductSold(totalProductSoldMap.getOrDefault(product.getId(), 0));
            productDTO.setTotalRevenue(totalRevenueMap.getOrDefault(product.getId(), 0.0));

            return productDTO;
        }).collect(Collectors.toList());
    }

    //Mobile App
    public List<ProductDTO> productOfTheSameType(String type,Integer idCategory,Integer page, Integer size){
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<Product> listProduct = this.productRepository.findByCategoryModelIdOrderByCategoryModelCategoryDesc(idCategory,pageable);
            return this.convertToDTO(listProduct,type);
        } catch (Exception e) {
            System.out.println("ProductServiceImpl - Error get productOfTheSameType: {}"+ e.getMessage());
            return Collections.emptyList();
        }
    };

    public List<ProductDTO> storeProduct(String type,String authorizationHeader,Integer page, Integer size){
        try {
            Integer idStore = type.equals("mobileApp")? Integer.valueOf(authorizationHeader):this.tokenService.validateTokenAndGetId(authorizationHeader);

            Pageable pageable = PageRequest.of(page, size);
            List<Product> listProduct = this.productRepository.findByCategoryModelStoreModelIdOrderByCategoryModelCategoryDesc(idStore,pageable).getContent();
            return this.convertToDTO(listProduct,type);
        } catch (Exception e) {
            System.out.println("ProductServiceImpl - Error get productOfShop: {}"+ e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    @Cacheable(value = "products", key = "#type + '_' + #page + '_' + #size")
    public List<ProductDTO> productList(String type, Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<Product> productList = productRepository.findAll(pageable).getContent();
            return convertToDTO(productList, type);
        } catch (Exception e) {
            System.out.println("ProductServiceImpl - Error get list product: {}"+ e.getMessage());
            return Collections.emptyList();
        }
    }

    //Web Admin

    @Override
    public List<ProductDTO> filterProduct(String authorizationHeader, Integer idCategory, Integer page, Integer size) {
        try {
            Integer idStore = this.tokenService.validateTokenAndGetId(authorizationHeader);

            Pageable pageable = PageRequest.of(page, size);

            List<Product> listProduct = this.productRepository.findByCategoryModelStoreModelIdAndCategoryModelIdOrderByCategoryModelCategoryDesc(idStore,idCategory,pageable);
            return this.convertToDTO(listProduct,"");
        } catch (Exception e) {
            return Collections.emptyList();
        }
    };

    @Override
    public List<ProductDTO> searchProductInStore(String authorizationHeader, String search) {
        try {
            Integer idStore = this.tokenService.validateTokenAndGetId(authorizationHeader);

            List<Product> listProduct = this.productRepository.findByNameContainingAndCategoryModelStoreModelIdOrderByCategoryModelCategoryDesc(search,idStore);
            return this.convertToDTO(listProduct,"");
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean add(String authorizationHeader, int idCategory, String name, double price, MultipartFile file) {

        // Store
        Store storeModel = new Store();
        storeModel.setId(Integer.valueOf(this.tokenService.validateTokenAndGetId(authorizationHeader)));

        // category
        Category categoryModel = new Category();
        categoryModel.setId(idCategory);
        categoryModel.setStoreModel(storeModel);

        // product
        Product productModel = new Product();
        productModel.setName(name);
        productModel.setPrice(price);
        productModel.setCategoryModel(categoryModel);
        productModel.setStatus(0);

        // image
        productModel.setImage(this.fileStorageService.storeFile(file));

        return this.productRepository.save(productModel).getId() > 0;
    }

//    @Override
//    public boolean update(String authorizationHeader,Integer idProduct, Integer idCategory, String name, double price, MultipartFile file) {
//
//        Integer idStore = this.tokenService.validateTokenAndGetId(authorizationHeader);
//
//        Optional<Product> getProductModel = this.productRepository.findById(idProduct);
//
//        Category categoryModel = new Category();
//        categoryModel.setId(idCategory);
//
//        if (getProductModel.isPresent() && getProductModel.get().getCategoryModel().getStoreModel().getId() == idStore) {
//            Product updateProductModel = getProductModel.get();
//            updateProductModel.setName(name);
//            if (!file.isEmpty()) {
//                String fileName = this.fileStorageService.storeFile(file);
//                if (fileName != null) {
//                    updateProductModel.setImage(fileName);
//                }
//            }
//            updateProductModel.setPrice(price);
//            updateProductModel.setCategoryModel(categoryModel);
//
//            return this.productRepository.save(updateProductModel).getId() > 0;
//        }
//
//        return false;
//    }
@Override
public boolean update(String authorizationHeader, Integer idProduct, Integer idCategory, String name, double price, MultipartFile file) {
    Integer idStore = this.tokenService.validateTokenAndGetId(authorizationHeader);

    Optional<Product> getProductModel = this.productRepository.findById(idProduct);

    Category categoryModel = new Category();
    categoryModel.setId(idCategory);

    if (getProductModel.isPresent() && getProductModel.get().getCategoryModel().getStoreModel().getId() == idStore) {
        Product updateProductModel = getProductModel.get();
        updateProductModel.setName(name);

        // Kiểm tra null trước khi dùng file
        if (file != null && !file.isEmpty()) {
            String fileName = this.fileStorageService.storeFile(file);
            if (fileName != null) {
                updateProductModel.setImage(fileName);
            }
        }

        updateProductModel.setPrice(price);
        updateProductModel.setCategoryModel(categoryModel);

        return this.productRepository.save(updateProductModel).getId() > 0;
    }

    return false;
}


    @Override
    public boolean changeStatus(int idProduct) {
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
    @Override
    public List<ProductDTO> getAllProducts(String type, String authorizationHeader) {
        try {
            Integer idStore = type.equals("mobileApp")
                    ? Integer.valueOf(authorizationHeader)
                    : this.tokenService.validateTokenAndGetId(authorizationHeader);

            List<Product> productList = this.productRepository
                    .findByCategoryModelStoreModelIdOrderByCategoryModelCategoryDesc(idStore);

            return convertToDTO(productList, type);
        } catch (Exception e) {
            System.out.println("ProductServiceImpl - Error getAllProducts: " + e.getMessage());
            return Collections.emptyList();
        }
    }



}
