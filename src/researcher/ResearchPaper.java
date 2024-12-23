package researcher;

import enums.Format;
import enums.NewsTags;
import news.News;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ResearchPaper {
    private String title;
    private List<Researcher> authors = new ArrayList<>();
    private String journal;
    private int pages;
    private Date publicationDate;
    private String doi;
    private ArrayList<String> citations = new ArrayList<>();

    public ResearchPaper() {}

    // Доп. конструктор (без Date) - не используем в данном варианте
    public ResearchPaper(String title, String journal, int pages, String doi) {
        this.title = title;
        this.journal = journal;
        this.pages = pages;
        this.doi = doi;
        this.publicationDate = new Date();
    }

    /**
     * Конструктор, который используем в Tester:
     */
    public ResearchPaper(String title, String journal, int pages, Date publicationDate, String doi) {
        this.title = title;
        this.journal = journal;
        this.pages = pages;
        this.publicationDate = publicationDate;
        this.doi = doi;
    }

    public ResearchPaper(String title,
                         List<Researcher> authors,
                         String journal,
                         int pages,
                         Date publicationDate,
                         String doi,
                         ArrayList<String> citations) {
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.pages = pages;
        this.publicationDate = publicationDate;
        this.doi = doi;
        this.citations = citations;
    }

    public String getCitation(Format format) {
        String s = "";
        if(format == Format.PLAIN_TEXT) {
            for (Researcher r : authors) {
                s += r.getName() + ", ";
            }
            s += "\"" + title + "\" ";
            s += publicationDate.getYear() + ", ";
            s += "pp. " + pages;
            s += ", doi: " + doi;
        }
        else {
            s += "author={";
            for (Researcher r : authors) {
                s += r.getName() + ", ";
            }
            s += "},\n";
            s += "title={" + title + "},\n";
            s += "year={" + publicationDate.getYear() + "},\n";
            s += "pages={" + pages + "},\n";
            s += "doi={" + doi +"}";
        }
        return s;
    }


    public static void publication(ResearchPaper p){
        News.addNews(new News(p.getTitle(), p.getJournal(), NewsTags.RESEARCH));
    }

    public int getCountCitations(){
        return citations.size();
    }

    public void addCitation(String c){
        citations.add(c);
    }

    // ===== Геттеры / сеттеры =====

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Researcher> getAuthors() {
        return authors;
    }

    public void addAuthor(Researcher researcher) {
        authors.add(researcher);
    }

    public void setAuthors(List<Researcher> authors) {
        this.authors = authors;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public ArrayList<String> getCitations() {
        return citations;
    }

    public void setCitations(ArrayList<String> citations) {
        this.citations = citations;
    }
}
