package researcher;

import exceptions.NonResearcherException;
import users.User;

import java.util.ArrayList;
import java.util.List;


public class ResearchProject {
    private String topic;
    private List<ResearchPaper> publishedPapers = new ArrayList<>();
    private List<Researcher> participants = new ArrayList<>();

    public ResearchProject() {
    }

    public ResearchProject(String topic, List<ResearchPaper> publishedPapers, List<Researcher> participants) {
        this.topic = topic;
        this.publishedPapers = publishedPapers;
        this.participants = participants;
    }

    public ResearchProject(String topic) {
        this.topic = topic;
    }


    public void addParticipants(User participant) throws NonResearcherException {
        if (participant instanceof Researcher){
            this.participants.add((Researcher) participant);
        }
        else {
            throw new NonResearcherException("only a researcher can be a part of a project");
        }
    }

    // ===== Геттеры / сеттеры =====

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<ResearchPaper> getPublishedPapers() {
        return publishedPapers;
    }

    public void addPublishedPapers(ResearchPaper researchPaper) {
        this.publishedPapers.add(researchPaper);
    }

    public List<Researcher> getParticipants() {
        return participants;
    }

    public void setPublishedPapers(List<ResearchPaper> publishedPapers) {
        this.publishedPapers = publishedPapers;
    }

    public void setParticipants(List<Researcher> participants) {
        this.participants = participants;
    }
}
