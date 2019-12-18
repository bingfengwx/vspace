package wang.vanson.vspace.im.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MsgDTO implements Serializable {

    private static final long serialVersionUID = -4123161370568240439L;
    private int type;
    private String mid;
    private String from;
    private String username;
    private String to;
    private String sendDate;
    private String sendTime;
    private Date createTime;
    private String groupId;
    private String content;
}
