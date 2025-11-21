import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Графический интерфейс для управления заказами хот-догов
 */
public class HotDogShopGUI extends JFrame {
    private final HotDogRepository repository;
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private JLabel totalSumLabel;
    private JLabel fullOrdersLabel;
    private JLabel averageOrderLabel;

    public HotDogShopGUI() {
        repository = new HotDogRepository();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Учет покупок хот-догов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Создаем панель управления
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);

        // Создаем таблицу для отображения заказов
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // Создаем панель статистики
        JPanel statsPanel = createStatsPanel();
        add(statsPanel, BorderLayout.SOUTH);

        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Обновляем статистику при запуске
        updateStatistics();
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Управление заказами"));

        // Кнопка для добавления хот-дога
        JButton addHotDogButton = new JButton("Добавить хот-дог");
        addHotDogButton.addActionListener(new AddHotDogListener());
        panel.add(addHotDogButton);

        // Кнопка для добавления компонента
        JButton addComponentButton = new JButton("Добавить компонент");
        addComponentButton.addActionListener(new AddComponentListener());
        panel.add(addComponentButton);

        // Кнопка для удаления заказа
        JButton deleteOrderButton = new JButton("Удалить заказ");
        deleteOrderButton.addActionListener(new DeleteOrderListener());
        panel.add(deleteOrderButton);

        // Кнопка для обновления статистики
        JButton refreshButton = new JButton("Обновить");
        refreshButton.addActionListener(e -> updateStatistics());
        panel.add(refreshButton);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Список заказов"));

