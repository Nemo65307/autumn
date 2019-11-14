package com.nemo.autumn.api.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nemo.autumn.domain.Role;
import com.nemo.autumn.validation.annotation.EnglishLanguage;
import com.nemo.autumn.validation.annotation.MinDigitCount;
import com.nemo.autumn.validation.annotation.MinSpecCharCount;
import com.nemo.autumn.api.common.DateAdapter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.Objects;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDto {

    private String id;

    @NotBlank
    @Size(min = 3, max = 40,
            message = "Login size should be at least 3 characters")
    @EnglishLanguage(withPunctuations = false)
    private String login;

    @NotBlank
    @Email(message = "Invalid email")
    private String email;

    @Size(min = 8, message = "Password size should be at least 8 characters")
    @MinDigitCount(value = 2,
            message = "Password should contain at least 2 digits")
    @MinSpecCharCount(value = 2,
            message = "Password should contain at least 2 special characters")
    private String password;

    @NotBlank
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false,
            withNumbers = false)
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @EnglishLanguage(withSpecSymbols = false, withPunctuations = false,
            withNumbers = false)
    @Size(max = 100)
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @XmlElement(name = "birthday", required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date birthday;

    @NotNull
    private Role role;

    public UserDto() {
    }

    public UserDto(String id, String login, String email, String password,
            String firstName, String lastName, Date birthday, Role role) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(login,
                userDto.login) && Objects.equals(email, userDto.email)
                && Objects.equals(password, userDto.password) && Objects.equals(
                firstName, userDto.firstName) && Objects.equals(lastName,
                userDto.lastName) && Objects.equals(birthday, userDto.birthday)
                && Objects.equals(role, userDto.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, email, password, firstName, lastName,
                birthday, role);
    }

}
