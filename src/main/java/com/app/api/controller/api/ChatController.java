package com.app.api.controller.api;


import com.app.api.model.Chat;
import com.app.api.service.implement.TokenServiceImpl;
import com.app.api.service.interfaces.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiChatController")
@RequestMapping("/api/v1/")
public class ChatController {
    private final IChatService chatInterface;
    private final TokenServiceImpl tokenService;

    @Autowired
    public ChatController(IChatService chatInterface, TokenServiceImpl tokenService) {
        this.chatInterface = chatInterface;
        this.tokenService = tokenService;
    }



//    @PostMapping("historyChat")
//    public List<HistoryChat> historyChat(@RequestBody HistoryChat historyChat){
//        if(historyChat.getTypeMessage().equals("App") == true){
//            String token  = historyChat.getSender().replace("Bearer ", "");
//            int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));
//            return this.chatInterface.historyChat(idAccount,historyChat.getIdStore());
//        }else{
//            return this.chatInterface.historyChat(historyChat.getIdAccount(),historyChat.getIdStore());
//        }
//    }

    @PostMapping("addChat")
    public ResponseEntity<?> addChat(@RequestBody Chat chatModel){
        if(this.chatInterface.addChat(chatModel)){
            return ResponseEntity.status(200).body("Add Success");
        }
        return ResponseEntity.status(500).body("Error");
    }

}
