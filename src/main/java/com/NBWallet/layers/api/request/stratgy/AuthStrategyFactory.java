package com.NBWallet.layers.api.request.stratgy;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Data
public class AuthStrategyFactory {

    // Используем Function<String[], AuthStrategy>, чтобы можно было передать параметры (login, password)
    private static final Map<String, Function<String[], AuthStrategy>> strategies = new HashMap<>();

    static {
        // Customer — требует логин и пароль
        strategies.put("customer", (params) -> {
            if (params == null || params.length < 2) {
                throw new IllegalArgumentException("Для роли 'customer' нужно указать login и password!");
            }
            String login = params[0];
            String password = params[1];
            return new CustomerAuthStrategy(login, password);
        });

        // Manager — без параметров
        strategies.put("manager", (params) -> new ManagerAuthStrategy());
    }

    /**
     * Получает стратегию авторизации по роли.
     * Для "customer" нужно передать login и password.
     */
    public static AuthStrategy getStrategy(String role, String... params) {
        return Optional.ofNullable(strategies.get(role.toLowerCase()))
                .orElseThrow(() -> new IllegalArgumentException("Unknown role: " + role))
                .apply(params);
    }
}
