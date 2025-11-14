/**
 * Класс соуса для хот-дога
 */
public class Sauce extends Component {
    private final String type;

    /**
     * Конструктор соуса
     * @param type тип соуса
     * @param price цена соуса
     */
    public Sauce(String type, double price) {
        super("Соус (" + type + ")", price);
        this.type = type;
    }

    /**
     * Получить тип соуса
     * @return тип соуса
     */
    public String getType() {
        return type;
    }
}