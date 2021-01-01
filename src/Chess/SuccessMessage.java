package Chess;



public class SuccessMessage {

    private MoveResult result;
    private String message;

    public SuccessMessage(MoveResult result, String message) {
        this.result = result;
        this.message = message;
    }

    public SuccessMessage() {
        result = MoveResult.SUCCESS;
        message = "";
    }
    public MoveResult getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setResult(MoveResult result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
