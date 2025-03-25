package com.app.api.service.implement;

import com.app.api.dto.CommentDTO;
import com.app.api.model.Account;
import com.app.api.model.Comment;
import com.app.api.model.Product;
import com.app.api.repository.ICommentRepository;
import com.app.api.service.interfaces.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements ICommentService {

    private final ICommentRepository commentRepository;
    private final TokenServiceImpl tokenService;
    private final FileStorageServiceImpl fileStorageService;

    @Override
    public CommentDTO add(String token, Integer idProduct, String comment, Integer star, MultipartFile file) {

        Integer idAccount = this.tokenService.validateTokenAndGetId(token);

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

        Integer idComment =  this.commentRepository.save(newComment).getId();

        if(idComment > 0){
            return new CommentDTO(this.commentRepository.findById(idComment).get());
        }
        return null;
    }

    @Override
    public List<CommentDTO> listComment(Integer idProduct) {
        List<Comment> commentModel = this.commentRepository.findByProductModelId(idProduct);
        return commentModel.stream().map(comment -> new CommentDTO(comment)).collect(Collectors.toList());
    }
}
