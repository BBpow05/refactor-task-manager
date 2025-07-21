import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PersonalTaskManagerRefactored {

    private static final String DB_FILE_PATH = "tasks_database.json";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // REFACTOR 1: Hàm đọc dữ liệu từ file JSON
    private static JSONArray loadTasksFromDb() {
        try (FileReader reader = new FileReader(DB_FILE_PATH)) {
            Object obj = new JSONParser().parse(reader);
            if (obj instanceof JSONArray) {
                return (JSONArray) obj;
            }
        } catch (IOException | ParseException e) {
            System.err.println("Lỗi khi đọc dữ liệu: " + e.getMessage());
        }
        return new JSONArray();
    }

    // REFACTOR 2: Hàm ghi dữ liệu vào file JSON
    private static void saveTasksToDb(JSONArray tasksData) {
        try (FileWriter file = new FileWriter(DB_FILE_PATH)) {
            file.write(tasksData.toJSONString());
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi dữ liệu: " + e.getMessage());
        }
    }

    // REFACTOR 3: Kiểm tra chuỗi rỗng
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // REFACTOR 4: Kiểm tra ngày đến hạn hợp lệ
    private LocalDate validateDueDate(String dueDateStr) {
        try {
            return LocalDate.parse(dueDateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.out.println("Ngày đến hạn không hợp lệ. Định dạng đúng: YYYY-MM-DD.");
            return null;
        }
    }

    // REFACTOR 5: Kiểm tra mức độ ưu tiên
    private boolean isValidPriority(String level) {
        List<String> validLevels = Arrays.asList("Thấp", "Trung bình", "Cao");
        return validLevels.contains(level);
    }

    // REFACTOR 6: Kiểm tra nhiệm vụ trùng lặp
    private boolean isDuplicateTask(JSONArray tasks, String title, LocalDate dueDate) {
        for (Object obj : tasks) {
            JSONObject task = (JSONObject) obj;
            String existingTitle = (String) task.get("title");
            String existingDate = (String) task.get("due_date");

            if (existingTitle.equalsIgnoreCase(title)
                    && existingDate.equals(dueDate.format(DATE_FORMATTER))) {
                return true;
            }
        }
        return false;
    }

    // HÀM CHÍNH: Thêm nhiệm vụ mới
    public JSONObject addNewTask(String title, String description,
                                 String dueDateStr, String priorityLevel) {

        // B1: Kiểm tra tiêu đề
        if (isEmpty(title)) {
            System.out.println("Tiêu đề không được để trống.");
            return null;
        }

        // B2: Kiểm tra ngày đến hạn
        LocalDate dueDate = validateDueDate(dueDateStr);
        if (dueDate == null) return null;

        // B3: Kiểm tra mức độ ưu tiên
        if (!isValidPriority(priorityLevel)) {
            System.out.println("Mức độ ưu tiên không hợp lệ. Chọn: Thấp, Trung bình, Cao.");
            return null;
        }

        // B4: Load dữ liệu
        JSONArray tasks = loadTasksFromDb();

        // B5: Kiểm tra trùng lặp
        if (isDuplicateTask(tasks, title, dueDate)) {
            System.out.println("Nhiệm vụ đã tồn tại với cùng ngày đến hạn.");
            return null;
        }

        // B6: Tạo đối tượng nhiệm vụ
        JSONObject task = new JSONObject();
        task.put("id", UUID.randomUUID().toString());
        task.put("title", title);
        task.put("description", description);
        task.put("due_date", dueDate.format(DATE_FORMATTER));
        task.put("priority", priorityLevel);
        task.put("status", "Chưa hoàn thành");

        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        task.put("created_at", now);
        task.put("last_updated_at", now);

        // B7: Thêm vào danh sách và lưu
        tasks.add(task);
        saveTasksToDb(tasks);

        System.out.println("Đã thêm nhiệm vụ: " + title);
        return task;
    }

    // Hàm chạy thử
    public static void main(String[] args) {
        PersonalTaskManagerRefactored manager = new PersonalTaskManagerRefactored();
        manager.addNewTask("Học Java", "Ôn lại lập trình hướng đối tượng", "2025-07-22", "Cao");
    }
}
