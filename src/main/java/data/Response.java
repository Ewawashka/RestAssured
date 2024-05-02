package data;

import javax.xml.bind.annotation.XmlElement;

public class Response {
    private Result result;
    private String stid;

    @XmlElement(name = "result")
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @XmlElement(name = "stid")
    public String getStid() {
        return stid;
    }

    public void setStid(String stid) {
        this.stid = stid;
    }
}
