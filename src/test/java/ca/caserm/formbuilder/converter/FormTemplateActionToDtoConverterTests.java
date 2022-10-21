package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.AbstractTests;
import ca.caserm.formbuilder.dto.FormTemplateActionDto;
import ca.caserm.formbuilder.entity.FormTemplate;
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
class FormTemplateActionToDtoConverterTests extends AbstractTests {

    @Autowired
    private FormTemplateActionToDtoConverter converter;

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public FormTemplateActionToDtoConverter formTemplateActionToDtoConverter() {
            return new FormTemplateActionToDtoConverter();
        }
    }

    @Test
    void testConvert() throws IOException {
        FormTemplateAction formTemplateAction = getObjectFromJson("json/form_template_action.json", FormTemplateAction.class);
        formTemplateAction.setFormTemplate(getObjectFromJson("json/form_template.json", FormTemplate.class));

        FormTemplateActionDto formTemplateActionDto = converter.convert(formTemplateAction);

        assertNotNull(formTemplateActionDto);
        assertNotNull(formTemplateActionDto.getId());
        assertNotNull(formTemplateActionDto.getActionName());
        assertNotNull(formTemplateActionDto.getMessage());
        assertNotNull(formTemplateActionDto.getEnabled());
        assertEquals(formTemplateAction.getId(), formTemplateActionDto.getId());
        assertEquals(formTemplateAction.getActionName(), formTemplateActionDto.getActionName());
        assertEquals(formTemplateAction.getMessage(), formTemplateActionDto.getMessage());
        assertEquals(formTemplateAction.getEnabled(), formTemplateActionDto.getEnabled());
        assertEquals(formTemplateAction.getFormTemplate().getId(), formTemplateActionDto.getFormTemplateId());
    }

}
