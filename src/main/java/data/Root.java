package data;

public class Root {
    private Tag response;

    public Root(Tag response) {
        this.response = response;
    }

    public Root() {
    }

    public Tag getResponse() {
        return response;
    }

    public void setResponse(Tag response) {
        this.response = response;
    }
}
