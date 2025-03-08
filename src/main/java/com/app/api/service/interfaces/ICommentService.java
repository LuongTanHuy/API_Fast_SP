package com.app.api.service.interfaces;

import com.app.api.model.Comment;

import java.util.List;

public interface ICommentService {
    public boolean addComment(Comment commentModel);
    public List<Comment> listCommentOfProduct(int id_product);
}
