package wang.vanson.vspace.im.ws;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.websocket.common.WsRequest;
import wang.vanson.vspace.im.dto.DataType;
import wang.vanson.vspace.im.dto.UserStartDTO;

@Component
public class UserStartMsgProcessor extends MsgProcessor {

    private final CommonHandle commonHandle;

    public UserStartMsgProcessor(MsgProcessorFactory msgProcessorFactory,
                                 CommonHandle commonHandle) {
        super(msgProcessorFactory);
        this.commonHandle = commonHandle;
    }

    @Override
    public DataType dataType() {
        return DataType.USER_START;
    }

    @Override
    public Object onText(WsRequest wsRequest, JSONObject jsonObject, ChannelContext channelContext) {
        UserStartDTO userStartDTO = jsonObject.toJavaObject(UserStartDTO.class);
        Tio.bindUser(channelContext, userStartDTO.getUid());
        commonHandle.incrementUser(userStartDTO.getUid(), userStartDTO.getSpaceName(), channelContext);
        return null;
    }
}
