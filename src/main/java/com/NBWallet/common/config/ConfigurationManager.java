// ============================================================
// ConfigurationManager — фасад доступа к AppConfig (Owner Cache)
// ------------------------------------------------------------
// SOLID:
//  S (SRP): Единственная причина изменения — логика получения AppConfig.
//  D (DIP): Зависит от контракта AppConfig, а не от способа хранения конфигурации.
//  O (OCP): Слабая — смена механизма/источника потребует правок внутри.
//  L/I: не применимо (класс-утилита, не интерфейс/иерархия).
// ============================================================
package com.NBWallet.common.config;

import org.aeonbits.owner.ConfigCache;

public class ConfigurationManager {

    // Возвращает кэшированный инстанс AppConfig.
    // Здесь видно DIP: на выходе — интерфейс AppConfig, реализацию генерит Owner.
    public static AppConfig getAppConfig() {
        return ConfigCache.getOrCreate(AppConfig.class);
    }

    public static DbConnectionConfiguration getDbConfig() {
        return ConfigCache.getOrCreate(DbConnectionConfiguration.class);
    }
}
