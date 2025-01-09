import java.io.*;
import java.util.*;

public class Main {
    private static Map<String, User> users = new HashMap<>();
    private static User currentUser;
    private static final String DATA_FILE = "users.txt";

    public static void main(String[] args) {
        loadUsers();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Регистрация\n2. Вход\n3. Выход");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    register(scanner);
                    break;
                case "2":
                    if (login(scanner)) {
                        userMenu(scanner);
                    }
                    break;
                case "3":
                    saveUsers();
                    System.out.println("Выход из приложения...");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте ещё раз.");
            }
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Такое имя пользователя уже существует.");
        } else {
            users.put(username, new User(username, password));
            System.out.println("Успешная регистрация.");
        }
    }

    private static boolean login(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            currentUser = user;
            System.out.println("Успешный вход.");
            return true;
        } else {
            System.out.println("Неверное имя пользователя или пароль.");
            return false;
        }
    }

    private static void userMenu(Scanner scanner) {
        Wallet wallet = currentUser.getWallet();
        while (true) {
            System.out.println("1. Добавить доход\n2. Добавить расход\n3. Установить бюджет в категории\n4. Отобразить данные\n5. Выход из кабинета");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введите категорию: ");
                    String incomeCategory = scanner.nextLine();
                    System.out.print("Введите сумму: ");
                    double incomeAmount = Double.parseDouble(scanner.nextLine());
                    wallet.addIncome(incomeCategory, incomeAmount);
                    break;
                case "2":
                    System.out.print("Введите категорию: ");
                    String expenseCategory = scanner.nextLine();
                    System.out.print("Введите сумму: ");
                    double expenseAmount = Double.parseDouble(scanner.nextLine());
                    wallet.addExpense(expenseCategory, expenseAmount);
                    break;
                case "3":
                    System.out.print("Введите категорию: ");
                    String budgetCategory = scanner.nextLine();
                    System.out.print("Введите сумму: ");
                    double budgetAmount = Double.parseDouble(scanner.nextLine());
                    wallet.setBudget(budgetCategory, budgetAmount);
                    break;
                case "4":
                    wallet.displaySummary();
                    break;
                case "5":
                    System.out.println("Выход из кабинета...");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте ещё раз.");
            }
        }
    }

    private static void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (User user : users.values()) {
                writer.println(user);
            }
            System.out.println("Данные успешно сохранены.");
        } catch (IOException e) {
            System.out.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }

    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                users.put(user.getUsername(), user);
            }
            System.out.println("Данные успешно загружены.");
        } catch (FileNotFoundException e) {
            System.out.println("Не найдено предыдущих данных. Создан новый файл.");
        } catch (IOException e) {
            System.out.println("Ошибка загрузки данных: " + e.getMessage());
        }
    }
}
