import java.util.ArrayList;
import java.util.Scanner;

public class PersonalTaskManagerRefactored {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> tasks = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addTask();
                case "2" -> removeTask();
                case "3" -> updateTask();
                case "4" -> viewTasks();
                case "5" -> {
                    System.out.println("Đã thoát chương trình.");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== TRÌNH QUẢN LÝ CÔNG VIỆC CÁ NHÂN ===");
        System.out.println("1. Thêm công việc");
        System.out.println("2. Xóa công việc");
        System.out.println("3. Cập nhật công việc");
        System.out.println("4. Xem danh sách công việc");
        System.out.println("5. Thoát");
        System.out.print("Chọn chức năng (1-5): ");
    }

    private static void addTask() {
        System.out.print("Nhập tên công việc mới: ");
        String task = scanner.nextLine().trim();
        if (!task.isEmpty()) {
            tasks.add(task);
            System.out.println("Đã thêm: " + task);
        } else {
            System.out.println("Tên công việc không được để trống.");
        }
    }

    private static void removeTask() {
        if (tasks.isEmpty()) {
            System.out.println("Danh sách công việc trống.");
            return;
        }

        viewTasks();
        int index = getTaskIndex("Nhập số thứ tự công việc cần xóa: ");
        if (isValidIndex(index)) {
            System.out.println("Đã xóa: " + tasks.remove(index));
        } else {
            System.out.println("Vị trí không hợp lệ.");
        }
    }

    private static void updateTask() {
        if (tasks.isEmpty()) {
            System.out.println("Danh sách công việc trống.");
            return;
        }

        viewTasks();
        int index = getTaskIndex("Nhập số thứ tự công việc cần cập nhật: ");
        if (isValidIndex(index)) {
            System.out.print("Nhập nội dung mới: ");
            String newTask = scanner.nextLine().trim();
            if (!newTask.isEmpty()) {
                tasks.set(index, newTask);
                System.out.println("Đã cập nhật.");
            } else {
                System.out.println("Nội dung không được để trống.");
            }
        } else {
            System.out.println("Vị trí không hợp lệ.");
        }
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Danh sách công việc trống.");
            return;
        }

        System.out.println("\nDanh sách công việc:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, tasks.get(i));
        }
    }

    private static int getTaskIndex(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }
}
