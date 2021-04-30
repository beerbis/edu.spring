package ru.beerbis.springer.dao;

import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Optional;

interface BaseDao<IdT, EntityT> {
    Collection<EntityT> all();
    Optional<EntityT> find(@NonNull IdT id);
    boolean replace(EntityT product);
    void save(EntityT product);
    boolean remove(IdT id);
}
