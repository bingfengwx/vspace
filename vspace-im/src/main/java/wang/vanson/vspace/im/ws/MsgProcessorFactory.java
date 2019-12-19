package wang.vanson.vspace.im.ws;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MsgProcessorFactory {
    private Map<Integer, MsgProcessor> processorMap = new HashMap<>();

    public void add(Integer type, MsgProcessor processor) {
        processorMap.put(type, processor);
    }

    public void remove(Integer type) {
        processorMap.remove(type);
    }

    public MsgProcessor get(Integer type) {
        return processorMap.get(type);
    }
}
