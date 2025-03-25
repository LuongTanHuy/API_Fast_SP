package com.app.api.service.implement;

import com.app.api.model.Banner;
import com.app.api.repository.IBannerRepository;
import com.app.api.service.interfaces.IBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements IBannerService
{
    private final  IBannerRepository bannerRepository;
    private final  TokenServiceImpl tokenService;
    private final  FileStorageServiceImpl fileStorageService;

    @Override
    public List<Banner> listBanner() {
        return this.bannerRepository.findAll();
    }

    @Override
    public List<Banner> add(String token, MultipartFile file, String text) {
        if(this.tokenService.validateTokenAndGetId(token).equals(null)){
            return null;
        }

        String fileName = this.fileStorageService.storeFile(file);
        if(this.bannerRepository.save(new Banner(fileName,text)).getId() > 0){
            return this.listBanner();
        }
        return null;
    }

    @Override
    public List<Banner> update(String token, MultipartFile file, String text,Integer id) {

        if(this.tokenService.validateTokenAndGetId(token).equals(null)){return null;}

        String fileName = null;
        if(!file.isEmpty()){fileName = this.fileStorageService.storeFile(file);}

        Optional<Banner> optionalBannerModel = this.bannerRepository.findById(id);
        if (optionalBannerModel.isPresent()) {
            Banner update= optionalBannerModel.get();
            if (fileName != null ) {update.setImage(fileName);}
            if (text != null ) {update.setText(text);}
            if(this.bannerRepository.save(update).getId() > 0){return this.listBanner();};
        }

        return null;
    }

    @Override
    public List<Banner> delete(String token,Integer id) {
        if(this.tokenService.validateTokenAndGetId(token).equals(null)){
            return null;
        }
        this.bannerRepository.deleteById(id);
        return this.listBanner();
    }

    @Override
    public List<Banner> search(String token, String search) {
        Integer idAccount = this.tokenService.validateTokenAndGetId(token);

        if(idAccount.equals(null)){return null;}
        return this.bannerRepository.findByKeyword(search);
    }
}
