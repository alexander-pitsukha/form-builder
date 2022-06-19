package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.AbstractTests;
import ca.caserm.formbuilder.dto.FormTemplateEmailDto;
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
class DtoToFormTemplateEmailConverterTests extends AbstractTests {

    @Autowired
    private DtoToFormTemplateEmailConverter converter;

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public DtoToFormTemplateEmailConverter dtoToFormTemplateEmailConverter() {
            return new DtoToFormTemplateEmailConverter();
        }
    }

    @Test
    void testConvert() throws IOException {
        FormTemplateEmailDto formTemplateEmailDto = getObjectFromJson("json/form_template_email_dto_with_id.json", FormTemplateEmailDto.class);

        FormTemplateEmail formTemplateEmail = converter.convert(formTemplateEmailDto);

        assertNotNull(formTemplateEmail);
        assertNotNull(formTemplateEmail.getActionName());
        assertNotNull(formTemplateEmail.getEmailAddress());
        assertNotNull(formTemplateEmail.getReplyEmailAddress());
        assertNotNull(formTemplateEmail.getEmailSubject());
        assertNotNull(formTemplateEmail.getEmailBody());
        assertNotNull(formTemplateEmail.getFromName());
        assertNotNull(formTemplateEmail.getFromAddress());
        assertNotNull(formTemplateEmail.getAttachment());
        assertNotNull(formTemplateEmail.getEnabled());
        assertEquals(formTemplateEmailDto.getActionName(), formTemplateEmail.getActionName());
        assertEquals(formTemplateEmailDto.getEmailAddress(), formTemplateEmail.getEmailAddress());
        assertEquals(formTemplateEmailDto.getReplyEmailAddress(), formTemplateEmail.getReplyEmailAddress());
        assertEquals(formTemplateEmailDto.getEmailSubject(), formTemplateEmail.getEmailSubject());
        assertEquals(formTemplateEmailDto.getEmailBody(), formTemplateEmail.getEmailBody());
        assertEquals(formTemplateEmailDto.getFromName(), formTemplateEmail.getFromName());
        assertEquals(formTemplateEmailDto.getFromAddress(), formTemplateEmail.getFromAddress());
        assertEquals(formTemplateEmailDto.getAttachment(), formTemplateEmail.getAttachment());
        assertEquals(formTemplateEmailDto.getEnabled(), formTemplateEmail.getEnabled());
    }

}
