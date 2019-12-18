package wang.vanson.vspace.im.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.vanson.vspace.im.dto.JsonResult;
import wang.vanson.vspace.im.util.JWTUtils;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/accessToken")
    public JsonResult temporaryUserToken(String username) {
        String token = JWTUtils.createToken(username);
        return JsonResult.success(token);
    }
}
