package com.app.api.controller.mvc;


import com.app.api.service.implement.TokenServiceImpl;
import com.app.api.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("webServerChatBotController")
@RequestMapping("/FastFood/")
public class BotChatWebController {

    @Autowired
    private IAccountService accountInterface;
    @Autowired
    private TokenServiceImpl tokenService;

    @GetMapping("trainBot")
    public String PageTrainAI(Model model, @CookieValue("FastFood") String token) {
//        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetAccountId(token));
//        model.addAttribute("account", this.accountInterface.accountProfile(idAccount,"Web"));
        model.addAttribute("title", "Bot AI");
        model.addAttribute("file_html", "/components/BotChatBody");
        model.addAttribute("component", "BotChatBody");
        return "pages/index";
    }


}
