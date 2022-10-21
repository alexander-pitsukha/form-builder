package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.AbstractTests;
import ca.caserm.formbuilder.dto.FormDto;
import ca.caserm.formbuilder.entity.Form;
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
class FormToDtoConverterTests extends AbstractTests {

    @Autowired
    private FormToDtoConverter converter;

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public FormToDtoConverter formToDtoConverter() {
            return new FormToDtoConverter();
        }
    }

    @Test
    void testConvert() throws IOException {
        Form form = getObjectFromJson("json/form_with_id.json", Form.class);

        FormDto formDto = converter.convert(form);

        assertNotNull(formDto);
        assertNotNull(formDto.getId());
        assertNotNull(formDto.getTitle());
        assertNotNull(formDto.getDescription());
        assertNotNull(formDto.getSchema());
        assertNotNull(formDto.getAccountId());
        assertNotNull(formDto.getCreateDate());
        assertNotNull(formDto.getUpdateDate());
        assertNotNull(formDto.getThemeColor());
        assertNotNull(formDto.getBackgroundColor());
        assertNotNull(formDto.getTextFont());
        assertNotNull(formDto.getCreateDate());
        assertNotNull(formDto.getUpdateDate());
        assertEqualsForm(form, formDto);
    }

    private void assertEqualsForm(Form form, FormDto formDto) {
        assertEquals(form.getId(), formDto.getId());
        assertEquals(form.getTitle(), formDto.getTitle());
        assertEquals(form.getDescription(), formDto.getDescription());
        assertEquals(form.getSchema(), formDto.getSchema());
        assertEquals(form.getAccountId(), formDto.getAccountId());
        assertEquals(form.getThemeColor(), formDto.getThemeColor());
        assertEquals(form.getBackgroundColor(), formDto.getBackgroundColor());
        assertEquals(form.getTextFont(), formDto.getTextFont());
        assertEquals(form.getFormTemplate().getId(), formDto.getFormTemplateId());
        assertEquals(form.getCreateDate().toLocalDate(), formDto.getCreateDate());
        assertEquals(form.getUpdateDate().toLocalDate(), formDto.getUpdateDate());
    }

}
