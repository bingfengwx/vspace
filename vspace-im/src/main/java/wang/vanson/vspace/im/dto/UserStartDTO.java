package wang.vanson.vspace.im.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserStartDTO implements Serializable {
    private static final long serialVersionUID = -134247270161754729L;
    private int type;
    private String uid;
    private String spaceName;
    private String username;
}
