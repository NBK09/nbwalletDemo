package com.NBWallet.layers.web.page;


import com.NBWallet.layers.web.manager.ElementManager;

public abstract class BasePage<T extends BasePage> {
    public abstract T waitForPageLoaded();
    protected final ElementManager elementManager = new ElementManager();

}