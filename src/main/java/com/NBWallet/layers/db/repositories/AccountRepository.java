package com.NBWallet.layers.db.repositories;

import com.NBWallet.layers.db.entities.Account;

public class AccountRepository extends RepositoryBaseImpl<Account, Integer> {
    public AccountRepository() {
        super(Account.class);
    }
}
