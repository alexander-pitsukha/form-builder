package ca.caserm.formbuilder;

import ca.caserm.formbuilder.controller.FormBuilderController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FormBuilderApplicationTests {

    @Autowired
    private FormBuilderController formBuilderController;

    @Test
    void contextLoads() {
        assertNotNull(formBuilderController);
    }

}
