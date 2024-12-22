import java.io.*;
import java.util.*;

// Подключаем ваши классы (оставьте свои импорты, как у вас в проекте):
import news.NewsList;
import users.UsersList;
import users.employees.TechSupportOrder;
import courses.CourseData.Data;

public class Tester {
    private static final String FILE_NAME = "user_data.txt";

    /**
     * Хранит данные в формате: login -> "пароль:тип"
     * Пример: userDatabase.get("ivan") = "qwerty:Teacher"
     */
    private static HashMap<String, String> userDatabase = new HashMap<>();

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Флаг, определяющий, используем ли русский язык (true) или английский (false).
     */
    private static boolean isRussian = false;

    public static void main(String[] args) {
        chooseLanguage();    // Сначала даём выбрать язык
        loadUserData();

        while (true) {
            // Меню выбора действия (регистрация, вход, выход)
            System.out.println(isRussian
                    ? "\nВыберите действие: 1 - Регистрация, 2 - Вход, 0 - Выход"
                    : "\nChoose an action: 1 - Register, 2 - Login, 0 - Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    if (loginUser()) {
                        // Если логин успешен, попадаем в mainMenu(...)
                    }
                    break;
                case 0:
                    System.out.println(isRussian ? "Программа завершена." : "Program finished.");
                    scanner.close();
                    return;
                default:
                    System.out.println(isRussian ? "Неверный выбор. Попробуйте снова." : "Invalid choice. Try again.");
            }
        }
    }

    /**
     * Метод выбора языка (1 - English, 2 - Russian).
     */
    private static void chooseLanguage() {
        System.out.println("Choose language / Выберите язык:");
        System.out.println("1 - English");
        System.out.println("2 - Русский");

        int langChoice = scanner.nextInt();
        scanner.nextLine(); // очистка буфера
        if (langChoice == 2) {
            isRussian = true;    // Русский
        } else {
            isRussian = false;   // Английский (по умолчанию)
        }
    }

    /**
     * Загрузка данных (логин, пароль, тип) из файла user_data.txt
     */
    private static void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String login = parts[0];
                    String password = parts[1];
                    String userType = parts[2];
                    userDatabase.put(login, password + ":" + userType);
                }
            }
            System.out.println(isRussian ? "Данные пользователей загружены." : "User data loaded.");
        } catch (FileNotFoundException e) {
            System.out.println(isRussian
                    ? "Файл данных не найден. Будет создан новый при регистрации."
                    : "User data file not found. A new file will be created upon registration.");
        } catch (IOException e) {
            System.out.println((isRussian ? "Ошибка чтения файла: " : "Error reading file: ") + e.getMessage());
        }
    }

    /**
     * Сохранение userDatabase обратно в user_data.txt
     */
    private static void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String login : userDatabase.keySet()) {
                String passAndType = userDatabase.get(login);
                String[] parts = passAndType.split(":");
                if (parts.length == 2) {
                    String password = parts[0];
                    String userType = parts[1];
                    writer.write(login + ":" + password + ":" + userType);
                    writer.newLine();
                }
            }
            System.out.println(isRussian ? "Данные пользователей сохранены." : "User data saved.");
        } catch (IOException e) {
            System.out.println(isRussian
                    ? "Ошибка записи файла: " + e.getMessage()
                    : "Error writing file: " + e.getMessage());
        }
    }

    /**
     * Регистрация нового пользователя
     */
    private static void registerUser() {
        System.out.println(isRussian ? "Введите логин:" : "Enter login:");
        String registerLogin = scanner.nextLine();

        if (userDatabase.containsKey(registerLogin)) {
            System.out.println(isRussian ? "Логин уже существует. Попробуйте другой." : "Login already exists. Try another.");
        } else {
            System.out.println(isRussian ? "Введите пароль:" : "Enter password:");
            String registerPassword = scanner.nextLine();

            // Выбираем тип пользователя
            System.out.println(isRussian
                    ? "Выберите тип пользователя:\n1 - Студент\n2 - Преподаватель\n3 - Менеджер\n4 - Админ\n5 - Техподдержка"
                    : "Choose user type:\n1 - Student\n2 - Teacher\n3 - Manager\n4 - Admin\n5 - TechSupportSpecialist");
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
                    System.out.println(isRussian
                            ? "Неверный выбор. Тип по умолчанию: Студент."
                            : "Invalid choice. Default user type: Student.");
                    userType = "Student";
            }

            // Сохраняем
            userDatabase.put(registerLogin, registerPassword + ":" + userType);
            saveUserData();

            // Дополнительно вызываем UsersList для детальной регистрации (ID, fullname, email)
            UsersList usersList = new UsersList(isRussian); // Передадим флаг языка в конструктор (если нужно)
            usersList.register();

            System.out.println(isRussian ? "Регистрация успешна!" : "Registration successful!");
        }
    }

    /**
     * Авторизация
     */
    private static boolean loginUser() {
        System.out.println(isRussian ? "Введите логин:" : "Enter login:");
        String login = scanner.nextLine();
        System.out.println(isRussian ? "Введите пароль:" : "Enter password:");
        String password = scanner.nextLine();

        if (userDatabase.containsKey(login)) {
            String passAndType = userDatabase.get(login); // "пароль:тип"
            String[] parts = passAndType.split(":");
            if (parts.length == 2) {
                String storedPassword = parts[0];
                String userType = parts[1];
                if (storedPassword.equals(password)) {
                    System.out.println(isRussian
                            ? "Вход выполнен успешно! Добро пожаловать, " + login + "!"
                            : "Login successful! Welcome, " + login + "!");
                    mainMenu(userType);
                    return true;
                }
            }
        }

        System.out.println(isRussian ? "Ошибка входа. Проверьте логин и пароль." : "Login error. Check login and password.");
        return false;
    }

    /**
     * Главное меню с учётом типа пользователя
     */
    private static void mainMenu(String userType) {
        while (true) {
            System.out.println(isRussian
                    ? "\nГлавное меню:\n1 - Новостная лента\n2 - Данные пользователя\n3 - Сформировать и прочитать транскрипт\n4 - Курсы\n5 - Техническая поддержка\n6 - Выйти"
                    : "\nMain menu:\n1 - News feed\n2 - User data\n3 - Generate & read transcript\n4 - Courses\n5 - Tech support\n6 - Exit");

            // Если Teacher / Manager / Admin / TechSupportSpecialist — добавим 7
            if ("Teacher".equalsIgnoreCase(userType)) {
                System.out.println(isRussian ? "7 - Поставить оценку студенту" : "7 - Set grade for student");
            } else if ("Manager".equalsIgnoreCase(userType)) {
                System.out.println(isRussian ? "7 - Финансовый кабинет" : "7 - Finance cabinet");
            } else if ("Admin".equalsIgnoreCase(userType)) {
                System.out.println(isRussian ? "7 - Удалить пользователя" : "7 - Remove user");
            } else if ("TechSupportSpecialist".equalsIgnoreCase(userType)) {
                System.out.println(isRussian ? "7 - Решить проблему пользователя" : "7 - Solve user issue");
            }

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(isRussian ? "Некорректный ввод. Попробуйте снова." : "Invalid input. Try again.");
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
                    System.out.println(isRussian
                            ? "Введите логин пользователя (для которого формируем транскрипт):"
                            : "Enter the user login (for whom we generate transcript):");
                    String studentName = scanner.nextLine();
                    transcript(studentName);
                    break;
                case 4:
                    System.out.println(isRussian
                            ? "Введите логин пользователя (для сохранения курсов):"
                            : "Enter the user login (for course registration):");
                    String usernameForCourses = scanner.nextLine();
                    registerForCourses(usernameForCourses);
                    break;
                case 5:
                    technicalSupport();
                    break;
                case 6:
                    System.out.println(isRussian ? "Выход из меню." : "Exiting menu.");
                    return;
                case 7:
                    if ("Teacher".equalsIgnoreCase(userType)) {
                        setGradeMenu();
                    } else if ("Manager".equalsIgnoreCase(userType)) {
                        financeMenu();
                    } else if ("Admin".equalsIgnoreCase(userType)) {
                        adminMenu();
                    } else if ("TechSupportSpecialist".equalsIgnoreCase(userType)) {
                        solveIssueMenu();
                    } else {
                        System.out.println(isRussian
                                ? "Недоступный пункт меню."
                                : "Menu item not available.");
                    }
                    break;
                default:
                    System.out.println(isRussian
                            ? "Неверный выбор. Попробуйте снова."
                            : "Invalid choice. Try again.");
            }
        }
    }

    /**
     * Пример для Teacher
     */
    private static void setGradeMenu() {
        System.out.println(isRussian ? "Введите ID студента для выставления оценки:" : "Enter student ID to set grade:");
        String studentId = scanner.nextLine();
        System.out.println(isRussian ? "Введите оценку (число):" : "Enter grade (number):");
        String grade = scanner.nextLine();
        // Пример логики
        System.out.println(isRussian
                ? "Учитель поставил студенту " + studentId + " оценку: " + grade
                : "Teacher set grade " + grade + " for student " + studentId);
    }

    /**
     * Пример: Финансовый кабинет (Manager)
     * Считает сумму обучения на основе ECTS * стоимость 1 кредита
     */
    private static void financeMenu() {
        System.out.println(isRussian
                ? "=== Финансовый кабинет ==="
                : "=== Finance Cabinet ===");

        System.out.println(isRussian
                ? "Введите логин студента, для которого считаем оплату:"
                : "Enter student login to calculate tuition:");
        String studentLogin = scanner.nextLine().trim();

        System.out.println(isRussian
                ? "Введите стоимость одного кредита (например, 5000):"
                : "Enter cost per credit (e.g., 5000):");
        double costPerCredit;
        try {
            costPerCredit = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(isRussian
                    ? "Некорректная стоимость. Выходим."
                    : "Invalid cost. Exiting.");
            return;
        }

        String coursesFile = studentLogin.replaceAll("\\s+", "_") + "_courses.txt";
        File file = new File(coursesFile);
        if (!file.exists()) {
            System.out.println(isRussian
                    ? "Файл с курсами не найден: " + coursesFile
                    : "Courses file not found: " + coursesFile);
            return;
        }

        int totalEcts = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue;
                try {
                    // Вытаскиваем ECTS из строки вида: (6 ECTS)
                    String ectsStr = line.replaceAll(".*\\((\\d+) ECTS\\).*", "$1");
                    int ects = Integer.parseInt(ectsStr);
                    totalEcts += ects;
                } catch (NumberFormatException ex) {
                    // пропускаем
                }
            }
        } catch (IOException e) {
            System.out.println(isRussian
                    ? "Ошибка чтения файла курсов: " + e.getMessage()
                    : "Error reading courses file: " + e.getMessage());
            return;
        }

        if (totalEcts == 0) {
            System.out.println(isRussian
                    ? "У студента нет курсов или ECTS = 0."
                    : "Student has no courses or ECTS = 0.");
            return;
        }

        double totalCost = costPerCredit * totalEcts;
        System.out.println(isRussian
                ? "Общее количество кредитов: " + totalEcts
                + "\nСтоимость одного кредита: " + costPerCredit
                + "\nИтоговая сумма за обучение: " + totalCost
                : "Total ECTS: " + totalEcts
                + "\nCost per credit: " + costPerCredit
                + "\nTuition total cost: " + totalCost);
    }

    /**
     * Администратор: удалить пользователя
     */
    private static void adminMenu() {
        System.out.println(isRussian
                ? "Введите логин пользователя, которого хотите удалить:"
                : "Enter the login of the user to remove:");
        String loginToDelete = scanner.nextLine();
        if (userDatabase.containsKey(loginToDelete)) {
            userDatabase.remove(loginToDelete);
            saveUserData();
            System.out.println(isRussian
                    ? "Пользователь " + loginToDelete + " успешно удалён."
                    : "User " + loginToDelete + " removed successfully.");
        } else {
            System.out.println(isRussian
                    ? "Пользователь " + loginToDelete + " не найден."
                    : "User " + loginToDelete + " not found.");
        }
    }

    /**
     * Техподдержка (TechSupportSpecialist)
     */
    private static void solveIssueMenu() {
        System.out.println(isRussian
                ? "Введите ID запроса техподдержки, который хотите решить:"
                : "Enter the tech support ticket ID to solve:");
        String ticketId = scanner.nextLine();

        TechSupportOrder order = TechSupportOrder.findRegistry(ticketId);
        if (order == null) {
            System.out.println(isRussian
                    ? "Запрос с таким ID не найден."
                    : "Ticket with that ID not found.");
            return;
        }
        if (order.isDone()) {
            System.out.println(isRussian
                    ? "Этот запрос уже завершён."
                    : "This ticket is already done.");
            return;
        }

        order.setDone(true);
        order.setAccepted(false);
        order.setNew(false);
        System.out.println(isRussian
                ? "Запрос " + ticketId + " отмечен как решённый."
                : "Ticket " + ticketId + " marked as solved.");
    }

    /* ================================
        Общие методы для всех ролей
       ================================ */

    /**
     * Новостная лента
     */
    private static void newsFeed() {
        System.out.println(isRussian
                ? "Вы выбрали новостную ленту."
                : "You chose the news feed.");
        NewsList newsList = new NewsList();

        Random random = new Random();
        int count = 5;
        System.out.println(isRussian
                ? "Последние новости:"
                : "Latest news:");

        for (int i = 0; i < count; i++) {
            int index = random.nextInt(newsList.news.length);
            System.out.println((i + 1) + ". " + newsList.news[index]);
        }
    }

    /**
     * Данные пользователя (студента/сотрудника)
     */
    private static void userData() {
        System.out.println(isRussian
                ? "Вы выбрали просмотр данных пользователя."
                : "You chose to view user data.");
        UsersList usersList = new UsersList(isRussian);
        usersList.loadUserData();
    }

    /**
     * Генерация транскрипта и сразу чтение
     */
    private static void transcript(String studentName) {
        String inputFileName = studentName.replaceAll("\\s+", "_") + "_courses.txt";
        Map<String, Integer> courses = new LinkedHashMap<>();

        // Чтение курсов
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue;
                // [CSC1103] Programming Principles 1 (6 ECTS)
                int credits;
                try {
                    String ectsStr = line.replaceAll(".*\\((\\d+) ECTS\\).*", "$1");
                    credits = Integer.parseInt(ectsStr);
                } catch (NumberFormatException e) {
                    continue;
                }
                String courseName = line.split("\\(\\d+ ECTS\\)")[0].trim();
                courses.put(courseName, credits);
            }
        } catch (FileNotFoundException e) {
            System.out.println(isRussian
                    ? "Файл с курсами не найден: " + inputFileName
                    : "Courses file not found: " + inputFileName);
            return;
        } catch (IOException e) {
            System.out.println(isRussian
                    ? "Ошибка чтения файла: " + inputFileName
                    : "Error reading file: " + inputFileName);
            e.printStackTrace();
            return;
        }

        if (courses.isEmpty()) {
            System.out.println(isRussian
                    ? "У пользователя " + studentName + " нет курсов."
                    : "No courses found for user " + studentName);
            return;
        }

        // Генерируем оценки, считаем GPA
        Random random = new Random();
        double totalCredits = 0;
        double weightedSum = 0;
        StringBuilder sb = new StringBuilder();

        sb.append(isRussian
                ? "Транскрипт для: " + studentName + "\n"
                : "Transcript for: " + studentName + "\n");
        sb.append(isRussian ? "Оценки по курсам:\n" : "Course Grades:\n");

        for (Map.Entry<String, Integer> entry : courses.entrySet()) {
            String course = entry.getKey();
            int credits = entry.getValue();
            double grade = Math.round(random.nextDouble() * 4.0 * 100) / 100.0;

            sb.append(String.format("%s (%d ECTS): %.2f\n", course, credits, grade));

            totalCredits += credits;
            weightedSum += (grade * credits);
        }

        double gpa = weightedSum / totalCredits;
        sb.append(String.format("\nGPA: %.2f\n", gpa));

        // Записываем в файл
        String transcriptFile = studentName.replaceAll("\\s+", "_") + "_transcript.txt";
        try (FileWriter writer = new FileWriter(transcriptFile)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println(isRussian
                    ? "Ошибка записи транскрипта."
                    : "Error writing transcript.");
            e.printStackTrace();
            return;
        }

        // Сразу читаем и выводим
        try (BufferedReader br = new BufferedReader(new FileReader(transcriptFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println(isRussian
                    ? "Ошибка при чтении транскрипта: " + transcriptFile
                    : "Error reading transcript: " + transcriptFile);
            e.printStackTrace();
        }
    }

    /**
     * Показать и выбрать курсы
     */
    private static void registerForCourses(String username) {
        showAvailableCourses();

        System.out.println(isRussian
                ? "\nВведите номера курсов через запятую (например: 1,3,4):"
                : "\nEnter course numbers separated by commas (e.g., 1,3,4):");
        String input = scanner.nextLine();
        String[] selectedIndexes = input.split(",");

        Data[] courses = Data.getAvailableCourses();
        ArrayList<Data> selectedCourses = new ArrayList<>();

        System.out.println(isRussian
                ? "Вы выбрали следующие курсы:"
                : "You selected the following courses:");

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
                    System.out.println(isRussian
                            ? "Неверный номер курса: " + (index + 1)
                            : "Invalid course number: " + (index + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println(isRussian
                        ? "Некорректный ввод: " + indexStr
                        : "Invalid input: " + indexStr);
            }
        }

        if (!selectedCourses.isEmpty()) {
            saveSelectedCourses(username, selectedCourses.toArray(new Data[0]));
        } else {
            System.out.println(isRussian
                    ? "Курсы не были выбраны."
                    : "No courses were selected.");
        }
    }

    /**
     * Показать доступные курсы
     */
    private static void showAvailableCourses() {
        System.out.println(isRussian
                ? "Доступные курсы:"
                : "Available courses:");
        Data[] courses = Data.getAvailableCourses();
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
     * Сохранить выбранные курсы
     */
    private static void saveSelectedCourses(String username, Data[] selectedCourses) {
        String fileName = username.replaceAll("\\s+", "_") + "_courses.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Data course : selectedCourses) {
                writer.write(String.format("[%s] %s (%d ECTS)%n",
                        course.getCode(),
                        course.getDiscipline(),
                        course.getEcts()));
            }
            System.out.println(isRussian
                    ? "Выбранные курсы сохранены в файл: " + fileName
                    : "Selected courses saved to file: " + fileName);
        } catch (IOException e) {
            System.out.println(isRussian
                    ? "Ошибка записи файла: " + e.getMessage()
                    : "Error writing file: " + e.getMessage());
        }
    }

    /**
     * Меню технической поддержки (общий метод)
     */
    private static void technicalSupport() {
        System.out.println(isRussian
                ? "Вы выбрали техническую поддержку."
                : "You chose Tech Support.");
        while (true) {
            System.out.println(isRussian
                    ? "\n1 - Создать запрос\n2 - Просмотреть запросы\n3 - Закрыть запрос\n4 - Вернуться в главное меню"
                    : "\n1 - Create request\n2 - View requests\n3 - Close request\n4 - Return to main menu");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(isRussian
                        ? "Некорректный ввод. Попробуйте снова."
                        : "Invalid input. Try again.");
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
                    System.out.println(isRussian
                            ? "Возвращаемся в главное меню."
                            : "Returning to main menu.");
                    return;
                default:
                    System.out.println(isRussian
                            ? "Неверный выбор. Попробуйте снова."
                            : "Invalid choice. Try again.");
            }
        }
    }

    /**
     * Создать запрос
     */
    private static void createSupportRequest() {
        System.out.println(isRussian
                ? "Введите описание вашего запроса:"
                : "Enter description of your request:");
        String description = scanner.nextLine();
        String orderId = "REQ-" + System.currentTimeMillis();

        TechSupportOrder newOrder = new TechSupportOrder(orderId, description, true, false, false);
        TechSupportOrder.addRegistry(newOrder);

        System.out.println(isRussian
                ? "Ваш запрос создан с ID: " + orderId
                : "Your request created with ID: " + orderId);
    }

    /**
     * Просмотреть все запросы
     */
    private static void viewSupportRequests() {
        System.out.println(isRussian ? "Ваши запросы:" : "Your requests:");
        boolean hasRequests = false;

        for (TechSupportOrder order : TechSupportOrder.techSupportOrderRegistry) {
            System.out.println(isRussian
                    ? "ID: " + order.getOrderId() + "\nОписание: " + order.getDescription() + "\nСтатус: " + getOrderStatus(order)
                    : "ID: " + order.getOrderId() + "\nDescription: " + order.getDescription() + "\nStatus: " + getOrderStatus(order));
            System.out.println();
            hasRequests = true;
        }

        if (!hasRequests) {
            System.out.println(isRussian
                    ? "У вас нет активных запросов."
                    : "You have no active requests.");
        }
    }

    /**
     * Закрыть запрос
     */
    private static void closeSupportRequest() {
        System.out.println(isRussian
                ? "Введите ID запроса, который хотите закрыть:"
                : "Enter the request ID you want to close:");
        String orderId = scanner.nextLine();

        TechSupportOrder order = TechSupportOrder.findRegistry(orderId);
        if (order != null) {
            if (!order.isDone()) {
                order.setDone(true);
                order.setAccepted(false);
                order.setNew(false);
                System.out.println(isRussian
                        ? "Запрос с ID " + orderId + " закрыт."
                        : "Request with ID " + orderId + " is closed.");
            } else {
                System.out.println(isRussian
                        ? "Этот запрос уже завершён."
                        : "This request is already done.");
            }
        } else {
            System.out.println(isRussian
                    ? "Запрос с таким ID не найден."
                    : "Request with this ID not found.");
        }
    }

    /**
     * Статус запроса
     */
    private static String getOrderStatus(TechSupportOrder order) {
        if (order.isDone()) return isRussian ? "Завершён" : "Done";
        if (order.isAccepted()) return isRussian ? "Принят" : "Accepted";
        if (order.isNew()) return isRussian ? "Новый" : "New";
        return isRussian ? "Неизвестный статус" : "Unknown status";
    }
}
