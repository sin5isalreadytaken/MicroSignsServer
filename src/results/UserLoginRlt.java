package results;

import items.UserItem;

/**
 * Created by dycaly on 2016/6/28.
 */
public class UserLoginRlt {
    private int state;
    private String reason;
    private UserItem userItem;

    public UserLoginRlt(int state, String reason, UserItem userItem) {
        this.state = state;
        this.reason = reason;
        this.userItem = userItem;
    }

    public int getState() {
        return state;
    }

    public String getReason() {
        return reason;
    }

    public UserItem getUserItem() {
        return userItem;
    }
}
