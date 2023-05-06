package org.launchcode.qleanquotes.controllers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.qleanquotes.models.Customer;
import org.launchcode.qleanquotes.models.data.CustomerRepository;
import org.launchcode.qleanquotes.models.dto.LoginFormDTO;
import org.launchcode.qleanquotes.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;



/* NOTE1: The @RestController annotation in Spring MVC is nothing but a combination of @Controller and @ResponseBody annotation.
 Response from a web application is generally view (HTML + CSS + JavaScript) */

/* NOTE2 :@CrossOrigin(origins = "http://localhost:4200") is a Java annotation that can be used in a Spring Boot application to allow
cross-origin requests from a specified origin.*/

//@RestController
//@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("customers")

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/register")
    public String displayRegisterForm(Model model){
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title", "Register");
        return "register";
    }


    @PostMapping("/register")
    public  String processRegisterForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO, Errors errors,
                                       HttpServletRequest request){
        if(errors.hasErrors()){
            return "/register";
        }
        Customer existingCustomer = customerRepository.findByEmail(registerFormDTO.getEmail());

        if(existingCustomer != null){
            errors.rejectValue("email", "email.alreadyexists", "A user with that email already exists");
            return "/login";
        }

        // Send customer back to form if passwords didn't match
        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if(!password.equals(verifyPassword)){
            errors.rejectValue("password" , "passwords.mismatch", "Passwords do not match");
            return "/register";
        }

        // OTHERWISE, save new email , hashed password and other info in database, and redirect to home page
        Customer newCustomer = new Customer(registerFormDTO.getName(), registerFormDTO.getLastName(),registerFormDTO.getEmail(),registerFormDTO.getPassword());
        customerRepository.save(newCustomer);
        return "redirect:/index";
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model){
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Log In");
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO, Errors errors,
                                   HttpServletRequest request){

        if(errors.hasErrors()){
            return "/login";
        }

        // Look up user in database using email they provided in the form
        Customer theCustomer = customerRepository.findByEmail(loginFormDTO.getEmail());


        if(theCustomer == null){
            errors.rejectValue("email", "customer.invalid", "The given email does not exist");
            return "/login";
        }

        String password = loginFormDTO.getPassword();

        if(!theCustomer.isMatchingPassword(password)){
            errors.rejectValue("password", "password.invalid", "Invalid password");
            return "/login";
        }

        return  "redirect:/index";
    }




}



