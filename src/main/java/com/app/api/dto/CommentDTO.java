package com.app.api.dto;

import com.app.api.model.Comment;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDTO {

    private String comment;
    private Timestamp createdAt;
    private String image;
    private Integer star;
    private AccountDTO accountDTO;

    public CommentDTO(Comment comment) {
        this.comment = comment.getComment();
        this.createdAt = comment.getCreated_at();
        this.image = comment.getImage();
        this.star = comment.getStar();
        this.accountDTO = new AccountDTO(comment.getAccountModel().getUsername(), comment.getAccountModel().getEmail(), comment.getAccountModel().getImage());
    }
}
