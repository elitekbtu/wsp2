package analytics;

import users.employees.Employee;

import java.util.*;

/**
 * Класс, описывающий запрос сотрудника (например, запрос на аналитический отчёт).
 * Для хранения запросов используется статический Set requestRegistry.
 */
public class EmployeeRequest {

    // Хранилище всех созданных запросов
    private static final Set<EmployeeRequest> requestRegistry = new HashSet<>();

    private String requestId;
    private Employee requester;
    private String requestType;
    private String description;
    private Date requestDate;
    private boolean isSignedOff;

    /**
     * Основной конструктор.
     *
     * @param requestId   - уникальный ID запроса (String)
     * @param requester   - сотрудник, создавший запрос (Employee)
     * @param requestType - тип запроса (String, мог бы быть enum)
     * @param description - описание запроса
     */
    public EmployeeRequest(String requestId, Employee requester, String requestType, String description) {
        this.requestId = requestId;
        this.requester = requester;
        this.requestType = requestType;
        this.description = description;
        this.requestDate = new Date(); // по умолчанию — текущая дата
        this.isSignedOff = false;      // по умолчанию не подписано/не закрыто
        requestRegistry.add(this);
    }

    /**
     * Метод, позволяющий «подписать» (закрыть/одобрить) запрос.
     * Можно развивать логику, например, проверять роль сотрудника, кто имеет право и т.д.
     */
    public void signOff() {
        isSignedOff = true;
    }

    /* ==============================
       Статические методы для работы
       с коллекцией запросов
       ============================== */

    /**
     * Найти запрос по requestId.
     * @param requestId - ID запроса
     * @return объект EmployeeRequest или null, если не найден
     */
    public static EmployeeRequest findByRequestId(String requestId) {
        for (EmployeeRequest req : requestRegistry) {
            if (req.getRequestId().equals(requestId)) {
                return req;
            }
        }
        return null;
    }

    /**
     * Возвращает список всех запросов.
     */
    public static List<EmployeeRequest> getAllRequests() {
        return new ArrayList<>(requestRegistry);
    }

    /**
     * Удалить запрос из реестра.
     * @param requestId - ID запроса, который нужно удалить
     */
    public static boolean removeRequest(String requestId) {
        return requestRegistry.removeIf(req -> req.getRequestId().equals(requestId));
    }

    /* ==============================
       Геттеры/сеттеры
       ============================== */

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Employee getRequester() {
        return requester;
    }

    public void setRequester(Employee requester) {
        this.requester = requester;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public boolean isSignedOff() {
        return isSignedOff;
    }

    public void setSignedOff(boolean isSignedOff) {
        this.isSignedOff = isSignedOff;
    }

    /* ==============================
       Переопределённые методы
       ============================== */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeRequest that = (EmployeeRequest) o;
        return Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return requestId != null ? requestId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "EmployeeRequest{" +
                "requestId='" + requestId + '\'' +
                ", requester=" + (requester != null ? requester.getFullName() : "null") +
                ", requestType='" + requestType + '\'' +
                ", description='" + description + '\'' +
                ", requestDate=" + requestDate +
                ", isSignedOff=" + isSignedOff +
                '}';
    }
}
