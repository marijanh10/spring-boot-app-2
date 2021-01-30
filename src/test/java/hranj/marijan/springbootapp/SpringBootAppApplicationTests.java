package hranj.marijan.springbootapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootAppApplication.class)
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")

public class SpringBootAppApplicationTests {

	@Test
	void contextLoads() {
	}

}
