package wang.vanson.vspace.im.ws;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;
import wang.vanson.vspace.im.dto.DataType;
import wang.vanson.vspace.im.dto.UserListDTO;
import wang.vanson.vspace.im.util.CharsetUtil;

@Component
public class CommonHandle {

    public void incrementUser(String userId, String spaceName, ChannelContext channelContext) {
        UserListDTO incrementUserDTO = new UserListDTO();
        incrementUserDTO.setType(DataType.INCREMENT_USER.getType());
        incrementUserDTO.addUser(userId);
        String incrementUserJson = JSON.toJSONString(incrementUserDTO);

        Tio.sendToGroup(channelContext.getTioConfig(), spaceName,
                WsResponse.fromText(incrementUserJson, CharsetUtil.UTF_8));
    }
}
