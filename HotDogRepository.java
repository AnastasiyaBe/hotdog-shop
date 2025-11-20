import java.util.ArrayList;
import java.util.List;

/**
 * Класс-репозиторий для хранения и управления заказами
 * НОВЫЙ КЛАСС: отвечает за хранение всех заказов
 */
public class HotDogRepository {
    private List<Order> orders;
    private int nextOrderId;

    public HotDogRepository() {
        this.orders = new ArrayList<>();
        this.nextOrderId = 1;
    }

    /**
     * Добавляет новый заказ в репозиторий
     */
    public int addOrder(Order order) {
        orders.add(order);
        return nextOrderId++;
    }

    /**
     * Возвращает все заказы
     */
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Возвращает количество заказов
     */
    public int getOrderCount() {
        return orders.size();
    }

    /**
     * Находит все полные заказы (с хот-догами)
     */
    public List<Order> getFullOrders() {
        List<Order> fullOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.isFullOrder()) {
                fullOrders.add(order);
            }
        }
        return fullOrders;
    }
}