        // Создаем модель таблицы
        String[] columns = {"ID", "Тип", "Название", "Стоимость", "Полный заказ"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ordersTable = new JTable(tableModel);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(ordersTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Статистика"));

        totalSumLabel = new JLabel("Общая сумма: 0 руб.");
        fullOrdersLabel = new JLabel("Полных заказов: 0");
        averageOrderLabel = new JLabel("Средний заказ: 0 руб.");

        totalSumLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fullOrdersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        averageOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(totalSumLabel);
        panel.add(fullOrdersLabel);
        panel.add(averageOrderLabel);

        return panel;
    }

    private void updateOrdersTable() {
        tableModel.setRowCount(0);
        List<Order> orders = repository.getAllOrders();

        int orderId = 1;
        for (Order order : orders) {
            List<Object> items = order.getItems();
            for (Object item : items) {
                String type = item instanceof HotDog ? "Хот-дог" : "Компонент";
                String name = "";
                double price = 0;
                boolean isFullOrder = order.isFullOrder();

                if (item instanceof HotDog) {
                    HotDog hotDog = (HotDog) item;
                    name = hotDog.getVariant().getName();
                    price = hotDog.getTotalPrice();
                } else if (item instanceof Component) {
                    Component component = (Component) item;
                    name = component.getName();
                    price = component.getPrice();
                }

                tableModel.addRow(new Object[]{
                    orderId,
                    type,
                    name,
                    String.format("%.2f руб.", price),
                    isFullOrder ? "Да" : "Нет"
                });
            }
            orderId++;
        }
    }

    private void updateStatistics() {
        List<Order> orders = repository.getAllOrders();
        
        double totalSum = orders.stream().mapToDouble(Order::getTotal).sum();
        long fullOrdersCount = orders.stream().filter(Order::isFullOrder).count();
        double averageOrder = orders.isEmpty() ? 0 : totalSum / orders.size();

        totalSumLabel.setText(String.format("Общая сумма: %.2f руб.", totalSum));
        fullOrdersLabel.setText(String.format("Полных заказов: %d", fullOrdersCount));
        averageOrderLabel.setText(String.format("Средний заказ: %.2f руб.", averageOrder));
        
        updateOrdersTable();
    }

    // Слушатель для добавления хот-дога
    private class AddHotDogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dialog = new JDialog(HotDogShopGUI.this, "Добавить хот-дог", true);
            dialog.setLayout(new GridLayout(4, 2, 10, 10));
            dialog.setSize(300, 200);
            dialog.setLocationRelativeTo(HotDogShopGUI.this);

            JLabel variantLabel = new JLabel("Вариант хот-дога:");
            JComboBox<HotDogVariant> variantCombo = new JComboBox<>(HotDogVariant.values());
            
            JLabel priceLabel = new JLabel("Базовая цена:");
            JTextField priceField = new JTextField("50");

            JButton addButton = new JButton("Добавить");
            JButton cancelButton = new JButton("Отмена");

            addButton.addActionListener(ev -> {
                try {
                    HotDogVariant variant = (HotDogVariant) variantCombo.getSelectedItem();
                    double price = Double.parseDouble(priceField.getText());
                    
                    HotDog hotDog = new HotDog(variant, price);
                    Order order = new Order();
                    order.addHotDog(hotDog);
                    repository.addOrder(order);
                    
                    updateOrdersTable();
                    updateStatistics();
                    dialog.dispose();
                    
                    JOptionPane.showMessageDialog(HotDogShopGUI.this, 
                        "Хот-дог добавлен успешно!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Введите корректную цену!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            });

            cancelButton.addActionListener(ev -> dialog.dispose());

            dialog.add(variantLabel);
            dialog.add(variantCombo);
            dialog.add(priceLabel);
            dialog.add(priceField);
            dialog.add(addButton);
            dialog.add(cancelButton);

            dialog.setVisible(true);
        }
    }

    // Слушатель для добавления компонента
    private class AddComponentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dialog = new JDialog(HotDogShopGUI.this, "Добавить компонент", true);
            dialog.setLayout(new GridLayout(5, 2, 10, 10));
            dialog.setSize(350, 250);
            dialog.setLocationRelativeTo(HotDogShopGUI.this);

            JLabel typeLabel = new JLabel("Тип компонента:");
            JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Булка", "Сосиска", "Соус"});
            
            JLabel nameLabel = new JLabel("Название:");
            JTextField nameField = new JTextField();
            
            JLabel priceLabel = new JLabel("Цена:");
            JTextField priceField = new JTextField("10");

            JButton addButton = new JButton("Добавить");
            JButton cancelButton = new JButton("Отмена");

            addButton.addActionListener(ev -> {
                try {
                    String componentType = (String) typeCombo.getSelectedItem();
                    String name = nameField.getText().trim();
                    double price = Double.parseDouble(priceField.getText());
                    
                    if (name.isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, 
                            "Введите название компонента!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    Component component;
                    switch (componentType) {
                        case "Булка":
                            component = new Bun(name, price);
                            break;
                        case "Сосиска":
                            component = new Sausage(name, price);
                            break;
                        case "Соус":
                            component = new Sauce(name, price);
                            break;
                        default:
                            throw new IllegalArgumentException("Неизвестный тип компонента");
                    }
                    
                    Order order = new Order();
                    order.addComponent(component);
                    repository.addOrder(order);
                    
                    updateOrdersTable();
                    updateStatistics();
                    dialog.dispose();
                    
                    JOptionPane.showMessageDialog(HotDogShopGUI.this, 
                        "Компонент добавлен успешно!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Введите корректную цену!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dialog, 
                        ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            });

            cancelButton.addActionListener(ev -> dialog.dispose());

            dialog.add(typeLabel);
            dialog.add(typeCombo);
            dialog.add(nameLabel);
            dialog.add(nameField);
            dialog.add(priceLabel);
            dialog.add(priceField);
            dialog.add(addButton);
            dialog.add(cancelButton);

            dialog.setVisible(true);
        }
    }

    // Слушатель для удаления заказа
    private class DeleteOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = ordersTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(HotDogShopGUI.this, 
                    "Выберите заказ для удаления!", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(HotDogShopGUI.this, 
                "Вы уверены, что хотите удалить выбранный заказ?", 
                "Подтверждение удаления", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean removed = repository.removeOrder(selectedRow);
                if (removed) {
                    updateOrdersTable();
                    updateStatistics();
                    JOptionPane.showMessageDialog(HotDogShopGUI.this, 
                        "Заказ успешно удален!", "Успех", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(HotDogShopGUI.this, 
                        "Ошибка при удалении заказа!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Используем системный Look and Feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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