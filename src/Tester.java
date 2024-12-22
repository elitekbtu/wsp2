import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

// Подключаем ваши классы:
import news.NewsList;
import users.UsersList;
import users.employees.TechSupportOrder;
import courses.CourseData.Data;

public class Tester {
    private static final String FILE_NAME = "user_data.txt";

    /**
     * Хранит данные в формате:
     *   login -> "пароль:тип"
     * Пример:
     *   userDatabase.get("ivan") = "qwerty:Teacher"
     */
    private static HashMap<String, String> userDatabase = new HashMap<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadUserData();
        while (true) {
            System.out.println("\nВыберите действие: 1 - Регистрация, 2 - Вход, 0 - Выход");
            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    if (loginUser()) {
                        // Если логин успешен, в loginUser() будет вызван mainMenu(...),
                        // и пользователь выйдет оттуда, когда выберет "Выйти".
                    }
                    break;
                case 0:
                    System.out.println("Программа завершена.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    /**
     * Загрузка данных из файла user_data.txt
     * Каждая строка формата: "login:password:type"
     */
    private static void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    // parts[0] = login, parts[1] = password, parts[2] = type
                    String login = parts[0];
                    String password = parts[1];
                    String userType = parts[2];
                    // В HashMap положим: login -> "password:userType"
                    userDatabase.put(login, password + ":" + userType);
                }
            }
            System.out.println("Данные пользователей загружены.");
        } catch (FileNotFoundException e) {
            System.out.println("Файл данных не найден. Будет создан новый при регистрации.");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    /**
     * Сохранение данных в файл user_data.txt
     * Перезаписываем файл заново всем содержимым из userDatabase
     */
    private static void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String login : userDatabase.keySet()) {
                // Достаём "пароль:тип"
                String passAndType = userDatabase.get(login);
                String[] parts = passAndType.split(":");
                if (parts.length == 2) {
                    String password = parts[0];
                    String userType = parts[1];
                    // Пишем в файл: login:password:type
                    writer.write(login + ":" + password + ":" + userType);
                    writer.newLine();
                }
            }
            System.out.println("Данные пользователей сохранены.");
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }

    /**
     * Регистрация нового пользователя
     */
    private static void registerUser() {
        System.out.println("Введите логин:");
        String registerLogin = scanner.nextLine();

        if (userDatabase.containsKey(registerLogin)) {
            System.out.println("Логин уже существует. Попробуйте другой.");
        } else {
            System.out.println("Введите пароль:");
            String registerPassword = scanner.nextLine();

            // Выбираем тип пользователя
            System.out.println("Выберите тип пользователя:");
            System.out.println("1 - Student");
            System.out.println("2 - Teacher");
            System.out.println("3 - Manager");
            System.out.println("4 - Admin");
            System.out.println("5 - TechSupportSpecialist");
            int typeChoice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            String userType;
            switch (typeChoice) {
                case 1:
                    userType = "Student";
                    break;
                case 2:
                    userType = "Teacher";
                    break;
                case 3:
                    userType = "Manager";
                    break;
                case 4:
                    userType = "Admin";
                    break;
                case 5:
                    userType = "TechSupportSpecialist";
                    break;
                default:
                    System.out.println("Неверный выбор. Тип по умолчанию: Student.");
                    userType = "Student";
            }

            // Сохраняем логин-> "пароль:тип"
            userDatabase.put(registerLogin, registerPassword + ":" + userType);
            saveUserData(); // Записываем в файл

            // Дополнительно вызываем UsersList для записи более подробных данных (ID, fullname, email).
            UsersList usersList = new UsersList();
            usersList.register();

            System.out.println("Регистрация успешна!");
        }
    }

    /**
     * Авторизация
     */
    private static boolean loginUser() {
        System.out.println("Введите логин:");
        String login = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        if (userDatabase.containsKey(login)) {
            String passAndType = userDatabase.get(login); // "пароль:тип"
            String[] parts = passAndType.split(":");
            if (parts.length == 2) {
                String storedPassword = parts[0];
                String userType = parts[1];
                if (storedPassword.equals(password)) {
                    System.out.println("Вход выполнен успешно! Добро пожаловать, " + login + "!");
                    // Переходим в меню
                    mainMenu(userType);
                    return true;
                }
            }
        }

        System.out.println("Ошибка входа. Проверьте логин и пароль.");
        return false;
    }

    /**
     * Главное меню с учётом типа пользователя
     */
    private static void mainMenu(String userType) {
        while (true) {
            System.out.println("\nГлавное меню:");
            System.out.println("1 - Новостная лента");
            System.out.println("2 - Данные пользователя");
            System.out.println("3 - Транскрипт (заглушка)");
            System.out.println("4 - Курсы");
            System.out.println("5 - Техническая поддержка");
            System.out.println("6 - Выйти");

            // Добавляем пункт 7, если это Teacher / Manager / Admin / TechSupport
            if ("Teacher".equalsIgnoreCase(userType)) {
                System.out.println("7 - Поставить оценку студенту");
            } else if ("Manager".equalsIgnoreCase(userType)) {
                System.out.println("7 - Управление финансами");
            } else if ("Admin".equalsIgnoreCase(userType)) {
                System.out.println("7 - Удалить пользователя");
            } else if ("TechSupportSpecialist".equalsIgnoreCase(userType)) {
                System.out.println("7 - Решить проблему пользователя");
            }

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Попробуйте снова.");
                continue;
            }

            switch (choice) {
                case 1:
                    newsFeed();
                    break;
                case 2:
                    userData();
                    break;
                case 3:
                    transcript();
                    break;
                case 4:
                    System.out.println("Введите имя пользователя (логин) для сохранения курсов:");
                    String username = scanner.nextLine();
                    registerForCourses(username);
                    break;
                case 5:
                    technicalSupport();
                    break;
                case 6:
                    System.out.println("Выход из меню.");
                    return; // Возвращаемся в метод main
                case 7:
                    // Уникальный пункт меню в зависимости от роли
                    if ("Teacher".equalsIgnoreCase(userType)) {
                        setGradeMenu();
                    } else if ("Manager".equalsIgnoreCase(userType)) {
                        financeMenu();
                    } else if ("Admin".equalsIgnoreCase(userType)) {
                        adminMenu();
                    } else if ("TechSupportSpecialist".equalsIgnoreCase(userType)) {
                        solveIssueMenu();
                    } else {
                        System.out.println("Недоступный пункт меню.");
                    }
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    /**
     * Поставить оценку студенту (Teacher)
     */
    private static void setGradeMenu() {
        System.out.println("Введите ID студента, которому хотите поставить оценку:");
        String studentId = scanner.nextLine();
        System.out.println("Введите оценку:");
        String grade = scanner.nextLine();
        // Логика сохранения оценки в БД или файл может быть здесь
        System.out.println("Учитель поставил студенту " + studentId + " оценку: " + grade);
    }

    /**
     * Управление финансами (Manager)
     */
    private static void financeMenu() {
        System.out.println("=== Управление финансами ===");
        // Здесь любая логика, связанная с финансами
        System.out.println("Функционал менеджера по финансам пока условный.");
    }

    /**
     * Удалить пользователя (Admin)
     */
    private static void adminMenu() {
        System.out.println("Введите логин пользователя, которого хотите удалить:");
        String loginToDelete = scanner.nextLine();
        if (userDatabase.containsKey(loginToDelete)) {
            userDatabase.remove(loginToDelete);
            saveUserData();
            System.out.println("Пользователь " + loginToDelete + " успешно удалён.");
            // При желании можно также удалить txt-файл из папки UsersData
        } else {
            System.out.println("Пользователь с логином " + loginToDelete + " не найден.");
        }
    }

    /**
     * Решить проблему пользователя (TechSupport)
     */
    private static void solveIssueMenu() {
        System.out.println("Введите ID запроса на техподдержку, который хотите решить:");
        String ticketId = scanner.nextLine();

        TechSupportOrder order = TechSupportOrder.findRegistry(ticketId);
        if (order == null) {
            System.out.println("Запрос с таким ID не найден.");
            return;
        }

        if (order.isDone()) {
            System.out.println("Этот запрос уже завершён.");
            return;
        }

        // Помечаем как решён
        order.setDone(true);
        order.setAccepted(false);
        order.setNew(false);
        System.out.println("Запрос " + ticketId + " отмечен как решённый.");
    }

    /* ================================
        Общие методы для всех ролей
       ================================ */

    /**
     * Новостная лента
     */
    private static void newsFeed() {
        System.out.println("Вы выбрали новостную ленту.");
        NewsList newsList = new NewsList();

        Random random = new Random();
        int count = 5;
        System.out.println("Последние новости:");

        for (int i = 0; i < count; i++) {
            int index = random.nextInt(newsList.news.length);
            System.out.println((i + 1) + ". " + newsList.news[index]);
        }
    }

    /**
     * Данные пользователя (студента/сотрудника) — считываем из файлов
     */
    private static void userData() {
        System.out.println("Вы выбрали просмотр данных пользователя.");
        UsersList usersList = new UsersList();
        usersList.loadUserData();
    }

    /**
     * Транскрипт — заглушка
     */
    private static void transcript() {
        System.out.println("=== Транскрипт пока не реализован. ===");
    }

    /**
     * Выбор курсов для студента
     */
    private static void registerForCourses(String username) {
        showAvailableCourses();

        System.out.println("\nВведите номера курсов через запятую (например: 1,3,4):");
        String input = scanner.nextLine();
        String[] selectedIndexes = input.split(",");

        Data[] courses = Data.getAvailableCourses(); // метод, возвращающий список доступных курсов
        ArrayList<Data> selectedCourses = new ArrayList<>();

        System.out.println("Вы выбрали следующие курсы:");
        for (String indexStr : selectedIndexes) {
            try {
                int index = Integer.parseInt(indexStr.trim()) - 1;
                if (index >= 0 && index < courses.length) {
                    Data selectedCourse = courses[index];
                    System.out.printf("[%s] %s (%d ECTS)%n",
                            selectedCourse.getCode(),
                            selectedCourse.getDiscipline(),
                            selectedCourse.getEcts());
                    selectedCourses.add(selectedCourse);
                } else {
                    System.out.println("Неверный номер курса: " + (index + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод: " + indexStr);
            }
        }

        if (!selectedCourses.isEmpty()) {
            saveSelectedCourses(username, selectedCourses.toArray(new Data[0]));
        } else {
            System.out.println("Курсы не были выбраны.");
        }
    }

    /**
     * Показать доступные курсы
     */
    private static void showAvailableCourses() {
        System.out.println("Доступные курсы:");
        Data[] courses = Data.getAvailableCourses(); // статический метод, который отдаёт список
        for (int i = 0; i < courses.length; i++) {
            Data course = courses[i];
            System.out.printf("%d. [%s] %s (%d ECTS)%n",
                    i + 1,
                    course.getCode(),
                    course.getDiscipline(),
                    course.getEcts());
        }
    }

    /**
     * Сохранить выбранные курсы для пользователя
     * Например, просто в текстовый файл "username_courses.txt"
     */
    private static void saveSelectedCourses(String username, Data[] selectedCourses) {
        String fileName = username + "_courses.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Data course : selectedCourses) {
                writer.write(String.format("[%s] %s (%d ECTS)%n",
                        course.getCode(),
                        course.getDiscipline(),
                        course.getEcts()));
            }
            System.out.println("Выбранные курсы сохранены в файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }

    /**
     * Меню технической поддержки
     */
    private static void technicalSupport() {
        System.out.println("Вы выбрали техническую поддержку.");
        while (true) {
            System.out.println("\n1 - Создать запрос");
            System.out.println("2 - Просмотреть запросы");
            System.out.println("3 - Закрыть запрос");
            System.out.println("4 - Вернуться в главное меню");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Попробуйте снова.");
                continue;
            }

            switch (choice) {
                case 1:
                    createSupportRequest();
                    break;
                case 2:
                    viewSupportRequests();
                    break;
                case 3:
                    closeSupportRequest();
                    break;
                case 4:
                    System.out.println("Возвращаемся в главное меню.");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    /**
     * Создать запрос в техподдержку
     */
    private static void createSupportRequest() {
        System.out.println("Введите описание вашего запроса:");
        String description = scanner.nextLine();
        String orderId = "REQ-" + System.currentTimeMillis();

        TechSupportOrder newOrder = new TechSupportOrder(orderId, description, true, false, false);
        TechSupportOrder.addRegistry(newOrder);

        System.out.println("Ваш запрос создан с ID: " + orderId);
    }

    /**
     * Просмотреть все созданные запросы
     */
    private static void viewSupportRequests() {
        System.out.println("Ваши запросы:");
        boolean hasRequests = false;

        for (TechSupportOrder order : TechSupportOrder.techSupportOrderRegistry) {
            System.out.println("ID: " + order.getOrderId());
            System.out.println("Описание: " + order.getDescription());
            System.out.println("Статус: " + getOrderStatus(order));
            System.out.println();
            hasRequests = true;
        }

        if (!hasRequests) {
            System.out.println("У вас нет активных запросов.");
        }
    }

    /**
     * Закрыть запрос (статус = Done)
     */
    private static void closeSupportRequest() {
        System.out.println("Введите ID запроса, который хотите закрыть:");
        String orderId = scanner.nextLine();

        TechSupportOrder order = TechSupportOrder.findRegistry(orderId);
        if (order != null) {
            if (!order.isDone()) {
                order.setDone(true);
                order.setAccepted(false);
                order.setNew(false);
                System.out.println("Запрос с ID " + orderId + " закрыт.");
            } else {
                System.out.println("Этот запрос уже завершён.");
            }
        } else {
            System.out.println("Запрос с таким ID не найден.");
        }
    }

    /**
     * Получить строковое описание статуса
     */
    private static String getOrderStatus(TechSupportOrder order) {
        if (order.isDone()) return "Завершён";
        if (order.isAccepted()) return "Принят";
        if (order.isNew()) return "Новый";
        return "Неизвестный статус";
    }
}
