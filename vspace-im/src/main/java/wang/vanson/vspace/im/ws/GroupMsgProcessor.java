package wang.vanson.vspace.im.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import wang.vanson.vspace.im.dto.DataType;
import wang.vanson.vspace.im.dto.MsgDTO;
import wang.vanson.vspace.im.util.CharsetUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * 群消息处理器
 */
@Component
public class GroupMsgProcessor extends MsgProcessor {
    public GroupMsgProcessor(MsgProcessorFactory msgProcessorFactory) {
        super(msgProcessorFactory);
    }

    @Override
    public DataType dataType() {
        return DataType.GROUP_MSG;
    }

    @Override
    public Object onText(WsRequest wsRequest, JSONObject jsonObject, ChannelContext channelContext) {
        LocalDateTime localDateTime = LocalDateTime.now();
        MsgDTO msgDTO = jsonObject.toJavaObject(MsgDTO.class);
        msgDTO.setMid(UUID.randomUUID().toString());
        msgDTO.setUsername(msgDTO.getFrom());
        msgDTO.setCreateTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        msgDTO.setSendDate(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        msgDTO.setSendTime(localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        String msgStr = JSON.toJSONString(msgDTO);
        Tio.sendToGroup(channelContext.getTioConfig(), msgDTO.getGroupId(), WsResponse.fromText(msgStr, CharsetUtil.UTF_8));
        return null;
    }
}
