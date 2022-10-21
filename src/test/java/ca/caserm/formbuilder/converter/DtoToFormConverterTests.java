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
class DtoToFormConverterTests extends AbstractTests {

    @Autowired
    private DtoToFormConverter converter;

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public DtoToFormConverter dtoToFormConverter() {
            return new DtoToFormConverter();
        }
    }

    @Test
    void testConvert() throws IOException {
        FormDto formDto = getObjectFromJson("json/form_dto_with_id.json", FormDto.class);

        Form form = converter.convert(formDto);

        assertNotNull(form);
        assertNotNull(form.getTitle());
        assertNotNull(form.getDescription());
        assertNotNull(form.getSchema());
        assertNotNull(form.getAccountId());
        assertNotNull(form.getThemeColor());
        assertNotNull(form.getBackgroundColor());
        assertNotNull(form.getTextFont());
        assertEquals(formDto.getTitle(), form.getTitle());
        assertEquals(formDto.getDescription(), form.getDescription());
        assertEquals(formDto.getSchema(), form.getSchema());
        assertEquals(formDto.getAccountId(), form.getAccountId());
        assertEquals(formDto.getThemeColor(), form.getThemeColor());
        assertEquals(formDto.getBackgroundColor(), form.getBackgroundColor());
        assertEquals(formDto.getTextFont(), form.getTextFont());
    }

}
