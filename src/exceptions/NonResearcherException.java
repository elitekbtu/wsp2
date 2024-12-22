package exceptions;

public class NonResearcherException extends Exception {
    // Exception when a non-researcher tries to join a research project
    public NonResearcherException(String description){
        super(description);
    }
}
