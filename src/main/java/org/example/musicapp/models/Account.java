package org.example.musicapp.models;
// Enum to define user roles
enum Role {
    USER, ARTIST, ADMIN
}

// Base Account class
abstract class Account {
    private String name;
    private int age;
    private String email;
    private String username;
    private String password;
    private Role role;

    public Account(String name, int age, String email, String username, String password, Role role) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public abstract void viewProfile();  // Each subclass will implement its own profile view
}
