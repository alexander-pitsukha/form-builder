package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.AbstractTests;
import ca.caserm.formbuilder.dto.FormTemplateActionDto;
import ca.caserm.formbuilder.entity.FormTemplateAction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class DtoToFormTemplateActionConverterTests extends AbstractTests {

    @Autowired
    private DtoToFormTemplateActionConverter converter;

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public DtoToFormTemplateActionConverter dtoToFormTemplateActionConverter() {
            return new DtoToFormTemplateActionConverter();
        }
    }

    @Test
    void testConvert() throws IOException {
        FormTemplateActionDto formTemplateActionDto = getObjectFromJson("json/form_template_action_dto_with_id.json", FormTemplateActionDto.class);

        FormTemplateAction formTemplateAction = converter.convert(formTemplateActionDto);

        assertNotNull(formTemplateAction);
        assertNotNull(formTemplateAction.getActionName());
        assertNotNull(formTemplateAction.getMessage());
        assertNotNull(formTemplateAction.getEnabled());
        assertEquals(formTemplateActionDto.getActionName(), formTemplateAction.getActionName());
        assertEquals(formTemplateActionDto.getMessage(), formTemplateAction.getMessage());
        assertEquals(formTemplateActionDto.getEnabled(), formTemplateAction.getEnabled());
    }

}
