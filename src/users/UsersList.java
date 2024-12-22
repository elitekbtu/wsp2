package users;

import users.employees.Admin;
import users.employees.Manager;
import users.employees.Teacher;
import users.employees.TechSupportSpecialist;
import users.students.Student;
import users.students.UndergraduateStudent;

import java.io.*;
import java.util.Scanner;

public class UsersList {

    private Scanner scanner;
    private boolean isRussian;

    /**
     * Конструктор, принимающий флаг языка (true = русский, false = английский).
     */
    public UsersList(boolean isRussian) {
        this.scanner = new Scanner(System.in);
        this.isRussian = isRussian;
    }

    /**
     * Если в вашем проекте где-то по умолчанию вызывается UsersList без флага,
     * можно завести ещё один конструктор по умолчанию:
     */
    public UsersList() {
        this(false); // По умолчанию - английский, или на ваш выбор
    }

    /**
     * Запуск процесса регистрации: выбор типа пользователя
     */
    public void register() {
        System.out.println(isRussian
                ? "Добро пожаловать в регистрацию"
                : "Welcome to registration");
        System.out.println(isRussian
                ? "Выберите опцию:"
                : "Choose an option:");
        System.out.println(isRussian
                ? "1. Студент  2. Преподаватель  3. Админ\n4. Техподдержка  5. Менеджер"
                : "1. Student  2. Teacher  3. Admin\n4. TechSupport  5. Manager");

        int choice = scanner.nextInt();
        scanner.nextLine(); // очистка буфера

        switch (choice) {
            case 1:
                registerStudent();
                break;
            case 2:
                registerTeacher();
                break;
            case 3:
                registerAdmin();
                break;
            case 4:
                registerTechSupport();
                break;
            case 5:
                registerManager();
                break;
            default:
                System.out.println(isRussian
                        ? "Неверный выбор. Попробуйте снова."
                        : "Invalid choice. Try again.");
        }
    }

    /**
     * Регистрация студента
     */
    private void registerStudent() {
        System.out.println(isRussian ? "Введите ID студента:" : "Enter student ID:");
        String id = scanner.nextLine();
        System.out.println(isRussian ? "Введите полное имя студента:" : "Enter student's full name:");
        String fullname = scanner.nextLine();
        System.out.println(isRussian ? "Введите email студента:" : "Enter student's email:");
        String email = scanner.nextLine();
        System.out.println(isRussian ? "Введите пароль студента:" : "Enter student's password:");
        String password = scanner.nextLine();

        Student student = new UndergraduateStudent(id, fullname, email, password);
        saveUserData(student);

        System.out.println(isRussian
                ? "Студент зарегистрирован успешно!"
                : "Student registered successfully!");
    }

    /**
     * Регистрация учителя
     */
    private void registerTeacher() {
        System.out.println(isRussian ? "Введите ID учителя:" : "Enter teacher ID:");
        String id = scanner.nextLine();
        System.out.println(isRussian ? "Введите полное имя учителя:" : "Enter teacher's full name:");
        String fullname = scanner.nextLine();
        System.out.println(isRussian ? "Введите email учителя:" : "Enter teacher's email:");
        String email = scanner.nextLine();
        System.out.println(isRussian ? "Введите пароль учителя:" : "Enter teacher's password:");
        String password = scanner.nextLine();

        // Допустим, у учителя есть поле "department"
        Teacher teacher = new Teacher(id, fullname, email, password, "Department");
        saveUserData(teacher);

        System.out.println(isRussian
                ? "Учитель зарегистрирован успешно!"
                : "Teacher registered successfully!");
    }

    /**
     * Регистрация админа
     */
    private void registerAdmin() {
        System.out.println(isRussian ? "Введите ID администратора:" : "Enter admin ID:");
        String id = scanner.nextLine();
        System.out.println(isRussian ? "Введите полное имя администратора:" : "Enter admin's full name:");
        String fullname = scanner.nextLine();
        System.out.println(isRussian ? "Введите email администратора:" : "Enter admin's email:");
        String email = scanner.nextLine();
        System.out.println(isRussian ? "Введите пароль администратора:" : "Enter admin's password:");
        String password = scanner.nextLine();

        Admin admin = new Admin(id, fullname, email, password);
        saveUserData(admin);

        System.out.println(isRussian
                ? "Администратор зарегистрирован успешно!"
                : "Admin registered successfully!");
    }

