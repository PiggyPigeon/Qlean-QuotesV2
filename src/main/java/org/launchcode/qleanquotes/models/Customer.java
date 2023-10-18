package org.launchcode.qleanquotes.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;


@Entity
public class Customer extends AbstractEntity implements UserDetails {
    @OneToMany
    @JoinColumn(name = "id")
    private List<Orders> orders = new ArrayList<>();

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String phoneNumber;

    private String street;

    private String zip;

    private String city;

//    adding Profile Image
//    Lob indicates that the images should be stored as a large image in the database
//    LAZY means this data us not loaded everytime customer data is retrieved
    @Lob @Basic(fetch=LAZY)
    @Column(columnDefinition="BLOB")
    protected byte[] profileImage;

    //below BCrypt class is provided by the spring-security-crypto dependency. It hashes the passwords for us.
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Customer() {
    }

    public Customer(String name, String lastName, String email, String password, String phoneNumber, String street, String city, String zip, byte[] profileImage) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = encoder.encode(password);
        this.phoneNumber = "";
        this.street = "";
        this.city = "";
        this.zip = "";
        this.profileImage = profileImage;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        String capitalizedStreet = capitalizeEachWord(street);
        this.street = capitalizedStreet;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        String capitalizedCity = capitalizeEachWord(city);
        this.city = capitalizedCity;
    }


    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    private String capitalizeEachWord(String text) {
        String[] words = text.split("\\s");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                char firstChar = Character.toUpperCase(word.charAt(0));
                String capitalizedWord = firstChar + word.substring(1);
                result.append(capitalizedWord).append(" ");
            }
        }
        return result.toString().trim();
    }



    //below nonsense is required by the UserDetails implementation or for security, dont touch, plz.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    //the transient annotation means the authorities field will not be persisted in the database
    //initializes authorities to an empty set
    @Transient
    Collection<? extends GrantedAuthority> authorities = Collections.emptySet();

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
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

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
