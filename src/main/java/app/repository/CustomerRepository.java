package app.repository;

import app.domain.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepository {

    // Для разнообразия сделаем тут имитацию базы данных в виде мап.
    private final Map<Long, Customer> database = new HashMap<>();
    private long maxId;

    // Метод сохранения покупателя.
    public Customer save(Customer customer) {
        customer.setId(++maxId);
        database.put(maxId, customer);
        return customer;
    }

    // Метод возврата всех
    public List<Customer> findAll() {
        // Как это работает.
        // 1. Метод values() отдаем нам коллекцию значений из мап.
        // 2. Это коллекцию мы отдаем в конструктор ArrayList
        // 3. Таким образом будет создан Arraylist из покупателей, которых нам вернул метод values.
        return new ArrayList<>(database.values());
    }

    public Customer findById(Long id) {
        return database.get(id);
    }

    public void update(Long id, String newName) {
        Customer customer = findById(id);
        if (customer != null) {
            customer.setName(newName);
        }
    }

    public void deleteById(Long id) {
        database.remove(id);
    }

}
