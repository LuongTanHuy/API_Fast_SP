package com.app.api.service.interfaces;

import com.app.api.dto.ListStoreDTO;
import com.app.api.dto.StoreDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IStoreService {
    public  List<ListStoreDTO> listStore(String token, Integer page, Integer size);
    public List<ListStoreDTO> search(String token,String search);
    public StoreDTO updateInfo(String token, MultipartFile file, String name, String address, String email, String phone);

}
