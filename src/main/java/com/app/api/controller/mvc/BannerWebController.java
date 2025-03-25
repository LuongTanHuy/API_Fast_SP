package com.app.api.controller.mvc;

import com.app.api.model.Banner;
import com.app.api.service.interfaces.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v2/web/")
public class BannerWebController {
    @Autowired
    private IBannerService bannerInterface;

    @GetMapping("banner")
    public ResponseEntity<List<Banner>> listBanner(){
        return ResponseEntity.status(200).body(this.bannerInterface.listBanner());
    }

    @GetMapping("banner/search")
    public ResponseEntity<List<Banner>> search(@RequestHeader("Authorization") String token,
                               @RequestParam("search") String search){

     List<Banner> banner = this.bannerInterface.search(token,search);
     if(banner.equals(null)){
         return ResponseEntity.status(401).build();
     }
     return ResponseEntity.status(200).body(banner);
    }

    @PostMapping("banner/add")
    public ResponseEntity<List<Banner>> add(@RequestHeader("Authorization") String token,
                                                  @RequestParam("file") MultipartFile file,
                                                  @RequestParam("text") String text) {

        List<Banner> list = this.bannerInterface.add(token, file, text);
        if (list.equals(null)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(200).body(list);
    }

    @PostMapping("banner/update")
    public ResponseEntity<List<Banner>> update(@RequestHeader("Authorization") String token,
                                          @RequestParam("file") MultipartFile file,
                                          @RequestParam("text") String text,
                                          @RequestParam("id") int id){


        List<Banner> list = this.bannerInterface.update(token,file,text,id);
        if (list.equals(null)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("banner/delete")
    public ResponseEntity<List<Banner>> delete(@RequestHeader("Authorization") String token,@RequestParam("id") int id){
        List<Banner> list = this.bannerInterface.delete(token,id);
        if (list.equals(null)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(200).body(list);
    }

}
