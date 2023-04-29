package com.skillbox.jdbc;

import java.sql.*;

public class Main {
    static final String URL = "jdbc:mysql://localhost:3306/main.java.skill_box";
    static final String USERNAME = "Ksenta";
    static final String PASSWORD = "270791Gznrf";

    private static void registerDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT course_name, CAST(AVG(cnt) AS DECIMAL(4,1)) AS avg_purchases_per_month " +
                    "FROM ( SELECT course_name, MONTH(subscription_date) AS month, COUNT(*) AS cnt " +
                    "  FROM PurchaseList " +
                    "  WHERE YEAR(subscription_date) = 2018 " +
                    "  GROUP BY course_name, month " +
                    ") AS t " +
                    "GROUP BY course_name";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                double avgPurchasesPerMonth = resultSet.getDouble("avg_purchases_per_month");
                System.out.println("Название курса - " + courseName + ": " + "среднее количество покупок - " + avgPurchasesPerMonth);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
