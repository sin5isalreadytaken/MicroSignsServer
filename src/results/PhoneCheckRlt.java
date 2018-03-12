package results;

/**
 * Created by dycaly on 2016/6/28.
 */
public class PhoneCheckRlt {
    private int state;
    private String reason;
    private String code;

    public PhoneCheckRlt(int state, String reason, String code) {
        this.state = state;
        this.reason = reason;
        this.code = code;
    }

    public int getState() {
        return state;
    }

    public String getReason() {
        return reason;
    }

    public String getCode() {
        return code;
    }
}
