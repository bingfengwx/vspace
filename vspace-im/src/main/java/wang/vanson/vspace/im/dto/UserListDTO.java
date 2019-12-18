package wang.vanson.vspace.im.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户列表消息
 * @author vanson
 */
@Data
public class UserListDTO implements Serializable {
    private static final long serialVersionUID = -8957331560989535808L;
    private int type;
    private List<String> users;

    public void addUser(String userId) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(userId);
    }
}
