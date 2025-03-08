package com.app.api.service.implement;

import com.app.api.model.Category;
import com.app.api.model.Store;
import com.app.api.repository.ICategoryRepository;
import com.app.api.repository.IOrderItemRepository;
import com.app.api.repository.IStoreRepository;
import com.app.api.service.interfaces.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements IStoreService {
    @Autowired
    private IStoreRepository storeRepository;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IOrderItemRepository orderItemRepository;
    private static final int FINISH = 3;

    private List<String> convertListCategory(List<Category> listCategory) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < listCategory.size(); i++) {
            result.add(listCategory.get(i).getCategory());
        }
        return result;
    }

    private int totalOrdersSold(int idStore) {
        return this.orderItemRepository.listOderItemOfStore(idStore,FINISH).size();
    }

    private double revenue(int idStore) {
        DecimalFormat decimalFormat = new DecimalFormat("#,000");
        double result = 0;
        for(int i=0 ;i< this.orderItemRepository.listOderItemOfStore(idStore,FINISH).size();i++){
            result += this.orderItemRepository.listOderItemOfStore(idStore,FINISH).get(i).getPrice();
        }
        return Double.parseDouble(decimalFormat.format(result));
    }

    @Override
    public List<Store> listStore(Pageable pageable) {
        Page<Store> listStore = this.storeRepository.findAll(pageable);
        List<Store> containerListStore = listStore.toList();
        List<Store> resultListStore = new ArrayList<>();

        for(int i=0;i< containerListStore.size();i++){
            int idStore = containerListStore.get(i).getId();
            List<Category> listCategory = this.categoryRepository.getAllByIdStore(idStore);

            Store object = containerListStore.get(i);
            object.setListCategory(this.convertListCategory(listCategory));
            object.setTotalOrdersSold(this.totalOrdersSold(idStore));
            object.setRevenue(this.revenue(idStore));

            resultListStore.add(object);

        }
        return resultListStore;
    }

    @Override
    public List<Store> getAllRequestOpenStore() {
        return this.storeRepository.getAllRequestOpenStore();
    }

    @Override
    public int totalStore(){
        return this.storeRepository.findAll().size();
    }

    @Override
    public List<Store> search(String search) {
        return this.storeRepository.findByKeyword(search);
    }

    @Override
    public boolean updateInfo(Store storeModel) {
        Optional<Store> getStoreModel = this.storeRepository.findById(storeModel.getId());
        if (getStoreModel.isPresent()) {
            Store updateStoreModel = getStoreModel.get();
            updateStoreModel.setName(storeModel.getName());
            updateStoreModel.setAddress(storeModel.getAddress());
            updateStoreModel.setEmail(storeModel.getEmail());
            updateStoreModel.setPhone(storeModel.getPhone());

            if (storeModel.getImage() != null && !storeModel.getImage().isEmpty()) {
                updateStoreModel.setImage(storeModel.getImage());
            }

            this.storeRepository.save(updateStoreModel);
            return true;
        }
        return false;
    }

    @Override
    public int addStore(Store storeModel) {
        return this.storeRepository.save(storeModel).getId();
    }

}
