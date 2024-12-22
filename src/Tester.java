import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;
import news.NewsList;
import users.UsersList;
import users.employees.TechSupportOrder;
import courses.CourseData.Data;

public class Tester {
    private static final String FILE_NAME = "user_data.txt";
    private static HashMap<String, String> userDatabase = new HashMap<>(); // Логины и пароли в памяти
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        loadUserData();
        while (true) {
            System.out.println("\nВыберите действие: 1 - Регистрация, 2 - Вход, 0 - Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser ();
                    break;
                case 2:
                    if (loginUser ()) {
                        mainMenu();
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

    private static void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2); // Формат логин:пароль
                if (parts.length == 2) {
                    userDatabase.put(parts[0], parts[1]);
                }
            }
            System.out.println("Данные пользователей загружены.");
        } catch (FileNotFoundException e) {
            System.out.println("Файл данных не найден. Будет создан новый.");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private static void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String login : userDatabase.keySet()) {
                String password = userDatabase.get(login);
                writer.write(login + ":" + password);
                writer.newLine();
            }
            System.out.println("Данные пользователей сохранены.");
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }

    private static void registerUser () {
        System.out.println("Введите логин:");
        String registerLogin = scanner.nextLine();

        if (userDatabase.containsKey(registerLogin)) {
            System.out.println("Логин уже существует. Попробуйте другой.");
        } else {
            System.out.println("Введите пароль:");
            String registerPassword = scanner.nextLine();
            userDatabase.put(registerLogin, registerPassword); // Сохранение логина и пароля
            saveUserData(); // Сохраняем данные в файл
            UsersList usersList = new UsersList();
            usersList.register();
            System.out.println("Регистрация успешна!");
        }
    }

    private static boolean loginUser () {
        System.out.println("Введите логин:");
        String login = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        if (userDatabase.containsKey(login) && userDatabase.get(login).equals(password)) {
            System.out.println("Вход выполнен успешно! Добро пожаловать, " + login + "!");
            return true;
        } else {
            System.out.println("Ошибка входа. Проверьте логин и пароль.");
            return false;
        }
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\nГлавное меню:");
            System.out.println("1 - Новостная лента");
            System.out.println("2 - Данные студента");
            System.out.println("3 - Транскрипт");
            System.out.println("4 - Курсы студента");
            System.out.println("5 - Техническая поддержка");
            System.out.println("6 - Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    newsFeed();
                    break;
                case 2:
                    studentData();
                    break;
                case 3:
                    transcript();
                    break;
                case 4:
                    System.out.println("Введите имя пользователя для сохранения курсов:");
                    String username = scanner.nextLine();
                    registerForCourses(username);
                    break;
                case 5:
                    technicalSupport();
                    break;
                case 6:
                    System.out.println("Выход из меню.");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

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


    private static void studentData() {
        System.out.println("Вы выбрали данные студента.");
        UsersList usersList = new UsersList();
        usersList.loadUserData();
    }

    private static void transcript() {

    }
    private static void showAvailableCourses() {
        System.out.println("Доступные курсы:");
        Data[] courses = Data.getAvailableCourses(); // Получаем курсы через метод
        for (int i = 0; i < courses.length; i++) {
            Data course = courses[i];
            System.out.printf("%d. [%s] %s (%d ECTS)%n", i + 1, course.getCode(), course.getDiscipline(), course.getEcts());
        }
    }


    private static void saveSelectedCourses(String username, Data[] selectedCourses) {
        // Путь файла теперь просто имя файла в текущей директории
        String fileName = username + "_courses.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Data course : selectedCourses) {
                writer.write(String.format("[%s] %s (%d ECTS)%n", course.getCode(), course.getDiscipline(), course.getEcts()));
            }
            System.out.println("Выбранные курсы сохранены в файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }

    private static void technicalSupport() {
        System.out.println("Вы выбрали техническую поддержку.");
        while (true) {
            System.out.println("\n1 - Создать запрос\n2 - Просмотреть запросы\n3 - Закрыть запрос\n4 - Вернуться в главное меню");
            int choice = scanner.nextInt();
            scanner.nextLine();

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
    private static void createSupportRequest() {
        System.out.println("Введите описание вашего запроса:");
        String description = scanner.nextLine();
        String orderId = "REQ-" + System.currentTimeMillis();


        TechSupportOrder newOrder = new TechSupportOrder(orderId, description, true, false, false);
        TechSupportOrder.addRegistry(newOrder);

        System.out.println("Ваш запрос создан с ID: " + orderId);
    }

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

    private static String getOrderStatus(TechSupportOrder order) {
        if (order.isDone()) return "Завершён";
        if (order.isAccepted()) return "Принят";
        if (order.isNew()) return "Новый";
        return "Неизвестный статус";
    }
    private static void registerForCourses(String username) {
        showAvailableCourses();

        System.out.println("\nВведите номера курсов через запятую (например: 1,3,4):");
        String input = scanner.nextLine();
        String[] selectedIndexes = input.split(",");

        Data[] courses = Data.getAvailableCourses();
        ArrayList<Data> selectedCourses = new ArrayList<>();

        System.out.println("Вы выбрали следующие курсы:");
        for (String indexStr : selectedIndexes) {
            try {
                int index = Integer.parseInt(indexStr.trim()) - 1; // Преобразуем в индекс
                if (index >= 0 && index < courses.length) {
                    Data selectedCourse = courses[index];
                    System.out.printf("[%s] %s (%d ECTS)%n", selectedCourse.getCode(), selectedCourse.getDiscipline(), selectedCourse.getEcts());
                    selectedCourses.add(selectedCourse); // Добавляем курс в список выбранных
                } else {
                    System.out.println("Неверный номер курса: " + (index + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод: " + indexStr);
            }
        }

        // Сохраняем выбранные курсы в файл
        if (!selectedCourses.isEmpty()) {
            saveSelectedCourses(username, selectedCourses.toArray(new Data[0]));
        } else {
            System.out.println("Курсы не были выбраны.");
        }
    }







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
}