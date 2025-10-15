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
        try (
            Connection connection = getBaseDataSource(db).getConnection();
            Statement statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)
        ) {
            // здесь выполняем SQL-запросы
            ResultSet rs = statement.executeQuery("SELECT 1");
            while (rs.next()) {
                System.out.println("Result: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
}
