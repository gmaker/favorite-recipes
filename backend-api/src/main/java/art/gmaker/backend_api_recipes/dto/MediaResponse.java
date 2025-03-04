package art.gmaker.backend_api_recipes.dto;

public class MediaResponse {
    private String url;

    public MediaResponse(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
}
