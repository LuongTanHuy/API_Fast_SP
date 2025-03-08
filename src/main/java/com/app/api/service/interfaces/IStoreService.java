package com.app.api.service.interfaces;

import com.app.api.model.Store;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStoreService {
    public int totalStore();
    public  List<Store> listStore(Pageable pageable);
    public List<Store> getAllRequestOpenStore();
    public List<Store> search(String search);
    public boolean updateInfo(Store storeModel);
    public int addStore(Store storeModel);

}
