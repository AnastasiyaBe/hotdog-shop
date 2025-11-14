import java.util.ArrayList;
import java.util.List;

/**
 * Класс заказа, содержащий продукты и компоненты
 */
public class Order {
    private final List<Object> items;

    /**
     * Конструктор заказа
     */
    public Order() {
        this.items = new ArrayList<>();
    }

    /**
     * Добавить хот-дог в заказ
     * @param hotDog хот-дог для добавления
     */
    public void addHotDog(HotDog hotDog) {
        items.add(hotDog);
    }

    /**
     * Добавить компонент в заказ
     * @param component компонент для добавления
     */
    public void addComponent(Component component) {
        items.add(component);
    }

    /**
     * Получить общую сумму заказа
     * @return общая сумма
     */
    public double getTotal() {
        return items.stream()
                .mapToDouble(item -> {
                    if (item instanceof HotDog) {
                        return ((HotDog) item).getTotalPrice();
                    } else if (item instanceof Component) {
                        return ((Component) item).getPrice();
                    }
                    return 0;
                })
                .sum();
    }

    /**
     * Получить количество позиций в заказе
     * @return количество позиций
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * Проверить, является ли заказ полным (содержит хот-доги)
     * @return true если заказ содержит хот-доги
     */
    public boolean isFullOrder() {
        return items.stream().anyMatch(item -> item instanceof HotDog);
    }

    /**
     * Получить список элементов заказа
     * @return список элементов
     */
    public List<Object> getItems() {
        return new ArrayList<>(items);
    }
}