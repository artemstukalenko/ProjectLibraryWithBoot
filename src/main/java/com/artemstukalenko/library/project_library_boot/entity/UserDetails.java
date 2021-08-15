package com.artemstukalenko.library.project_library_boot.entity;

import com.artemstukalenko.library.project_library_boot.view.FirstView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import static com.artemstukalenko.library.project_library_boot.controller.RegexContainer.*;

@Component
@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @Column(name = "username")
    private String username;

    @Pattern(regexp = VALID_NAME, message = "First name is invalid")
    @NotBlank
    @Column(name = "first_name")
    private String userFirstName;

    @NotBlank
    @Pattern(regexp = VALID_SURNAME, message = "Surname is invalid")
    @Column(name = "last_name")
    private String userLastName;

    @NotBlank
    @Pattern(regexp = VALID_EMAIL, message = "Email is invalid")
    @Column(name = "email")
    private String userEmail;

    @NotBlank
    @Pattern(regexp = VALID_PHONE_NUMBER, message = "Phone number is invalid")
    @Column(name = "phone_number")
    private String userPhoneNumber;

    @NotBlank
    @Column(name = "address")
    private String userAddress;

    @Column(name = "penalty")
    private int userPenalty;
    @Column(name = "authority_string")
    private String authorityString;

    private transient boolean hasPenalty;

    public UserDetails() {}

    public UserDetails(User user) {
        this.username = user.getUsername();
        this.userPenalty = 0;
    }

    public UserDetails(String userFirstName, String userLastName, String userEmail, String userPhoneNumber, String userAddress) {
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.userPenalty = 0;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getUserPenalty() {
        return userPenalty;
    }

    public void setPenalty(int penalty) {
        this.userPenalty += penalty;
    }

    public void setAuthorityString(String authorityStringFromDB) {
        switch (authorityStringFromDB) {
            case "ROLE_ADMIN":
                this.authorityString = "ADMIN";
                break;
            case "ROLE_LIBRARIAN":
                this.authorityString = "LIBRARIAN";
                break;
            default:
                this.authorityString = "";
                break;
        }
    }

    public String getAuthorityString() {
        return authorityString;
    }

    public boolean isHasPenalty() {
        return this.userPenalty > 0;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "username='" + username + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userPenalty=" + userPenalty +
                '}';
    }
}
