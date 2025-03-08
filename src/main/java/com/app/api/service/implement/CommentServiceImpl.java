package com.app.api.service.implement;

import com.app.api.model.Account;
import com.app.api.model.Comment;
import com.app.api.repository.ICommentRepository;
import com.app.api.service.interfaces.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private ICommentRepository commentRepository;
    @Override
    public boolean addComment(Comment commentModel) {
        if(this.commentRepository.save(commentModel).getId() > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<Comment> listCommentOfProduct(int id_product) {
        List<Comment> commentModel = this.commentRepository.findAll();
        List<Comment> result = new ArrayList<>();
        for(int i=0;i<commentModel.size();i++){
            if(commentModel.get(i).getProductModel().getId() == id_product){
                Comment getModel = new Comment();
                getModel.setComment(commentModel.get(i).getComment());
                getModel.setImage(commentModel.get(i).getImage());
                getModel.setStar(commentModel.get(i).getStar());
                getModel.setCreated_at(commentModel.get(i).getCreated_at());

                Account getAccount = new Account();
                getAccount.setUsername(commentModel.get(i).getAccountModel().getUsername());
                getAccount.setImage(commentModel.get(i).getAccountModel().getImage());

                getModel.setAccountModel(getAccount);
                result.add(getModel);
            }
        }
        return result;
    }
}
