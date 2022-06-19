package ca.caserm.formbuilder.mapper;

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

@ExtendWith(SpringExtension.class)
class FormMapperTests extends AbstractTests {

    @Autowired
    private FormMapper formMapper;

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public FormMapper formMapper() {
            return new FormMapper();
        }
    }

    @Test
    void testMap() throws IOException {
        Form form = getObjectFromJson("json/form_with_id.json", Form.class);
        FormDto formDto = getObjectFromJson("json/form_dto_with_id.json", FormDto.class);
        formDto.setId(form.getId());

        formMapper.map(formDto, form);

        assertEquals(formDto.getTitle(), form.getTitle());
        assertEquals(formDto.getDescription(), form.getDescription());
        assertEquals(formDto.getSchema(), form.getSchema());
        assertEquals(formDto.getAccountId(), form.getAccountId());
        assertEquals(formDto.getThemeColor(), form.getThemeColor());
        assertEquals(formDto.getBackgroundColor(), form.getBackgroundColor());
        assertEquals(formDto.getTextFont(), form.getTextFont());
    }

}
