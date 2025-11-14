import java.util.ArrayList;
import java.util.List;

/**
 * Класс магазина хот-догов для учета покупок
 */
public class HotDogShop {
    private final List<Order> orders;

    /**
     * Конструктор магазина
     */
    public HotDogShop() {
        this.orders = new ArrayList<>();
    }

    /**
     * Продажа хот-дога
     * @param variant вариант хот-дога
     * @param price базовая цена
     */
    public void sellHotDog(HotDogVariant variant, double price) {
        HotDog hotDog = new HotDog(variant, price);
        Order order = new Order();
        order.addHotDog(hotDog);
        orders.add(order);
        System.out.println("Продан: " + hotDog);
    }

    /**
     * Продажа отдельного компонента
     * @param component компонент для продажи
     */
    public void sellComponent(Component component) {
        Order order = new Order();
        order.addComponent(component);
        orders.add(order);
        System.out.println("Продан компонент: " + component);
    }

    /**
     * Получить общую сумму всех заказов
     * @return общая сумма
     */
    public double getTotalSum() {
        return orders.stream().mapToDouble(Order::getTotal).sum();
    }

    /**
     * Получить количество полных заказов (с хот-догами)
     * @return количество полных заказов
     */
    public int getFullOrderCount() {
        return (int) orders.stream().filter(Order::isFullOrder).count();
    }

    /**
     * Получить среднюю стоимость заказов
     * @return средняя стоимость
     */
    public double getAverageOrder() {
        if (orders.isEmpty()) {
            return 0;
        }
        return getTotalSum() / orders.size();
    }

    /**
     * Получить список всех заказов
     * @return список заказов
     */
    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }
}