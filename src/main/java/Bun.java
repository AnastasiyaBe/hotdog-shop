/**
 * Класс булки для хот-дога
 */
public class Bun extends Component {
    private final String type;

    /**
     * Конструктор булки
     * @param type тип булки
     * @param price цена булки
     */
    public Bun(String type, double price) {
        super("Булка (" + type + ")", price);
        this.type = type;
    }

    /**
     * Получить тип булки
     * @return тип булки
     */
    public String getType() {
        return type;
    }
}