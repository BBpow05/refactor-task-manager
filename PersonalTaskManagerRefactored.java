import java.util.ArrayList;
import java.util.Scanner;

public class PersonalTaskManagerRefactored {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1 -> addTask();
                case 2 -> removeTask();
                case 3 -> viewTasks();
                case 4 -> {
                    System.out.println("Tạm biệt!");
                    running = false;
                }
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n==== QUẢN LÝ NHIỆM VỤ CÁ NHÂN ====");
        System.out.println("1. Thêm nhiệm vụ");
        System.out.println("2. Xóa nhiệm vụ");
        System.out.println("3. Xem danh sách nhiệm vụ");
        System.out.println("4. Thoát");
        System.out.print("Chọn chức năng: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Lựa chọn không hợp lệ
        }
    }

    private static void addTask() {
        System.out.print("Nhập tên nhiệm vụ: ");
        String task = scanner.nextLine();
        tasks.add(task);
        System.out.println("✔ Đã thêm nhiệm vụ.");
    }

    private static void removeTask() {
        viewTasks();
        System.out.print("Nhập số thứ tự nhiệm vụ cần xóa: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < tasks.size()) {
                tasks.remove(index);
                System.out.println("✔ Đã xóa nhiệm vụ.");
            } else {
                System.out.println("❌ Vị trí không hợp lệ.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Lỗi: Nhập không hợp lệ.");
        }
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("📭 Danh sách nhiệm vụ trống.");
        } else {
            System.out.println("📋 Danh sách nhiệm vụ:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, tasks.get(i));
            }
        }
    }
}
