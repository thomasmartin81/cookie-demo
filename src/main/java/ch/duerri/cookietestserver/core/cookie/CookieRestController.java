package ch.duerri.cookietestserver.core.cookie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
@CrossOrigin
@RequestMapping("/")
public class CookieRestController {
    private static final String COOKIE_NAME = "MyCookie";

    @GetMapping("create")
    @ResponseBody
    public ResponseEntity<String> createCookie(HttpServletResponse response){
        addCookie(response);

        return ResponseEntity.ok("Cookie set");
    }

    private void addCookie( HttpServletResponse response){
        Cookie cookie = new Cookie(COOKIE_NAME,"AnyValue");

        // expires in 7 days
        cookie.setMaxAge(7 * 24 * 60 * 60);

        // browser should only add for https requests
        cookie.setSecure(true);

        // browser should add cookie only for request, but mustn't let script access it
        cookie.setHttpOnly(true);

        cookie.setDomain("group1.local.domain");
        response.addCookie(cookie);
    }


    @CrossOrigin(origins = {"https://test1.group2.local.domain:8080"}, allowCredentials = "true")
    @GetMapping("read")
    public ResponseEntity<String> readCookie(@CookieValue(name = COOKIE_NAME, required = false, defaultValue = "Cookie not found") String cookieValue){
        return ResponseEntity.ok("Cookie value is: " + cookieValue);
    }
}
