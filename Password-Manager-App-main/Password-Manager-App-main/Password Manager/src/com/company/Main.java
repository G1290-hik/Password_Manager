package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private void menu() {
        Scanner in = new Scanner(System.in);
        System.out.println("-----------------------------------------");
        System.out.print("Enter 1 for SignUp or Enter 2 for Login: ");
        int choice = in.nextInt();

        switch (choice) {
            case 1:
                signUp();
                break;
            case 2:
                login();
                break;
            default:
                menu();
        }
    }

    private void login() {
        Scanner cin = new Scanner(System.in);
        System.out.println("-----------------------------------------");
        System.out.print("Enter username: ");
        String username = cin.nextLine();
        System.out.print("Enter Password: ");
        String password = cin.nextLine();

        String url = "jdbc:mysql://127.0.0.1:3306/users";
        String uname = "root";
        String pass = "root";
        String query = "SELECT * from sinUp where username=? and passw=?";

        try (Connection con = DriverManager.getConnection(url, uname, pass);
                PreparedStatement st = con.prepareStatement(query)) {

            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                System.out.println("Login Successful");
            } else {
                System.out.println("Username or password is incorrect");
            }
        } catch (SQLException e) {
            System.out.println("Database not connected: " + e.getMessage());
        }
    }

    private void signUp() {
        Scanner cin = new Scanner(System.in);
        System.out.println("-----------------------------------------");
        System.out.print("Enter username: ");
        String username = cin.nextLine();
        while (username.length() < 5) {
            System.out.println("Username must contain at least 5 characters");
            System.out.print("Enter username: ");
            username = cin.nextLine();
        }
        System.out.print("Enter Password: ");
        String password = cin.nextLine();
        while (password.length() < 8) {
            System.out.println("Password must contain at least 8 characters");
            System.out.print("Enter password: ");
            password = cin.nextLine();
        }

        String url = "jdbc:mysql://127.0.0.1:3306/users";
        String uname = "root";
        String pass = "Ragul@16";
        String query = "INSERT INTO sinUp (username, passw) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(url, uname, pass);
                PreparedStatement st = con.prepareStatement(query)) {

            st.setString(1, username);
            st.setString(2, password);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully");
                login();
            } else {
                System.out.println("Failed to register user");
            }
        } catch (SQLException e) {
            System.out.println("Database not connected: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.menu();
    }
}
