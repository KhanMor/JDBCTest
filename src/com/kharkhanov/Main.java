package com.kharkhanov;

import dao.GroupDAO;
import dao.JournalDAO;
import dao.LectionDAO;
import dao.StudentDAO;

import java.sql.*;
import java.time.LocalDate;

public class Main {
    private static void students(Connection conn) throws SQLException {
        System.out.println("------------Students----------------");
        StudentDAO studentDAO = new StudentDAO(conn);
        studentDAO.insert("New Student", Date.valueOf(LocalDate.of(1989,1,1)),"m", 1);
        studentDAO.select("New Student");
        studentDAO.update("New Student", "New Student Changed", Date.valueOf(LocalDate.of(1989,1,1)),"m", 1);
        studentDAO.list();
        studentDAO.delete("New Student Changed");
        System.out.println("-------------------------------------");
    }
    private static void groups(Connection conn) throws SQLException {
        System.out.println("------------Groups----------------");
        GroupDAO groupDAO = new GroupDAO(conn);
        groupDAO.insert("New GROUP", 666);
        groupDAO.select("New GROUP");
        groupDAO.update("New GROUP", "New GROUP Changed", 999);
        groupDAO.list();
        groupDAO.delete("New GROUP Changed");
        System.out.println("-------------------------------------");
    }
    private static void lections(Connection conn) throws SQLException {
        System.out.println("------------lections----------------");
        LectionDAO lectionDAO = new LectionDAO(conn);
        lectionDAO.insert("New Lection", "New TEXT", "new subject");
        lectionDAO.select("New Lection");
        lectionDAO.update("New Lection", "New Lection Changed", "New TEXT", "new subject");
        lectionDAO.list();
        lectionDAO.delete("New Lection Changed");
        System.out.println("-------------------------------------");
    }
    private static void journals(Connection conn) throws SQLException {
        System.out.println("------------Journal----------------");
        JournalDAO journalDAO = new JournalDAO(conn);
        journalDAO.insert("Иванов И.И.", "JAVA", Date.valueOf(LocalDate.of(2017,2,16)));
        journalDAO.select("Иванов И.И.", "JAVA");
        journalDAO.update(
                "Иванов И.И.", "JAVA",
                "Петров П.П.", "JAVA",
                Date.valueOf(LocalDate.of(2017,2,14))
        );
        journalDAO.list();
        journalDAO.delete("Петров П.П.", "JAVA", Date.valueOf(LocalDate.of(2017,2,14)));
        System.out.println("-------------------------------------");
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/students";
            String login = "khan";
            String password = "khan_megakey";

            try(Connection conn =DriverManager.getConnection(url, login, password)) {
                students(conn);
                groups(conn);
                lections(conn);
                journals(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
