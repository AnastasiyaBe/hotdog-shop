/**
 * Базовый класс для всех компонентов хот-дога.
 * Реализует возможность продажи компонентов отдельно.
 */
public class Component {
    private final String name;
    private final double price;

    /**
     * Конструктор компонента
     * @param name название компонента
     * @param price цена компонента
     */
    public Component(String name, double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Получить название компонента
     * @return название компонента
     */
    public String getName() {
        return name;
    }

    /**
     * Получить цену компонента
     * @return цена компонента
     */
    public double getPrice() {
        return price;
    }

    /**
     * Строковое представление компонента
     * @return строка с информацией о компоненте
     */
    @Override
    public String toString() {
        return String.format("%s - %.2f руб.", name, price);
    }
}