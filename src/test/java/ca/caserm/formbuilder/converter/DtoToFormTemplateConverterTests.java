package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.AbstractTests;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class DtoToFormTemplateConverterTests extends AbstractTests {

    @Autowired
    private DtoToFormTemplateConverter converter;

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public DtoToFormTemplateConverter dtoToFormTemplateConverter() {
            return new DtoToFormTemplateConverter();
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
    void testConvert() throws IOException {
        FormTemplateDto formTemplateDto = getObjectFromJson("json/form_template_dto_with_id.json", FormTemplateDto.class);
        FormTemplateEmailDto formTemplateEmailDto = getObjectFromJson("json/form_template_email_dto_with_id.json", FormTemplateEmailDto.class);
        formTemplateEmailDto.setFormTemplateId(formTemplateDto.getId());
        FormTemplateActionDto formTemplateActionDto = getObjectFromJson("json/form_template_action_dto_with_id.json", FormTemplateActionDto.class);
        formTemplateActionDto.setFormTemplateId(formTemplateDto.getId());
        formTemplateDto.setFormTemplateEmailDtos(Collections.singletonList(formTemplateEmailDto));
        formTemplateDto.setFormTemplateActionDtos(Collections.singletonList(formTemplateActionDto));

        FormTemplate formTemplate = converter.convert(formTemplateDto);

        assertNotNull(formTemplate);
        assertNotNull(formTemplate.getTitle());
        assertNotNull(formTemplate.getDescription());
        assertNotNull(formTemplate.getSchema());
        assertNotNull(formTemplate.getAccountId());
        assertNotNull(formTemplate.getThemeColor());
        assertNotNull(formTemplate.getBackgroundColor());
        assertNotNull(formTemplate.getTextFont());
        assertNotNull(formTemplate.getEnabled());
        assertNotNull(formTemplate.getFormTemplateEmails());
        assertNotNull(formTemplate.getFormTemplateActions());
        assertEquals(formTemplateDto.getTitle(), formTemplate.getTitle());
        assertEquals(formTemplateDto.getDescription(), formTemplate.getDescription());
        assertEquals(formTemplateDto.getSchema(), formTemplate.getSchema());
        assertEquals(formTemplateDto.getAccountId(), formTemplate.getAccountId());
        assertEquals(formTemplateDto.getThemeColor(), formTemplate.getThemeColor());
        assertEquals(formTemplateDto.getBackgroundColor(), formTemplate.getBackgroundColor());
        assertEquals(formTemplateDto.getTextFont(), formTemplate.getTextFont());
        assertEquals(formTemplateDto.getEnabled(), formTemplate.getEnabled());
        assertFormTemplateEmails(formTemplateDto.getFormTemplateEmailDtos(), formTemplate.getFormTemplateEmails());
        assertFormTemplateActions(formTemplateDto.getFormTemplateActionDtos(), formTemplate.getFormTemplateActions());
    }

    private void assertFormTemplateEmails(List<FormTemplateEmailDto> formTemplateEmailDtos, List<FormTemplateEmail> formTemplateEmails) {
        assertNotNull(formTemplateEmails);
        assertEquals(1, formTemplateEmails.size());
        assertNotNull(formTemplateEmails.get(0).getActionName());
        assertNotNull(formTemplateEmails.get(0).getEmailAddress());
        assertNotNull(formTemplateEmails.get(0).getReplyEmailAddress());
        assertNotNull(formTemplateEmails.get(0).getEmailSubject());
        assertNotNull(formTemplateEmails.get(0).getEmailBody());
        assertNotNull(formTemplateEmails.get(0).getFromName());
        assertNotNull(formTemplateEmails.get(0).getFromAddress());
        assertNotNull(formTemplateEmails.get(0).getAttachment());
        assertNotNull(formTemplateEmails.get(0).getEnabled());
        assertEquals(formTemplateEmailDtos.get(0).getActionName(), formTemplateEmails.get(0).getActionName());
        assertEquals(formTemplateEmailDtos.get(0).getEmailAddress(), formTemplateEmails.get(0).getEmailAddress());
        assertEquals(formTemplateEmailDtos.get(0).getReplyEmailAddress(), formTemplateEmails.get(0).getReplyEmailAddress());
        assertEquals(formTemplateEmailDtos.get(0).getEmailSubject(), formTemplateEmails.get(0).getEmailSubject());
        assertEquals(formTemplateEmailDtos.get(0).getEmailBody(), formTemplateEmails.get(0).getEmailBody());
        assertEquals(formTemplateEmailDtos.get(0).getFromName(), formTemplateEmails.get(0).getFromName());
        assertEquals(formTemplateEmailDtos.get(0).getFromAddress(), formTemplateEmails.get(0).getFromAddress());
        assertEquals(formTemplateEmailDtos.get(0).getAttachment(), formTemplateEmails.get(0).getAttachment());
        assertEquals(formTemplateEmailDtos.get(0).getEnabled(), formTemplateEmails.get(0).getEnabled());
    }

    private void assertFormTemplateActions(List<FormTemplateActionDto> formTemplateActionDtos, List<FormTemplateAction> formTemplateActions) {
        assertNotNull(formTemplateActions);
        assertEquals(1, formTemplateActions.size());
        assertNotNull(formTemplateActions.get(0).getActionName());
        assertNotNull(formTemplateActions.get(0).getMessage());
        assertNotNull(formTemplateActions.get(0).getEnabled());
        assertEquals(formTemplateActionDtos.get(0).getActionName(), formTemplateActions.get(0).getActionName());
        assertEquals(formTemplateActionDtos.get(0).getMessage(), formTemplateActions.get(0).getMessage());
        assertEquals(formTemplateActionDtos.get(0).getEnabled(), formTemplateActions.get(0).getEnabled());
    }

}
