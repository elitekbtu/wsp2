package researcher;

import users.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Researcher extends User {
    private List<ResearchPaper> papers = new ArrayList<>();
    int hIndex;
    private boolean cited = false;

    public Researcher() {}

    public Researcher(String id, String fullname) {
        super(id, fullname);
    }

    public Researcher(String id, String fullname, String email, String password) {
        super(id, fullname, email, password);
    }


    public void printPapers(Comparator<ResearchPaper> comparator) {
        papers.sort(comparator);
        for(ResearchPaper rp : papers){
            System.out.println(rp);
        }
    }


    public void calculateHIndex() {
        hIndex = 0;
        papers.sort(Comparator.comparingInt(ResearchPaper::getCountCitations).reversed());
        for (int i = 0; i < papers.size(); i++) {
            int citations = papers.get(i).getCitations().size();

            if (citations >= i + 1) {
                hIndex = i + 1;
            } else {
                break;
            }
        }
    }

    private List<ResearchPaper> getPapers() {
        return papers;
    }

    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
    }

    public int gethIndex() {
        return hIndex;
    }

    public List<ResearchPaper> publishProject(){
        if(cited){
            System.out.println("Project already cited");
            return null;
        }
        this.cited = true;
        return getPapers();
    }
}
