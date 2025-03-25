package com.app.api.controller.mvc;

import com.app.api.service.interfaces.IAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v2/web/auth/")
public class AuthenticationWebController {

    @Autowired
    private IAuthentication authenticationService;

    @PostMapping("checkLogin")
    public ResponseEntity<?> checkLogin(@RequestParam("email") String email, @RequestParam("password") String password){
        if(authenticationService.login(email,password).equals(null)){
            return ResponseEntity.status(403).body("Invalid email or password");
        }else {
            return ResponseEntity.status(200).body(authenticationService.login(email,password));
        }
    }

}
