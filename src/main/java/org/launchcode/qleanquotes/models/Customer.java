package org.launchcode.qleanquotes.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.Collections;

//TODO there needs to be persistence annotations in this model for the databases tables to relate to each other (foreign key! think @manytoone, @onetomany, blah blah)
@Entity
public class Customer extends AbstractEntity implements UserDetails {

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    //Pigeon: security will handle the pwhash, plz dont touch this.
    @NotNull
    private String pwHash;

    //below BCrypt class is provided by the spring-security-crypto dependency. It hashes the passwords for us.
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Customer() {
    }

    //below is constructor at registration
    public Customer(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.pwHash = encoder.encode(password);
    }


    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }



    //below nonsense is required by the UserDetails implementation or for security, dont touch, plz.

    //this will be a constructor for security
    public Customer(Customer customer) {
        this.name = customer.name;
        this.lastName = customer.lastName;
        this.email = customer.email;
        this.pwHash = customer.pwHash;
        this.authorities = customer.authorities;
    }
    @Transient
    Collection<? extends GrantedAuthority> authorities = Collections.emptySet();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return pwHash;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String encodedPassword) {
    }



}
