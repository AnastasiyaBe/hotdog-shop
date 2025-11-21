import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Главный класс для запуска графического приложения
 */
public class Main {
    public static void main(String[] args) {
        // Запуск графического интерфейса
        SwingUtilities.invokeLater(() -> {
            try {
                // Установка системного Look and Feel
                javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | 
                     IllegalAccessException | UnsupportedLookAndFeelException e) {
                // Логируем ошибку, но продолжаем работу
                System.err.println("Ошибка при установке Look and Feel: " + e.getMessage());
            }
            
            HotDogShopGUI gui = new HotDogShopGUI();
            gui.setVisible(true);
        });
    }
}