package com.nemo.autumn.mvc.controller;

import com.nemo.autumn.exception.UserAlreadyExistsException;
import com.nemo.autumn.security.CurrentUser;
import com.nemo.autumn.security.SecurityUtil;
import com.nemo.autumn.service.UserService;
import com.nemo.autumn.mvc.CaptchaService;
import com.nemo.autumn.mvc.form.SignupForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class CommonController {

    private final UserService userService;

    private final CaptchaService captchaService;

    @Autowired
    public CommonController(UserService userService,
            CaptchaService captchaService) {
        this.userService = userService;
        this.captchaService = captchaService;
    }

    @RequestMapping(value = { "", "/", "/index*" }, method = RequestMethod.GET)
    public String index() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        CurrentUser currentUser = SecurityUtil.getCurrentUser();
        if (currentUser != null) {
            return "redirect:/" + SecurityUtil.getRoleHomePage(
                    currentUser.getRole());
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "/login-failed")
    public String loginFailed(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String getError() {
        return "error";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String getSignup(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        model.addAttribute("captchaSiteKey", captchaService.getSite());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String registerNewAccount(
            @Valid @ModelAttribute("signupForm") SignupForm signupForm,
            BindingResult bindingResult, Model model,
            HttpServletRequest request) {
        String response = request.getParameter("g-recaptcha-response");
        if (!captchaService.isCaptchaValid(response)) { // checking captcha
            model.addAttribute("existsError",
                    "There was a problem with captcha!");
            return "signup";
        }
        if (bindingResult.hasErrors()) { // validating form
            model.addAttribute("signupForm", signupForm);
            return "signup";
        } else { // persisting
            try {
                userService.createUser(signupForm);
                return "signup-success";
            } catch (UserAlreadyExistsException e) { // duplicate found in DB
                model.addAttribute("existsError", e.getMessage());
                return "signup";
            }
        }
    }

}