package notify.wooper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import notify.wooper.service.LineJwtGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/line")
public class LineController {

    @Autowired
    private LineJwtGenerator lineJwtGenerator;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/generate_line_token")
    public ResponseEntity<String> generateLineToken() throws Exception {
        String token = lineJwtGenerator.generate();

        return ResponseEntity.ok(token);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestBody String body) throws Exception {

        System.out.println("LINE Webhook:");
        System.out.println(body);

        JsonNode root = objectMapper.readTree(body);
        JsonNode events = root.path("events");

        for (JsonNode event : events) {

            String type = event.path("type").asText();

            if ("follow".equals(type)) {

                String userId = event
                        .path("source")
                        .path("userId")
                        .asText();

                System.out.println(
                        "LINE User ID = " + userId
                );
            }
        }

        return ResponseEntity.ok().build();
    }
}
