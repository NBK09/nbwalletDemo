package com.NBWallet.layers.api.request.stratgy;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class AuthStrategyFactory {
    private static final Map<String, Supplier<AuthStrategy>> strategies = Map.of(
            "customer", CustomerAuthStrategy::new,
            "manager", ManagerAuthStrategy::new
    );

    public static AuthStrategy getStrategy(String role) {
        return Optional.ofNullable(strategies.get(role.toLowerCase()))
                .orElseThrow(() -> new IllegalArgumentException("Unknown role: " + role))
                .get();
    }
}
