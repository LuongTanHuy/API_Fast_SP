package com.app.api.service.interfaces;

import com.app.api.model.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBannerService {
    public List<Banner> listBanner();
    public List<Banner> add(String token, MultipartFile file, String text);
    public List<Banner> update(String token, MultipartFile file, String text,Integer id);
    public List<Banner> delete(String token,Integer idBanner);
    public List<Banner> search(String token,String search);
}
