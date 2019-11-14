package com.nemo.autumn.mvc.form;

import com.nemo.autumn.validation.annotation.EnglishLanguage;
import com.nemo.autumn.validation.annotation.FieldsValueMatch;
import com.nemo.autumn.validation.annotation.MinDigitCount;
import com.nemo.autumn.validation.annotation.MinSpecCharCount;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@FieldsValueMatch(field = "password", fieldMatch = "passwordAgain", message = "Passwords do not match!")
public class SignupForm {

    @NotBlank
    @Size(min = 3, max = 40, message = "Login size should be at least 3 characters")
    @EnglishLanguage
    private String login;

    @NotBlank
    @Email(message = "Invalid email")
    private String email;

    @Size(min = 8, message = "Password size should be at least 8 characters")
    @MinDigitCount(value = 2, message = "Password should contain at least 2 digits")
    @MinSpecCharCount(value = 2, message = "Password should contain at least 2 special characters")
    private String password;
    private String passwordAgain;

    @NotBlank
    @Size(max = 100)
    @EnglishLanguage
    private String firstName;

    @NotBlank
    @Size(max = 100)
    @EnglishLanguage
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SignupForm that = (SignupForm) o;
        return Objects.equals(login, that.login) && Objects.equals(email,
                that.email) && Objects.equals(password, that.password)
                && Objects.equals(passwordAgain, that.passwordAgain)
                && Objects.equals(firstName, that.firstName) && Objects.equals(
                lastName, that.lastName) && Objects.equals(birthday,
                that.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, email, password, passwordAgain, firstName,
                lastName, birthday);
    }
}
