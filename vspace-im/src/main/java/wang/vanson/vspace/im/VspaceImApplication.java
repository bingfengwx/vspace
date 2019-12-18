package wang.vanson.vspace.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tio.websocket.starter.EnableTioWebSocketServer;

@SpringBootApplication
@EnableTioWebSocketServer
public class VspaceImApplication {

    public static void main(String[] args) {
        SpringApplication.run(VspaceImApplication.class, args);
    }

}
