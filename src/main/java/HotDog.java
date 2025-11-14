import java.util.ArrayList;
import java.util.List;

/**
 * Класс хот-дога, использующий композицию для сборки из компонентов
 */
public class HotDog {
    private final HotDogVariant variant;
    private final List<Component> components;
    private final double basePrice;

    /**
     * Конструктор хот-дога
     * @param variant вариант хот-дога
     * @param basePrice базовая цена
     */
    public HotDog(HotDogVariant variant, double basePrice) {
        this.variant = variant;
        this.basePrice = basePrice;
        this.components = new ArrayList<>();
        initializeComponents();
    }

    /**
     * Инициализация компонентов хот-дога
     */
    private void initializeComponents() {
        switch (variant) {
            case HUNTER_DOG:
                components.add(new Bun("Пшеничная", 30));
                components.add(new Sausage("Охотничья", 80));
                components.add(new Sauce("Горчичный", 10));
                break;
            case MASTER_DOG:
                components.add(new Bun("Ржаная", 35));
                components.add(new Sausage("Докторская", 90));
                components.add(new Sauce("Кетчуп", 10));
                components.add(new Sauce("Майонез", 10));
                break;
            case BERLINKA:
                components.add(new Bun("Немецкая", 40));
                components.add(new Sausage("Баварская", 100));
                components.add(new Sauce("Горчичный", 10));
                components.add(new Sauce("Кетчуп", 10));
                break;
        }
    }

    /**
     * Получить общую стоимость хот-дога
     * @return общая стоимость
     */
    public double getTotalPrice() {
        double componentsPrice = components.stream()
                .mapToDouble(Component::getPrice)
                .sum();
        return basePrice + componentsPrice;
    }

    /**
     * Получить вариант хот-дога
     * @return вариант хот-дога
     */
    public HotDogVariant getVariant() {
        return variant;
    }

    /**
     * Получить список компонентов
     * @return список компонентов
     */
    public List<Component> getComponents() {
        return new ArrayList<>(components);
    }

    /**
     * Строковое представление хот-дога
     * @return строка с информацией о хот-доге
     */
    @Override
    public String toString() {
        return String.format("%s - %.2f руб.", variant.getName(), getTotalPrice());
    }
}