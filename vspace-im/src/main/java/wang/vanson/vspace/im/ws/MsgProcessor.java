package wang.vanson.vspace.im.ws;

import com.alibaba.fastjson.JSONObject;
import org.tio.core.ChannelContext;
import org.tio.websocket.common.WsRequest;
import wang.vanson.vspace.im.dto.DataType;

public abstract class MsgProcessor {

    public MsgProcessor(MsgProcessorFactory msgProcessorFactory) {
        msgProcessorFactory.add(dataType().getType(), this);
    }

    /**
     * 设置消息类型
     * @return DataType
     */
    public abstract DataType dataType();

    /**
     * 处理文本消息
     * @param wsRequest WsRequest
     * @param jsonObject 消息
     * @param channelContext ChannelContext
     * @return 任何对象，null-不返回数据
     */
    public Object onText(WsRequest wsRequest, JSONObject jsonObject, ChannelContext channelContext) {
        return null;
    }

    /**
     * 处理二进制消息
     * @param wsRequest WsRequest
     * @param bytes 消息
     * @param channelContext ChannelContext
     * @return 任何对象，null-不返回数据
     */
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) {
        return null;
    }
}
