// ============================================================
// AppConfig — контракт конфигурации (Owner)
// ------------------------------------------------------------
// SOLID:
//  S (SRP): Отвечает только за контракт конфигов (одна зона ответственности).
//  I (ISP): Узкий интерфейс — только необходимые методы, нет «толстых» интерфейсов.
//  D (DIP): Потребители зависят от абстракции (интерфейс AppConfig), а не от конкретной реализации.
//  O (OCP): Ограниченно — новый конфиг = нужно добавлять метод (модификация).
//  L (LSP): Неприменимо — это интерфейс, который не участвует в нашей иерархии подстановки.
// ============================================================
package com.NBWallet.common.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:app.properties"}) // Источник значений — файл app.properties в classpath
public interface AppConfig extends Config {

    // Ключи и методы — строгий контракт, на который опирается остальной код.
    // Здесь явно видно DIP: все потребители читают значения через этот интерфейс, не зная как он реализован.

    @Key("base.url")
    String baseUrl();  // базовый URL приложения для API

    @Key("headless.mode")
    boolean headless(); // режим headless для браузера

    @Key("bearer.token")
    String bearer_token(); // токен

    @Key("docker.remote")
    boolean remote();

    @Key("remote.url.docker")
    String dockerUrl();

    @Key("server")
    String server();

    @Key("port")
    Integer port();

    @Key("user")
    String user();

    @Key("sqlpassword")
    String sqlpassword();
}
