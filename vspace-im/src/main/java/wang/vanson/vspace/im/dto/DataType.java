package wang.vanson.vspace.im.dto;

/**
 * 消息类型
 * @author vanson
 */
public enum DataType {
    /** 用户列表 */
    ALL_USER_LIST(10),
    /** 新增用户 */
    INCREMENT_USER(11),
    /** 群发消息 */
    GROUP_MSG(20),
    /** 私聊消息 */
    PRIVATE_MSG(21),
    USER_START(30);

    private int type;

    DataType(int i) {
        this.type = i;
    }

    public int getType() {
        return type;
    }

}
