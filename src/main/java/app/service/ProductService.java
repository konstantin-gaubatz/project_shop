package app.service;

/*
Этот класс находится на третьем слое нашего приложения. Сервисы содержать всю бизнес-логику, то есть логику по всей
необходимой обработке объектов для целей нашего приложения.
Сервис не имеет прямого доступа к базе данных.
Чтобы работать с данными сервис обращается к репозиторию.
*/


import app.domain.Product;
import app.exceptions.ProductNotFoundException;
import app.exceptions.ProductSaveException;
import app.exceptions.ProductUpdateException;
import app.repository.ProductRepository;

import java.util.List;

public class ProductService {

    // Создаем объект репозитория, чтобы иметь доступ к его методам.
    private final ProductRepository repository = new ProductRepository();

    //    Функционал сервиса продуктов.
//
//    Сохранить продукт в базе данных (при сохранении продукт автоматически считается активным).
    public Product save(Product product) {
        if (product == null) {
            throw new ProductSaveException("Продукт не может быть null");
        }

        String title = product.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new ProductSaveException("Наименование продукта не должно быть пустым");
        }

        if (product.getPrice() < 0) {
            throw new ProductSaveException("Цена продукта не должна быть отрицательной");
        }

        product.setActive(true);
        return repository.save(product);
    }
//    Вернуть все продукты из базы данных (активные).

    public List<Product> getAllActiveProducts() {
        return repository.findAll().stream().filter(Product::isActive).toList();
    }

//    Вернуть один продукт из базы данных по его идентификатору (если он активен).
    public Product getActiveProductById(Long id) {
        Product product = repository.findById(id);

        if (product == null || !product.isActive()) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }
//    Изменить один продукт в базе данных по его идентификатору.

    public void update(Long id, double newPrice) {
        if (newPrice < 0) {
            throw new ProductUpdateException("Цена продукта не должна быть отрицательной");
        }
        repository.update(id, newPrice);
    }
//    Удалить продукт из базы данных по его идентификатору.

    public void deleteById(Long id) {
        Product product = getActiveProductById(id);
        product.setActive(false);
    }
//    Удалить продукт из базы данных по его наименованию.

    public void deleteByTitle(String title) {
        getAllActiveProducts()
                .stream()
                .filter(x -> x.getTitle().equals(title))
                .forEach(x -> x.setActive(false));

    }
//    Восстановить удалённый продукт в базе данных по его идентификатору.

    public void restoreById(Long id) {
        Product product = repository.findById(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        product.setActive(true);

    }
//    Вернуть общее количество продуктов в базе данных (активных).

    public int getActiveProductsNumber() {
        return getAllActiveProducts().size();
    }
//    Вернуть суммарную стоимость всех продуктов в базе данных (активных).

    public double getActibeProductsTotalCost() {
        // 1 Способ.
//        double sum = 0.0;
//        for (Product product : getAllActiveProducts()) {
//            sum += product.getPrice();
//        }
//        return sum;
        // Способ 2
        return getAllActiveProducts().stream().mapToDouble(Product::getPrice).sum();
    }
//    Вернуть среднюю стоимость продукта в базе данных (из активных).

    public double getActiveProductsAveragePrice() {
        // Способ 1.
//        int productNumber = getActiveProductsNumber();
//
//        if (productNumber == 0) {
//            return 0.0;
//        }
//
//        return getActiveProductsTotalCost() / productNumber;
//    }
        return getAllActiveProducts().stream().mapToDouble(Product::getPrice).average().orElse(0.0);
    }
}
