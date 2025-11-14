/**
 * Перечисление вариантов хот-догов
 */
public enum HotDogVariant {
    /** Охотничий хот-дог */
    HUNTER_DOG("Охотничий"),
    /** Мастер Дог */
    MASTER_DOG("Мастер Дог"), 
    /** Берлинка */
    BERLINKA("Берлинка");

    private final String name;

    /**
     * Конструктор варианта хот-дога
     * @param name название варианта
     */
    HotDogVariant(String name) {
        this.name = name;
    }

    /**
     * Получить название варианта
     * @return название варианта хот-дога
     */
    public String getName() {
        return name;
    }
}