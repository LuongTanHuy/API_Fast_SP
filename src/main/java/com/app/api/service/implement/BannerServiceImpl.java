package com.app.api.service.implement;

import com.app.api.model.Banner;
import com.app.api.repository.IBannerRepository;
import com.app.api.service.interfaces.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BannerServiceImpl implements IBannerService
{
    @Autowired
    private IBannerRepository bannerRepository;


    @Override
    public List<Banner> getAll() {
        return this.bannerRepository.findAll();
    }

    @Override
    public boolean addBanner(Banner bannerModel) {
        if(this.bannerRepository.save(bannerModel).getId() > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateBanner(Banner bannerModel) {
        Optional<Banner> optionalBannerModel = this.bannerRepository.findById(bannerModel.getId());

        if (optionalBannerModel.isPresent()) {
            Banner updateBannerModel = optionalBannerModel.get();

            if (bannerModel.getImage() != null ) {
                updateBannerModel.setImage(bannerModel.getImage());
            }

            if (bannerModel.getText() != null ) {
                updateBannerModel.setText(bannerModel.getText());
            }
            this.bannerRepository.save(updateBannerModel);

            return true;
        }

        return false;
    }

    @Override
    public boolean deleteBanner(int id) {
        this.bannerRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Banner> search(String search) {
        return this.bannerRepository.findByKeyword(search);
    }
}
