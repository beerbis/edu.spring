package ru.beerbis.springer.dao;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import ru.beerbis.springer.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

abstract class BaseDaoImpl<IdT extends Serializable, EntityT extends BaseEntity<IdT>> implements BaseDao<IdT, EntityT> {

    private final Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);
    private final SessionFactory factory;
    private final Class<EntityT> clazz;
    private final String queryNameAll;
    private final String queryNameDel;

    public BaseDaoImpl(SessionFactory factory, Class<EntityT> clazz) {
        this.factory = factory;
        this.clazz = clazz;
        queryNameAll = clazz.getSimpleName() + ".all";
        queryNameDel = clazz.getSimpleName() + ".del";
    }

    @Override
    public Collection<EntityT> all() {
        try (var session = factory.getCurrentSession()) {
            session.beginTransaction();
            return session.createNamedQuery(queryNameAll, clazz).getResultList();
        }
    }

    @Override
    public Optional<EntityT> find(@NonNull IdT id) {
        requireNonNull(id, "id");
        try (var session = factory.getCurrentSession()) {
            session.beginTransaction();
            return Optional.ofNullable(session.find(clazz, id));
        }
    }

    @Override
    public boolean replace(EntityT entity) {
        try (var session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            log.info("{} updated: {}", clazz.getSimpleName(), entity);
            return true;
        }
    }

    @Override
    public void save(EntityT entity) {
        try (var session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            log.info("{} stored: {}", clazz.getSimpleName(), entity);
        }
    }

    @Override
    public boolean remove(@NonNull IdT id) {
        requireNonNull(id, "id");
        try (var session = factory.getCurrentSession()) {
            session.beginTransaction();
            var updateCount = session.createNamedQuery(queryNameDel)
                    .setParameter("id", id)
                    .executeUpdate();

            if (updateCount == 0) {
                log.info("{} remove, not found: {}", clazz.getSimpleName(), id);
                return false;
            }

            session.getTransaction().commit();
            log.info("{} removed: {}", clazz.getSimpleName(), id);
            return true;
        }
    }
}
