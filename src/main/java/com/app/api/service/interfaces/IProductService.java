package com.app.api.service.interfaces;

import com.app.api.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {
    public List<ProductDTO> productList(String type,Integer page, Integer size);
    public List<ProductDTO> storeProduct(String type, String authorizationHeader, Integer page, Integer size);
    public List<ProductDTO> productOfTheSameType(String type,Integer idCategory, Integer page, Integer size);
    public List<ProductDTO>filterProduct(String authorizationHeader, Integer idCategory, Integer page, Integer size);
    public List<ProductDTO> searchProductInStore(String authorizationHeader, String search);


    public boolean add(String authorizationHeader, int idCategory, String name, double price, MultipartFile file);
    public boolean update(String authorizationHeader,Integer idProduct, Integer idCategory, String name, double price, MultipartFile file);
    public boolean changeStatus(int id);
}
