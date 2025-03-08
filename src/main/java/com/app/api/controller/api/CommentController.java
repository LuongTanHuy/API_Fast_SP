package com.app.api.controller.api;

import com.app.api.model.Account;
import com.app.api.model.Comment;
import com.app.api.model.Product;
import com.app.api.service.implement.FileStorageServiceImpl;
import com.app.api.service.implement.TokenServiceImpl;
import com.app.api.service.interfaces.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

    @Autowired
    private ICommentService commentInterface;
    @Autowired
    private TokenServiceImpl tokenService;
    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @GetMapping("comment-product/{idProduct}")
    public List<Comment> listCommentOfProduct(@PathVariable("idProduct") int idProduct) {
        return this.commentInterface.listCommentOfProduct(idProduct);
    }

    @PostMapping("add-comment")
    public ResponseEntity<?> addComment(@RequestHeader("Authorization") String authorizationHeader,
                                        @RequestParam("idProduct") int idProduct,
                                        @RequestParam("comment") String comment,
                                        @RequestParam("star") int star,
                                        @RequestParam("file") MultipartFile file) {
        String token = authorizationHeader.replace("Bearer ", "");
        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));

        Account accountModel = new Account();
        accountModel.setId(idAccount);

        Product productModel = new Product();
        productModel.setId(idProduct);

        Comment newComment = new Comment();
        newComment.setAccountModel(accountModel);
        newComment.setProductModel(productModel);
        newComment.setComment(comment);
        newComment.setStar(star);
        newComment.setImage(this.fileStorageService.storeFile(file));

        return  this.commentInterface.addComment(newComment)?
                ResponseEntity.status(200).body("ok"):
                ResponseEntity.status(500).body("Error");
    }

}
