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
import wang.vanson.vspace.im.dto.UserListDTO;
import wang.vanson.vspace.im.util.CharsetUtil;
import wang.vanson.vspace.im.util.JWTUtils;

@Component
@Slf4j
public class IMWebSocketMsgHandler implements IWsMsgHandler {

    private final MsgProcessorFactory msgProcessorFactory;
    private final CommonHandle commonHandle;

    public IMWebSocketMsgHandler(MsgProcessorFactory msgProcessorFactory,
                                 CommonHandle commonHandle) {
        this.msgProcessorFactory = msgProcessorFactory;
        this.commonHandle = commonHandle;
    }

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

        if (!StringUtils.isEmpty(username)) {
            Tio.bindUser(channelContext, username);
        }
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
            userListDTO.setType(DataType.ALL_USER_LIST.getType());
            userListDTO.setUsers(users.getList());
            String dataJson = JSON.toJSONString(userListDTO);

            Tio.send(channelContext, WsResponse.fromText(dataJson, CharsetUtil.UTF_8));
        }

        if (!StringUtils.isEmpty(userId)) {
            commonHandle.incrementUser(userId, spaceName, channelContext);
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
        MsgProcessor msgProcessor = msgProcessorFactory.get(type);
        if (msgProcessor == null) {
            return null;
        }
        return msgProcessor.onText(wsRequest, jsonObject, channelContext);
    }
}