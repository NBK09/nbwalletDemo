package com.NBWallet.layers.db.repositories;

import java.io.Serializable;
import java.util.List;

public interface RepositoryBase<TEntity, ID extends Serializable> {
    void create(TEntity entity);

    TEntity get(ID id);

    List<TEntity> getAll();

    void update(TEntity entity);

    void delete(TEntity entity);

}
