package demo.security;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuntze on 23/04/15.
 */
@Controller
@RequestMapping("/verifycode")
public class VerificationCodeController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(VerificationCodeController.class);

    @RequestMapping(method = RequestMethod.POST)
    public String verifyCode(String code) {
        String view = "redirect:/code?error";
        try {
            if (TimeBasedOneTimePassword.isVerificationCodeValid(Integer.parseInt(code))) {
                grantAuthority();
                view = "redirect:/home";
            }
        } catch (NumberFormatException e) {
            // ignore
        } catch (InvalidCodeException e) {
            LOGGER.error("error while verifying code", e.getMessage());
        }
        return view;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showVerificationCodeForm() {
        return "code";
    }

    private void grantAuthority() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = new ArrayList<>(auth.getAuthorities());
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
