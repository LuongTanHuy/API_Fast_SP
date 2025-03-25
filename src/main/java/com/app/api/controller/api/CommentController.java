package com.app.api.controller.api;

import com.app.api.dto.CommentDTO;
import com.app.api.service.interfaces.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v2/app/")
public class CommentController {

    @Autowired
    private ICommentService commentInterface;

    @GetMapping("comment/list")
    public ResponseEntity<List<CommentDTO>> listComment(@RequestParam("idProduct") Integer idProduct) {
        return ResponseEntity.ok(this.commentInterface.listComment(idProduct));
    }

    @PostMapping("comment/add")
    public ResponseEntity<?> add(@RequestHeader("Authorization") String token,
                                        @RequestParam("idProduct") Integer idProduct,
                                        @RequestParam("comment") String comment,
                                        @RequestParam("star") Integer star,
                                        @RequestParam("file") MultipartFile file) {
        CommentDTO commentDTO = this.commentInterface.add(token,idProduct,comment,star,file);

        return  commentDTO.equals(null)?
                ResponseEntity.status(401).build():
                ResponseEntity.status(200).body(commentDTO);
    }

}
