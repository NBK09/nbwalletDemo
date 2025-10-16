package com.NBWallet.common.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:nwwallet.connection.properties"})
public interface DbConnectionConfiguration extends Config {

    @Key("datasource.url")
    String jdbcUrl();

    @Key("datasource.username")
    String username();

    @Key("datasource.password")
    String password();

    @Key("datasource.driver")
    String driverClassName();

    @Key("datasource.configuration")
    String configurationFile();
}
