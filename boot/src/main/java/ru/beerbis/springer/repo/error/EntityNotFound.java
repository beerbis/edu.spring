package ru.beerbis.springer.repo.error;

public class EntityNotFound extends RuntimeException {

    public EntityNotFound(Class<?> entityClass, Object id) {
        this(entityClass, id, null);
    }

    public EntityNotFound(Class<?> entityClass, Object id, String description) {
        super(String.format("Entity not found, %s: id=%s%s",
                entityClass.getSimpleName(),
                id,
                description != null ? "\r\nDescription: " + description : ""));
    }
}
