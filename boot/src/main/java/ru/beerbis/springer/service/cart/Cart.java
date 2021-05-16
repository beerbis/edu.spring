package ru.beerbis.springer.service.cart;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.beerbis.springer.repo.error.ForbiddenInputValue;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Бин корзинки, сессионный.
 * Хранит ID товаров и количество.
 * Количество поддерживается консистентным.
 * Товары не проверяются на валидность. Надо будет проверить - проверится при оформлении, или при заливке в модель.
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart {

    /**
     * Product.id -> count
     *
     * Сами Product хранить не целесообразно:
     * 1. Для view их всё равно надо перечитывать, на случай изменений за время сессии
     * 2. Для оформления покупки - надо перечитывать тем более, зафиксировать актуальный на момент покупки ценник
     * 3. Для хранение все эти миллиорны клонов совершенно не нужны - экономим память
     * 4. Чтобы сунуть в Map - нужен hashcode/equals, по id, что не факт что соответствует идее сущности Product.
     *    Или делать обёртку над Product для использования его как ключ в мапе - тоже дичь какая-то.
     */
    private final ConcurrentMap<Integer, Integer> items = new ConcurrentHashMap<>();

    /**
     * Задать конкретное количество товара в корзине
     *
     * @param productId ID номенклатурной позиции
     * @param count количество товара
     * @throws ru.beerbis.springer.repo.error.ForbiddenInputValue for negative or zero count
     * @throws NullPointerException for count=null
     */
    public void set(Integer productId, Integer count) {
        checkCountParam(count);
        items.compute(productId, (id, oldCount) -> count);
    }

    public Integer put(Integer productId) {
        return put(productId, 1);
    }

    /**
     * Добавить новый товар, или увеличить количество
     *
     * @param productId ID номенклатурной позиции
     * @param count значение инкремента количества/количество
     * @return новое установившееся значение количества
     * @throws ru.beerbis.springer.repo.error.ForbiddenInputValue for negative or zero count
     * @throws NullPointerException for count=null
     */
    public Integer put(Integer productId, Integer count) {
        checkCountParam(count);
        return items.compute(productId, (id, oldCount) ->
                oldCount == null
                ? count
                : oldCount + count);
    }

    /**
     * Уменьшить количество товара оптимистично, либо удалить позицию, если выпали из натурального ряда
     * <p>Примечания: как-то кривовато реализация выглядит, учитывая что наиболее вероятно это будет удаление,
     *    а ещё можно DDos-ит сборщик мусора удалениями несуществующего элемента
     *
     * @param productId ID номенклатурной позиции
     * @param count значение декремента количества
     * @return новое установившееся значение количества
     * @throws ru.beerbis.springer.repo.error.ForbiddenInputValue for negative or zero count
     * @throws NullPointerException for count=null
     */
    public Integer remove(Integer productId, Integer count) {
        checkCountParam(count);
        var newCount = items.compute(productId, (id, oldCount) ->
                oldCount == null || oldCount <= count
                        ? null
                        : oldCount - count);

        if (newCount != null) return newCount;

        //ветка неудачи оптимизма - снести элемент, если его ещё не модифицировали.
        //можно ветку неудач унести в демон очистки корзинок и не париться.
        items.values().removeIf(Objects::isNull);
        return 0;
    }

    /**
     * Удалить позицию из корзины
     *
     * @param productId ID удаляемой номенклатурной позиции
     * @return удаление состоялось, или не было что удалять
     */
    public boolean remove(Integer productId) {
        return items.keySet().removeIf(productId::equals);
    }

    /**
     * Проверить что параметр количества имеет смысл
     *
     * @param count проверяемое значение
     * @throws ru.beerbis.springer.repo.error.ForbiddenInputValue for negative or zero count
     * @throws NullPointerException for count=null
     */
    private static void checkCountParam(Integer count) {
        requireNonNull(count, "count");
        if (count <= 0) throw new ForbiddenInputValue(Cart.class, "count", count, "count should be greater then zero");
    }

    /**
     * Получить содержимое корзинки списком
     *
     * @return содержимое корзинки
     */
    public List<CartItem> listAll() {
        return items.entrySet().stream().map(it -> new CartItem(it.getKey(), it.getValue())).collect(Collectors.toList());
    }

    /**
     * Получить описание отдельно взятой позиции из корзинки, по ID номенклатурной позиции
     *
     * @param productId ID поменклатурной позиции
     * @return единичный элемент "корзинки"
     */
    public Optional<CartItem> get(Integer productId) {
        var itemDesc = items.get(productId);
        if (itemDesc == null) return Optional.empty();

        return Optional.of(new CartItem(productId, itemDesc));
    }
}
