package com.jgmp.secret.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jgmp.secret.service.SecretService;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private SecretService service;

    @GetMapping
    public String sayHello(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            model.addAttribute("username", username);
        }
        return "index.html";
    }

    @PostMapping("/create-link")
    public String createLink(@RequestParam("textbox") String secret, Model model) throws Exception {
        var key = "http://localhost:8080/get-secret/" + service.saveToDbAndReturnKey(secret);
        model.addAttribute("key", key);
        return "index.html";
    }

    @GetMapping("/get-secret/{key}")
    public String getSecret(@PathVariable("key") String key, Model model) throws Exception {
        try {
            String secret = service.getInfoByKey(key);
            model.addAttribute("secret", secret);
        } catch (IllegalStateException e) {
            model.addAttribute("error", "Secret not found or has been deleted!");
        }
        return "index.html";
    }
}
