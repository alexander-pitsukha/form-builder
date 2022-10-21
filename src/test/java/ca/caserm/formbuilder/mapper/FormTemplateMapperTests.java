package ca.caserm.formbuilder.mapper;

import ca.caserm.formbuilder.AbstractTests;
import ca.caserm.formbuilder.converter.DtoToFormTemplateActionConverter;
import ca.caserm.formbuilder.converter.DtoToFormTemplateEmailConverter;
import ca.caserm.formbuilder.dto.FormTemplateActionDto;
import ca.caserm.formbuilder.dto.FormTemplateDto;
import ca.caserm.formbuilder.dto.FormTemplateEmailDto;
import ca.caserm.formbuilder.entity.FormTemplate;
import ca.caserm.formbuilder.entity.FormTemplateAction;
import ca.caserm.formbuilder.entity.FormTemplateEmail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class FormTemplateMapperTests extends AbstractTests {

    @Autowired
    private FormTemplateMapper formTemplateMapper;

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public FormTemplateMapper formTemplateMapper() {
            return new FormTemplateMapper();
        }

        @Bean
        public DtoToFormTemplateEmailConverter dtoToFormTemplateEmailConverter() {
            return new DtoToFormTemplateEmailConverter();
        }

        @Bean
        public DtoToFormTemplateActionConverter dtoToFormTemplateActionConverter() {
            return new DtoToFormTemplateActionConverter();
        }

        @Bean
        public DefaultConversionService defaultConversionService(List<Converter<?, ?>> converters) {
            DefaultConversionService defaultConversionService = new DefaultConversionService();
            converters.forEach(defaultConversionService::addConverter);
            return defaultConversionService;
        }
    }

    @Test
    void testMap() throws IOException {
        FormTemplate formTemplate = getObjectFromJson("json/form_template_with_id.json", FormTemplate.class);
        FormTemplateAction formTemplateAction = getObjectFromJson("json/form_template_action.json", FormTemplateAction.class);
        formTemplateAction.setFormTemplate(getObjectFromJson("json/form_template.json", FormTemplate.class));
        FormTemplateEmail formTemplateEmail = getObjectFromJson("json/form_template_email.json", FormTemplateEmail.class);
        formTemplateEmail.setFormTemplate(formTemplate);
        formTemplate.setFormTemplateEmails(new ArrayList<>(Collections.singletonList(formTemplateEmail)));
        formTemplate.setFormTemplateActions(new ArrayList<>(Collections.singletonList(formTemplateAction)));
        FormTemplateActionDto formTemplateActionDto = getObjectFromJson("json/form_template_action_dto.json", FormTemplateActionDto.class);
        formTemplateActionDto.setFormTemplateId(formTemplate.getId());
        FormTemplateDto formTemplateDto = getObjectFromJson("json/form_template_dto.json", FormTemplateDto.class);
        formTemplateDto.setId(formTemplate.getId());
        formTemplateDto.setFormTemplateEmailDtos(Collections.singletonList(getObjectFromJson("json/form_template_email_dto.json", FormTemplateEmailDto.class)));
        formTemplateDto.setFormTemplateActionDtos(Collections.singletonList(formTemplateActionDto));

        formTemplateMapper.map(formTemplateDto, formTemplate);

        assertEquals(formTemplate.getTitle(), formTemplateDto.getTitle());
        assertEquals(formTemplate.getDescription(), formTemplateDto.getDescription());
        assertEquals(formTemplate.getSchema(), formTemplateDto.getSchema());
        assertEquals(formTemplate.getAccountId(), formTemplateDto.getAccountId());
        assertEquals(formTemplate.getThemeColor(), formTemplateDto.getThemeColor());
        assertEquals(formTemplate.getBackgroundColor(), formTemplateDto.getBackgroundColor());
        assertEquals(formTemplate.getTextFont(), formTemplateDto.getTextFont());
        assertEquals(formTemplate.getEnabled(), formTemplateDto.getEnabled());
        assertEquals(1, formTemplateDto.getFormTemplateEmailDtos().size());
        assertEquals(1, formTemplateDto.getFormTemplateActionDtos().size());
    }

}
