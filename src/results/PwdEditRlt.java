package results;

/**
 * Created by dycaly on 2016/7/3.
 */
public class PwdEditRlt {
    private int state;
    private String reason;

    public PwdEditRlt(int state, String reason) {
        this.state = state;
        this.reason = reason;
    }

    public int getState() {
        return state;
    }

    public String getReason() {
        return reason;
    }
}
