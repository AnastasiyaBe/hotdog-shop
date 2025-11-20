import java.util.List;

/**
 * Класс магазина хот-догов для учета покупок
 * РЕФАКТОРИНГ: теперь использует репозиторий вместо прямого управления заказами
 */
public class HotDogShop {
    // РЕФАКТОРИНГ: заменили List<Order> на репозиторий для лучшей организации кода
    private final HotDogRepository repository;

    public HotDogShop() {
        this.repository = new HotDogRepository(); // РЕФАКТОРИНГ: инициализация репозитория
    }

    /**
     * Продажа хот-дога с автоматическим добавлением в репозиторий
     */
    public void sellHotDog(HotDogVariant variant, double price) {
        HotDog hotDog = new HotDog(variant, price);
        Order order = new Order();
        order.addHotDog(hotDog);
        
        // РЕФАКТОРИНГ: используем репозиторий вместо прямого добавления в список
        int orderId = repository.addOrder(order);
        System.out.println("Продан: " + hotDog + " (ID заказа: " + orderId + ")");
    }

    /**
     * Продажа отдельного компонента
     */
    public void sellComponent(Component component) {
        Order order = new Order();
        order.addComponent(component);
        
        // РЕФАКТОРИНГ: используем репозиторий
        int orderId = repository.addOrder(order);
        System.out.println("Продан компонент: " + component + " (ID заказа: " + orderId + ")");
    }

    /**
     * Получить общую сумму всех заказов
     * РЕФАКТОРИНГ: теперь считаем через репозиторий
     */
    public double getTotalSum() {
        double total = 0;
        for (Order order : repository.getAllOrders()) {
            total += order.getTotal();
        }
        return total;
    }

    /**
     * Получить количество полных заказов (с хот-догами)
     * РЕФАКТОРИНГ: используем метод репозитория
     */
    public int getFullOrderCount() {
        return repository.getFullOrders().size();
    }

    /**
     * Получить среднюю стоимость заказов
     */
    public double getAverageOrder() {
        int count = repository.getOrderCount();
        if (count == 0) {
            return 0;
        }
        return getTotalSum() / count;
    }

    /**
     * Получить список всех заказов
     * РЕФАКТОРИНГ: получаем заказы из репозитория
     */
    public List<Order> getOrders() {
        return repository.getAllOrders();
    }
}
