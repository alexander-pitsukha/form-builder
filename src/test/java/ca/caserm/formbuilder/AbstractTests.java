package ca.caserm.formbuilder;

import ca.caserm.formbuilder.config.TestAppConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;

import java.util.Random;

@Getter
@Import(TestAppConfiguration.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class AbstractTests {

    private static final Random RANDOM = new Random();
    @Autowired
    private ObjectMapper objectMapper;

    public <T> T getObjectFromJson(String fileSource, Class<T> valueType) throws java.io.IOException {
        return objectMapper.readValue(new ClassPathResource(fileSource).getInputStream(), valueType);
    }

    public static long nextLong() {
        return RANDOM.nextLong();
    }

    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

}