    /**
     * Регистрация сотрудника техподдержки
     */
    private void registerTechSupport() {
        System.out.println(isRussian
                ? "Введите ID сотрудника техподдержки:"
                : "Enter Tech Support Specialist ID:");
        String id = scanner.nextLine();
        System.out.println(isRussian
                ? "Введите полное имя сотрудника:"
                : "Enter specialist's full name:");
        String fullname = scanner.nextLine();
        System.out.println(isRussian
                ? "Введите email сотрудника:"
                : "Enter specialist's email:");
        String email = scanner.nextLine();
        System.out.println(isRussian
                ? "Введите пароль сотрудника:"
                : "Enter specialist's password:");
        String password = scanner.nextLine();

        TechSupportSpecialist techSupport = new TechSupportSpecialist(id, fullname, email, password);
        saveUserData(techSupport);

        System.out.println(isRussian
                ? "Сотрудник технической поддержки зарегистрирован успешно!"
                : "Tech Support Specialist registered successfully!");
    }

    /**
     * Регистрация менеджера
     */
    private void registerManager() {
        System.out.println(isRussian
                ? "Введите ID менеджера:"
                : "Enter manager ID:");
        String id = scanner.nextLine();
        System.out.println(isRussian
                ? "Введите полное имя менеджера:"
                : "Enter manager's full name:");
        String fullname = scanner.nextLine();
        System.out.println(isRussian
                ? "Введите email менеджера:"
                : "Enter manager's email:");
        String email = scanner.nextLine();
        System.out.println(isRussian
                ? "Введите пароль менеджера:"
                : "Enter manager's password:");
        String password = scanner.nextLine();

        // Предположим, у менеджера есть поле "department" = "Management"
        Manager manager = new Manager(id, fullname, email, password, "Management");
        saveUserData(manager);

        System.out.println(isRussian
                ? "Менеджер зарегистрирован успешно!"
                : "Manager registered successfully!");
    }

    /**
     * Сохранение данных пользователя в файл:
     * users/UsersData/ТипПользователя/ID.txt
     */
    private void saveUserData(User user) {
        String userType = user.getClass().getSimpleName();
        // Например: "UndergraduateStudent", "Teacher", "Manager" и т.д.

        String directoryPath = "users/UsersData/" + userType;
        File directory = new File(directoryPath);

        // Создаём папку, если не существует
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                // Можно вывести сообщение, но только если нужно
            } else {
                System.out.println(isRussian
                        ? "Ошибка создания директории: " + directoryPath
                        : "Failed to create directory: " + directoryPath);
                return;
            }
        }

        // Название файла: ID.txt
        String fileName = directoryPath + "/" + user.getId() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Запишем ID:password
            writer.write(user.getId() + ":" + user.getPassword());
            writer.newLine();
            writer.write((isRussian ? "Полное имя: " : "Full name: ") + user.getFullname());
            writer.newLine();
            writer.write((isRussian ? "Email: " : "Email: ") + user.getEmail());
            // Если нужно, добавьте ещё какие-то поля
        } catch (IOException e) {
            System.out.println(isRussian
                    ? "Ошибка записи файла: " + e.getMessage()
                    : "Error writing file: " + e.getMessage());
        }
    }

    /**
     * Загрузка (чтение) данных пользователя из локального файла
     */
    public void loadUserData() {
        System.out.println(isRussian ? "Введите ID:" : "Enter ID:");
        String id = scanner.nextLine();

        System.out.println(isRussian
                ? "Выберите свой тип:\n1. Студент  2. Преподаватель  3. Админ\n4. Техподдержка  5. Менеджер"
                : "Choose your type:\n1. Student  2. Teacher  3. Admin\n4. TechSupport  5. Manager");

        int choice = scanner.nextInt();
        scanner.nextLine();

        File file = null;

        switch (choice) {
            case 1:
                // У вас может быть так, что студент всегда "UndergraduateStudent"
                file = new File("users/UsersData/UndergraduateStudent/" + id + ".txt");
                break;
            case 2:
                file = new File("users/UsersData/Teacher/" + id + ".txt");
                break;
            case 3:
                file = new File("users/UsersData/Admin/" + id + ".txt");
                break;
            case 4:
                file = new File("users/UsersData/TechSupportSpecialist/" + id + ".txt");
                break;
            case 5:
                file = new File("users/UsersData/Manager/" + id + ".txt");
                break;
            default:
                System.out.println(isRussian
                        ? "Неверный выбор типа пользователя."
                        : "Invalid user type choice.");
                return;
        }

        if (file != null) {
            if (file.exists()) {
                readFile(file);
            } else {
                System.out.println(isRussian
                        ? "Файл пользователя не найден: " + file.getPath()
                        : "User file not found: " + file.getPath());
            }
        }
    }

    /**
     * Прочитать файл и вывести данные пользователя
     */
    private void readFile(File file) {
        try (Scanner fileScanner = new Scanner(file)) {
            System.out.println(isRussian
                    ? "Данные пользователя:"
                    : "User data:");
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(isRussian
                    ? "Ошибка при чтении файла: " + e.getMessage()
                    : "Error reading file: " + e.getMessage());
        }
    }
}
