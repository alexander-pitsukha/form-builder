package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.AbstractTests;
import ca.caserm.formbuilder.dto.FormTemplateEmailDto;
import ca.caserm.formbuilder.entity.FormTemplate;
import ca.caserm.formbuilder.entity.FormTemplateEmail;
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
class FormTemplateEmailToDtoConverterTests extends AbstractTests {

    @Autowired
    private FormTemplateEmailToDtoConverter converter;

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public FormTemplateEmailToDtoConverter formTemplateEmailToDtoConverter() {
            return new FormTemplateEmailToDtoConverter();
        }
    }

    @Test
    void testConvert() throws IOException {
        FormTemplateEmail formTemplateEmail = getObjectFromJson("json/form_template_email.json", FormTemplateEmail.class);
        formTemplateEmail.setFormTemplate(getObjectFromJson("json/form_template.json", FormTemplate.class));

        FormTemplateEmailDto formTemplateEmailDto = converter.convert(formTemplateEmail);

        assertNotNull(formTemplateEmailDto);
        assertNotNull(formTemplateEmailDto.getId());
        assertNotNull(formTemplateEmailDto.getActionName());
        assertNotNull(formTemplateEmailDto.getEmailAddress());
        assertNotNull(formTemplateEmailDto.getReplyEmailAddress());
        assertNotNull(formTemplateEmailDto.getEmailSubject());
        assertNotNull(formTemplateEmailDto.getEmailBody());
        assertNotNull(formTemplateEmailDto.getFromName());
        assertNotNull(formTemplateEmailDto.getFromAddress());
        assertNotNull(formTemplateEmailDto.getAttachment());
        assertNotNull(formTemplateEmailDto.getEnabled());
        assertEquals(formTemplateEmail.getId(), formTemplateEmail.getId());
        assertEquals(formTemplateEmail.getActionName(), formTemplateEmailDto.getActionName());
        assertEquals(formTemplateEmail.getEmailAddress(), formTemplateEmailDto.getEmailAddress());
        assertEquals(formTemplateEmail.getReplyEmailAddress(), formTemplateEmailDto.getReplyEmailAddress());
        assertEquals(formTemplateEmail.getEmailSubject(), formTemplateEmailDto.getEmailSubject());
        assertEquals(formTemplateEmail.getEmailBody(), formTemplateEmailDto.getEmailBody());
        assertEquals(formTemplateEmail.getFromName(), formTemplateEmailDto.getFromName());
        assertEquals(formTemplateEmail.getFromAddress(), formTemplateEmailDto.getFromAddress());
        assertEquals(formTemplateEmail.getAttachment(), formTemplateEmailDto.getAttachment());
        assertEquals(formTemplateEmail.getEnabled(), formTemplateEmailDto.getEnabled());
        assertEquals(formTemplateEmail.getFormTemplate().getId(), formTemplateEmailDto.getFormTemplateId());
    }

}
