package com.app.api.service.interfaces;

import com.app.api.model.Banner;

import java.util.List;

public interface IBannerService {
    public List<Banner> getAll();
    public boolean addBanner(Banner bannerModel);
    public boolean updateBanner(Banner bannerModel);
    public boolean deleteBanner(int id);
    public List<Banner> search(String search);
}
