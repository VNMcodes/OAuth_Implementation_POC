package com.vnmcodes.oauth_oicd.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class OAuthController {

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
        model.addAttribute("name", authentication != null ? authentication.getName() : null);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        // Renders a page with provider buttons/links
        return "login";
    }

    @GetMapping("/user")
    public String user(Authentication authentication, Model model) {
        if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
            OAuth2User oAuth2User = oauth2Token.getPrincipal();
            model.addAttribute("attributes", oAuth2User.getAttributes());
            model.addAttribute("registrationId", oauth2Token.getAuthorizedClientRegistrationId());
            model.addAttribute("name", authentication.getName());
        }
        return "user";
    }

    @GetMapping("/api/me")
    @ResponseBody
    public Map<String, Object> me(Principal principal, Authentication authentication) {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("principalName", principal != null ? principal.getName() : null);
        if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
            info.put("registrationId", oauth2Token.getAuthorizedClientRegistrationId());
            info.put("attributes", ((OAuth2User) oauth2Token.getPrincipal()).getAttributes());
        }
        return info;
    }

}
