package results;

import items.UserItem;

/**
 * Created by dycaly on 2016/7/4.
 */
public class UserInfoEditRlt {

    private int state;
    private String reason;
    private UserItem userItem;

    public UserInfoEditRlt(int state, String reason, UserItem userItem) {
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
