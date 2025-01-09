import java.util.HashMap;
import java.util.Map;

class Wallet {
    private double totalIncome;
    private double totalExpense;
    private Map<String, Double> incomeByCategory;
    private Map<String, Double> expenseByCategory;
    private Map<String, Double> budgetByCategory;

    public Wallet() {
        this.totalIncome = 0;
        this.totalExpense = 0;
        this.incomeByCategory = new HashMap<>();
        this.expenseByCategory = new HashMap<>();
        this.budgetByCategory = new HashMap<>();
    }

    public void addIncome(String category, double amount) {
        totalIncome += amount;
        incomeByCategory.put(category, incomeByCategory.getOrDefault(category, 0.0) + amount);
    }

    public void addExpense(String category, double amount) {
        totalExpense += amount;
        expenseByCategory.put(category, expenseByCategory.getOrDefault(category, 0.0) + amount);

        if (budgetByCategory.containsKey(category)) {
            double budget = budgetByCategory.get(category);
            double expense = expenseByCategory.get(category);
            if (expense > budget) {
                System.out.printf("Предупреждение: Вы превысили расходы в категории: '%s'. Текущие расходы: %.2f, Бюджет: %.2f\n", category, expense, budget);
            }
        }

        if (totalExpense > totalIncome) {
            System.out.printf("Предупреждение: Ваш суммарный расход (%.2f) превышает текущий доход (%.2f).\n", totalExpense, totalIncome);
        }
    }

    public void setBudget(String category, double amount) {
        budgetByCategory.put(category, amount);
    }

    public void displaySummary() {
        System.out.println("Общий доход: " + totalIncome);
        System.out.println("Общий расход: " + totalExpense);
        System.out.println("Бюджет категории: ");
        for (String category : budgetByCategory.keySet()) {
            double budget = budgetByCategory.get(category);
            double expense = expenseByCategory.getOrDefault(category, 0.0);
            double income = incomeByCategory.getOrDefault(category, 0.0);
            System.out.printf("%s: Бюджет = %.2f, Остаток = %.2f\n", category, budget, budget - expense + income);
        }
    }

    public String toString() {
        return totalIncome + "," + totalExpense + "," + mapToString(incomeByCategory) + "," + mapToString(expenseByCategory) + "," + mapToString(budgetByCategory);
    }

    private String mapToString(Map<String, Double> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
        }
        return sb.toString();
    }

    private static Map<String, Double> stringToMap(String data) {
        Map<String, Double> map = new HashMap<>();
        if (data.isEmpty()) return map;
        String[] entries = data.split(";");
        for (String entry : entries) {
            String[] keyValue = entry.split("=");
            map.put(keyValue[0], Double.parseDouble(keyValue[1]));
        }
        return map;
    }

    public static Wallet fromString(String data) {
        String[] parts = data.split(",", 5);
        Wallet wallet = new Wallet();
        wallet.totalIncome = Double.parseDouble(parts[0]);
        wallet.totalExpense = Double.parseDouble(parts[1]);
        wallet.incomeByCategory = stringToMap(parts[2]);
        wallet.expenseByCategory = stringToMap(parts[3]);
        wallet.budgetByCategory = stringToMap(parts[4]);
        return wallet;
    }
}
