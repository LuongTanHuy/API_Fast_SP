package com.app.api.service.implement;

import com.app.api.dto.ListStoreDTO;
import com.app.api.dto.StoreDTO;
import com.app.api.model.Category;
import com.app.api.model.OrderItem;
import com.app.api.model.Store;
import com.app.api.repository.ICategoryRepository;
import com.app.api.repository.IOrderItemRepository;
import com.app.api.repository.IStoreRepository;
import com.app.api.service.interfaces.IStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements IStoreService {
    private final IStoreRepository storeRepository;
    private final ICategoryRepository categoryRepository;
    private final IOrderItemRepository orderItemRepository;

    private final TokenServiceImpl tokenService;
    private final FileStorageServiceImpl fileStorageService;
    private static final int FINISH = 3;

    private List<String> convertListCategory(List<Category> listCategory) {
        return listCategory.stream().map(Category::getCategory).collect(Collectors.toList());
    }

    private Integer totalOrdersSold(Integer idStore) {
        return this.orderItemRepository.listOderItemOfStore(idStore, FINISH).size();
    }

    private Double revenue(Integer idStore) {
        List<OrderItem> orderItems = this.orderItemRepository.listOderItemOfStore(idStore, FINISH);
        return orderItems.stream().mapToDouble(OrderItem::getPrice).sum();
    }

    private List<ListStoreDTO> convertListStoreDTO(List<Store> listStore){
        Map<Integer, Integer> totalOrdersMap = listStore.stream()
                .collect(Collectors.toMap(Store::getId, store -> totalOrdersSold(store.getId())));

        Map<Integer, Double> revenueMap = listStore.stream()
                .collect(Collectors.toMap(Store::getId, store -> revenue(store.getId())));

        return listStore.stream()
                .map(store -> new ListStoreDTO(
                        store,
                        totalOrdersMap.get(store.getId()),
                        revenueMap.get(store.getId()),
                        convertListCategory(this.categoryRepository.getAllByIdStore(store.getId()))
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ListStoreDTO> listStore(String token, Integer page, Integer size) {
        Integer id = this.tokenService.validateTokenAndGetId(token);

        if (id == null) {
            return Collections.emptyList();
        }

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC);
        List<Store> listStore = this.storeRepository.findAll(pageable).getContent();

        return  this.convertListStoreDTO(listStore);

    }


    @Override
    public List<ListStoreDTO> search(String token, String search) {
        Integer id = this.tokenService.validateTokenAndGetId(token);

        if (id == null) {
            return Collections.emptyList();
        }

        return this.convertListStoreDTO(this.storeRepository.findByKeyword(search));
    }

    @Override
    public StoreDTO updateInfo(String token, MultipartFile file, String name, String address, String email, String phone)
    {
        Integer idStore = this.tokenService.validateTokenAndGetId(token);

        Optional<Store> getStoreModel = this.storeRepository.findById(idStore);
        if (getStoreModel.isPresent()) {
            Store updateStoreModel = getStoreModel.get();
            updateStoreModel.setName(name);
            updateStoreModel.setAddress(address);
            updateStoreModel.setEmail(email);
            updateStoreModel.setPhone(phone);

            if(!file.isEmpty()){
                updateStoreModel.setImage(this.fileStorageService.storeFile(file));
            }

            ;
            return this.storeRepository.save(updateStoreModel).getId() >0 ? new StoreDTO(updateStoreModel): null;
        }
        return null;
    }

}

