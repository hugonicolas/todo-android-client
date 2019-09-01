package network;

public class GenericStatusResponse {
    private int status;
    private String error;

    public GenericStatusResponse(int status, String errors) {
        this.status = status;
        this.error = errors;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
