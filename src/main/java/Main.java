import java.util.List;

/**
 * Демонстрационное консольное приложение для учета покупок хот-догов
 */
public class Main {
    /**
     * Основной метод приложения
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        HotDogShop shop = new HotDogShop();

        System.out.println("=== Учет покупок хот-догов ===\n");

        // 3. Продать каждый вид хот-дога
        System.out.println("1. Продажа хот-догов:");
        shop.sellHotDog(HotDogVariant.HUNTER_DOG, 50);
        shop.sellHotDog(HotDogVariant.MASTER_DOG, 60);
        shop.sellHotDog(HotDogVariant.BERLINKA, 70);

        // 2. Продажа компонентов отдельно
        System.out.println("\n2. Продажа компонентов отдельно:");
        shop.sellComponent(new Bun("Пшеничная", 30));
        shop.sellComponent(new Sausage("Охотничья", 80));
        shop.sellComponent(new Sauce("Горчичный", 10));

        // Вывод результатов
        System.out.println("\n3. Результаты учета:");
        // 4. Подсчитать общую сумму всех заказов
        System.out.printf("Общая сумма всех заказов: %.2f руб.%n", shop.getTotalSum());
        
        // 5. Подсчитать количество полных заказов
        System.out.printf("Количество полных заказов (с хот-догами): %d%n", shop.getFullOrderCount());
        
        // 6. Подсчитать среднюю стоимость заказов
        System.out.printf("Средняя стоимость заказов: %.2f руб.%n", shop.getAverageOrder());

        // Дополнительная информация
        System.out.println("\n4. Детализация заказов:");
        List<Order> orders = shop.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.printf("Заказ #%d: %.2f руб. (%s)%n", 
                i + 1, 
                order.getTotal(),
                order.isFullOrder() ? "полный" : "компоненты");
        }
    }
}