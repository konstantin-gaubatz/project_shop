package app.repository;


import app.domain.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
Этот класс находится на втором слое нашего приложения - репозитории.
Задача репозитория - осуществлять доступ к данным, которые хранятся в базе данных.
В качестве имитации базы данных у нас здесь будет выступать лист.

Репозиторий, как правило, реализует CRUD функционал
CRUD - create read update delete.
 */
public class ProductRepository {

    // Создаем лист продуктов из класса продукты. Он будет имитацией нашей базы данных.

    private final List<Product> database = new ArrayList();

    // Поле которое учитывает какой сейчас максимальный идентификатор продукта в базе данных.
    // Так мы не указали ее значение она будет сразу нулем. Когда мы добавим первый продукт она изменится на 1.
    private long maxId;

    // Метод, который сохраняет новый продукт в базе (Create)
    public Product save(Product product) {
        // Префиксный инкремент - сначала увеличивает, а потом присваивает.
        product.setId(++maxId);
        database.add(product);
        return product;
    }

    // Методы чтения read, всю базу и по одному.

    // Метод, который возвращает все продукты из базы данных.
    public List<Product> findAll() {
        return database;
    }

    // Метод, который возвращает один продукт по идентификатору
    public Product findById(Long id) {
        for (Product product : database) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    // Метод изменения цены продукта в базе данных. Операция update.
    public void update(Long id, double newPrice) {
        for (Product product : database) {
            if (product.getId().equals(id)) {
                product.setPrice(newPrice);
                break;
            }
        }
    }

    // Метод, который удаляет продукт из базы данных.
    public void deleteDyId(Long id) {
        Iterator<Product> iterator = database.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }
}
