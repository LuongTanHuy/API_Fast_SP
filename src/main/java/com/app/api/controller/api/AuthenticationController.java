package com.app.api.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/v2/app/auth/")

public class AuthenticationController {
//    private final AuthenticationService authenticationService;
//    private final EmailServiceImpl emailService;
//    private final TokenServiceImpl tokenService;
//    private final AccountServiceImpl accountService;
//
//    @Autowired
//    public AuthenticationController(AuthenticationService authenticationService,
//                                    EmailServiceImpl emailService, TokenServiceImpl tokenService,
//                                    AccountServiceImpl accountService) {
//        this.authenticationService = authenticationService;
//        this.emailService = emailService;
//        this.tokenService = tokenService;
//        this.accountService = accountService;
//    }
//
//    @PostMapping("login")
//    public ResponseEntity<?> login(@RequestBody Account accountModel) {
//        Account _accountModel = this.authenticationService.login(accountModel.getEmail(), accountModel.getPassword());
//
//        if (_accountModel != null) {
//            if (_accountModel.getPermission() == true) {
//                String token = this.tokenService.generateToken(_accountModel.getId());
//                return ResponseEntity.status(200).body(token);
//            } else {
//                return ResponseEntity.status(403).body("Account is inactive or locked");
//            }
//        } else {
//            return ResponseEntity.status(404).body("Invalid email or password");
//        }
//    }


//    @PostMapping("signUp")
//    public ResponseEntity<String> signUp(@RequestBody Account accountModel) {
//        Account _accountModel = this.authenticationService.signUp(accountModel.getEmail(), accountModel.getPassword());
//        return  (_accountModel.getId() > 0) ?
//                ResponseEntity.status(200).body(this.tokenService.generateToken(_accountModel.getId())) :
//                (ResponseEntity<String>) ResponseEntity.status(500);
//    }

//    @PostMapping("sendOtp")
//    public ResponseEntity<String> sendOtp(@RequestBody Account accountModel,
//                                          @RequestHeader("Authorization") String authorizationHeader) {
//        String idAccount = this.tokenService.validateTokenAndGetId(authorizationHeader.replace("Bearer ", ""));
//        emailService.sendOtpEmail(accountModel.getEmail(), idAccount);
//        return ResponseEntity.status(200).body("OTP sent successfully");
//    }
//
//    @PostMapping("checkOtp")
//    public ResponseEntity<String> checkOtp(@RequestBody Account accountModel,
//                                           @RequestHeader("Authorization") String authorizationHeader) {
//        String token = authorizationHeader.replace("Bearer ", "");
//        int idAccount = Integer.parseInt(this.tokenService.validateTokenAndGetId(token));
//        if (token == null) {
//            return  String.valueOf(idAccount) == null ?
//                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token") :
//                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not provided");
//        }
//
//        return  this.accountService.checkOtp(idAccount, accountModel.getOtp()) ?
//                ResponseEntity.status(200).body("OTP Correct") :
//                ResponseEntity.status(500).body("OTP Incorrect");
//    }
//
//    @PostMapping("checkEmail")
//    public ResponseEntity<String> checkEmail(@RequestBody Account accountModel) {
//        return this.accountService.checkEmail(accountModel.getEmail()) ?
//                ResponseEntity.status(200).body("Success") :
//                ResponseEntity.status(404).body("Not Found");
//    }

}
