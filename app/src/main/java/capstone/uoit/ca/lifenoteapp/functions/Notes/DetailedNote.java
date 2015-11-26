package capstone.uoit.ca.lifenoteapp.functions.Notes;

/**
 * Created by ubuntu on 16/11/15.
 */
public class DetailedNote extends Note {
    String textContent;
    String symptomList;


    public String getSymptomList() {
        return symptomList;
    }

    public void setSymptomList(String symptomList) {
        this.symptomList = symptomList;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
