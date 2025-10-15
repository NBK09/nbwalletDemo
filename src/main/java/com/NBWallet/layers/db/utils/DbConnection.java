package com.NBWallet.layers.db.utils;


import com.NBWallet.common.config.ConfigurationManager;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;

public class DbConnection {
    private static Connection connection; // для подключения
    private static Statement statement; // нужен для не параметризованных запросов

    private static PGSimpleDataSource getBaseDataSource(String db) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource() {{
            setServerName((ConfigurationManager.getAppConfig().server()));
            setPortNumber(ConfigurationManager.getAppConfig().port());
            setUser(ConfigurationManager.getAppConfig().user());
            setPassword(ConfigurationManager.getAppConfig().sqlpassword());
            setDatabaseName(db);
        }};
        return dataSource;
    }
    public static void openConnection(String db) {
        try {
            connection = getBaseDataSource(db).getConnection();
            statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );

            // Проверим, что соединение реально установлено
            ResultSet rs = statement.executeQuery("SELECT 1");
            if (rs.next()) {
                System.out.println("✅ Connected to DB, test query result: " + rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("❌ Ошибка при подключении к БД: " + e.getMessage(), e);
        }
    }

    public static ResultSet query(String query, Object... params) throws SQLException {
        if (params.length == 0){
            return statement.executeQuery(query);
        }else {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement.executeQuery();
        }
    }

    public static int executeUpdate(String query, Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeUpdate();
    }

    public static void closeConnection() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
            System.out.println("🔒 Connection closed.");
        } catch (SQLException e) {
            System.err.println("⚠️ Ошибка при закрытии соединения: " + e.getMessage());
        }
    }
}
