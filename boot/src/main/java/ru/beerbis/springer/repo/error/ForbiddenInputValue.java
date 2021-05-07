package ru.beerbis.springer.repo.error;

public class ForbiddenInputValue extends RuntimeException {
    public ForbiddenInputValue(Class<?> entityClass, String valueId, Object value) {
        this(entityClass, valueId, value, null);
    }

    public ForbiddenInputValue(Class<?> entityClass, String valueId, Object value, String description) {
        super(String.format("Value not supported for operation, entity %s: %s=%s%s",
                entityClass.getSimpleName(), valueId, value,
                description != null ? "\r\nDescription: " + description : ""));
    }
}
