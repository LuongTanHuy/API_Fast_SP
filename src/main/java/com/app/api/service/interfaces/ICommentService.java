package com.app.api.service.interfaces;

import com.app.api.dto.CommentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICommentService {
    public CommentDTO add(String token, Integer idProduct, String comment, Integer star, MultipartFile file);
    public List<CommentDTO> listComment(Integer idProduct);
}
