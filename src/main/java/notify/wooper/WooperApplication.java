package notify.wooper;

import notify.wooper.service.LineJwtGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("notify.wooper.mapper")
public class WooperApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(WooperApplication.class, args);
	}

}
