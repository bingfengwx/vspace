package wang.vanson.vspace.im.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.utils.page.Page;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;
import wang.vanson.vspace.im.dto.DataType;
import wang.vanson.vspace.im.dto.MsgDTO;
import wang.vanson.vspace.im.dto.UserListDTO;
import wang.vanson.vspace.im.dto.UserStartDTO;
import wang.vanson.vspace.im.util.CharsetUtil;
import wang.vanson.vspace.im.util.JWTUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class IMWebSocketMsgHandler implements IWsMsgHandler {
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) {
        String token = httpRequest.getParam("t");
        String spaceName = httpRequest.getParam("spaceName");
        Tio.bindGroup(channelContext, spaceName);

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(spaceName)) {
            return httpResponse;
        }

        String username;
        try {
            username = JWTUtils.verifyToken(token);
        } catch (Exception e) {
            return null;
        }
        Tio.bindUser(channelContext, username);
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) {
        String spaceName = httpRequest.getParam("spaceName");
        String userId = channelContext.userid;
        Page<String> users = Tio.getPageOfGroup(channelContext.getTioConfig(),
                spaceName, 0, 100,
                value -> ((ChannelContext) value).userid);

        if (users != null) {
            UserListDTO userListDTO = new UserListDTO();
            userListDTO.setType(DataType.ALL_USER_LIST.getValue());
            userListDTO.setUsers(users.getList());
            String dataJson = JSON.toJSONString(userListDTO);

            Tio.send(channelContext, WsResponse.fromText(dataJson, CharsetUtil.UTF_8));
        }

        if (!StringUtils.isEmpty(userId)) {
            incrementUser(userId, spaceName, channelContext);
        }
        log.debug("握手成功");
    }
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) {
        log.debug("接收到bytes消息");
        return null;
    }
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) {
        return null;
    }
    @Override
    public Object onText(WsRequest wsRequest, String s, ChannelContext channelContext) {
        log.debug("接收到文本消息："+s);
        JSONObject jsonObject = JSON.parseObject(s);
        int type = jsonObject.getInteger("type");
        if (DataType.USER_START.getValue() == type) {
            UserStartDTO userStartDTO = jsonObject.toJavaObject(UserStartDTO.class);
            Tio.bindUser(channelContext, userStartDTO.getUid());
            incrementUser(userStartDTO.getUid(), userStartDTO.getSpaceName(), channelContext);
        } else if (DataType.GROUP_MSG.getValue() == type) {
            LocalDateTime localDateTime = LocalDateTime.now();
            MsgDTO msgDTO = jsonObject.toJavaObject(MsgDTO.class);
            msgDTO.setMid(UUID.randomUUID().toString());
            msgDTO.setUsername(msgDTO.getFrom());
            msgDTO.setCreateTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
            msgDTO.setSendDate(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            msgDTO.setSendTime(localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            String msgStr = JSON.toJSONString(msgDTO);
            Tio.sendToGroup(channelContext.getTioConfig(), msgDTO.getGroupId(), WsResponse.fromText(msgStr, CharsetUtil.UTF_8));
        }
        return null;
    }

    private void incrementUser(String userId, String spaceName, ChannelContext channelContext) {
        UserListDTO incrementUserDTO = new UserListDTO();
        incrementUserDTO.setType(DataType.INCREMENT_USER.getValue());
        incrementUserDTO.addUser(userId);
        String incrementUserJson = JSON.toJSONString(incrementUserDTO);

        Tio.sendToGroup(channelContext.getTioConfig(), spaceName,
                WsResponse.fromText(incrementUserJson, CharsetUtil.UTF_8), ctx -> !ctx.userid.equals(userId));
    }
}