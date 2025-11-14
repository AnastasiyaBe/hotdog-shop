/**
 * Класс сосиски для хот-дога
 */
public class Sausage extends Component {
    private final String type;

    /**
     * Конструктор сосиски
     * @param type тип сосиски
     * @param price цена сосиски
     */
    public Sausage(String type, double price) {
        super("Сосиска (" + type + ")", price);
        this.type = type;
    }

    /**
     * Получить тип сосиски
     * @return тип сосиски
     */
    public String getType() {
        return type;
    }
}