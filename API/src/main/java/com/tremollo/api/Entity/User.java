package com.tremollo.api.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "\"user\"")
public class User {

    @Column(name = "fname")
    String firstName;

    @Column(name = "lname")
    String lastName;

    @Column(name = "avatar")
    String avatarLink;

    @Column(name = "dob")
    Date dateOfBirth;

    @Column(name = "gender")
    Character gender;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    Date dateCreated;

    @Column(name = "about")
    String about;

    @Column(name = "type")
    Integer type;

    @Column(name = "username")
    String username;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer userId;

    @Column(name = "followers_count")
    Integer followersCount;

    public User(){}

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public User(String firstName, String lastName, String avatarLink, Date dateOfBirth, Character gender, String email, String password, Date dateCreated, String about, Integer type, String username, Integer userId, Integer followersCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarLink = avatarLink;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.dateCreated = dateCreated;
        this.about = about;
        this.type = type;
        this.username = username;
        this.userId = userId;
        this.followersCount = followersCount;
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

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avatarLink='" + avatarLink + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateCreated=" + dateCreated +
                ", about='" + about + '\'' +
                ", type=" + type +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                ", followersCount=" + followersCount +
                '}';
    }
}
