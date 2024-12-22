package analytics;

import java.util.*;

/**
 * Класс, представляющий отчет (Report).
 * Хранение всех отчетов организовано в статическом Set reportRegistry.
 */
public class Report {
    private static final Set<Report> reportRegistry = new HashSet<>();
    private static int reportCounter = 1;

    private int reportId;
    private String title;
    private Date creationDate;
    private List<String> contents;

    /**
     * Базовый конструктор, принимает только заголовок отчёта.
     * Присваивает уникальный reportId и добавляет в registry.
     */
    public Report(String title) {
        this.reportId = reportCounter++;
        this.title = title;
        this.creationDate = new Date();
        this.contents = new ArrayList<>();
        reportRegistry.add(this);
    }

    /**
     * Дополнительный конструктор, позволяющий сразу указать контент (contents).
     */
    public Report(String title, String... contents) {
        this(title); // вызываем предыдущий конструктор
        this.contents.addAll(Arrays.asList(contents));
    }

    /**
     * Метод, позволяющий добавить одну строку в контент отчёта.
     */
    public void addContent(String content) {
        contents.add(content);
    }

    /**
     * Возвращает полный текст отчёта как одну строку.
     * (Для более сложных случаев можно формировать форматированный вывод.)
     */
    public String getFullReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Report #").append(reportId).append(" ===\n");
        sb.append("Title: ").append(title).append("\n");
        sb.append("Created on: ").append(creationDate).append("\n");
        sb.append("Contents:\n");

        for (String line : contents) {
            sb.append(" - ").append(line).append("\n");
        }
        return sb.toString();
    }

    /* ==============================
       Статические методы для работы
       с коллекцией отчётов
       ============================== */

    /**
     * Найти отчет по reportId.
     */
    public static Report findByReportId(int reportId) {
        for (Report r : reportRegistry) {
            if (r.getReportId() == reportId) {
                return r;
            }
        }
        return null;
    }

    /**
     * Возвращает список всех отчетов.
     */
    public static List<Report> getAllReports() {
        return new ArrayList<>(reportRegistry);
    }

    /**
     * Удалить отчет из реестра по reportId.
     */
    public static boolean removeReport(int reportId) {
        return reportRegistry.removeIf(r -> r.getReportId() == reportId);
    }

    /* ==============================
       Геттеры/сеттеры
       ============================== */

    public int getReportId() {
        return reportId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    /* ==============================
       Переопределённые методы
       ============================== */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Report report = (Report) o;
        return reportId == report.reportId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId);
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", title='" + title + '\'' +
                ", creationDate=" + creationDate +
                ", contents=" + contents +
                '}';
    }
}
