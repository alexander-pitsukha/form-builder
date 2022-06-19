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
class FormTemplateToDtoConverterTests extends AbstractTests {

    @Autowired
    private FormTemplateToDtoConverter converter;

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public FormTemplateToDtoConverter formTemplateToDtoConverter() {
            return new FormTemplateToDtoConverter();
        }

        @Bean
        public FormTemplateEmailToDtoConverter formTemplateEmailToDtoConverter() {
            return new FormTemplateEmailToDtoConverter();
        }

        @Bean
        public FormTemplateActionToDtoConverter formTemplateActionToDtoConverter() {
            return new FormTemplateActionToDtoConverter();
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
        FormTemplate formTemplate = getObjectFromJson("json/form_template_with_id.json", FormTemplate.class);
        FormTemplateAction formTemplateAction = getObjectFromJson("json/form_template_action.json", FormTemplateAction.class);
        FormTemplateEmail formTemplateEmail = getObjectFromJson("json/form_template_email.json", FormTemplateEmail.class);
        formTemplateEmail.setFormTemplate(formTemplate);
        formTemplateAction.setFormTemplate(formTemplate);
        formTemplate.setFormTemplateEmails(Collections.singletonList(formTemplateEmail));
        formTemplate.setFormTemplateActions(Collections.singletonList(formTemplateAction));

        FormTemplateDto formTemplateDto = converter.convert(formTemplate);

        assertNotNull(formTemplateDto);
        assertNotNull(formTemplateDto.getId());
        assertNotNull(formTemplateDto.getTitle());
        assertNotNull(formTemplateDto.getDescription());
        assertNotNull(formTemplateDto.getSchema());
        assertNotNull(formTemplateDto.getAccountId());
        assertNotNull(formTemplateDto.getCreateDate());
        assertNotNull(formTemplateDto.getUpdateDate());
        assertNotNull(formTemplateDto.getThemeColor());
        assertNotNull(formTemplateDto.getBackgroundColor());
        assertNotNull(formTemplateDto.getTextFont());
        assertNotNull(formTemplateDto.getEnabled());
        assertNotNull(formTemplateDto.getCreateDate());
        assertNotNull(formTemplateDto.getUpdateDate());
        assertFormTemplate(formTemplate, formTemplateDto);
        assertFormTemplateEmails(formTemplate.getFormTemplateEmails(), formTemplateDto.getFormTemplateEmailDtos());
        assertFormTemplateActions(formTemplate.getFormTemplateActions(), formTemplateDto.getFormTemplateActionDtos());
    }

    private void assertFormTemplate(FormTemplate formTemplate, FormTemplateDto formTemplateDto) {
        assertEquals(formTemplate.getId(), formTemplateDto.getId());
        assertEquals(formTemplate.getTitle(), formTemplateDto.getTitle());
        assertEquals(formTemplate.getDescription(), formTemplateDto.getDescription());
        assertEquals(formTemplate.getSchema(), formTemplateDto.getSchema());
        assertEquals(formTemplate.getAccountId(), formTemplateDto.getAccountId());
        assertEquals(formTemplate.getThemeColor(), formTemplateDto.getThemeColor());
        assertEquals(formTemplate.getBackgroundColor(), formTemplateDto.getBackgroundColor());
        assertEquals(formTemplate.getTextFont(), formTemplateDto.getTextFont());
        assertEquals(formTemplate.getEnabled(), formTemplateDto.getEnabled());
        assertEquals(formTemplate.getCreateDate().toLocalDate(), formTemplateDto.getCreateDate());
        assertEquals(formTemplate.getUpdateDate().toLocalDate(), formTemplateDto.getUpdateDate());
    }

    private void assertFormTemplateEmails(List<FormTemplateEmail> formTemplateEmails, List<FormTemplateEmailDto> formTemplateEmailDtos) {
        assertNotNull(formTemplateEmails);
        assertEquals(1, formTemplateEmails.size());
        assertNotNull(formTemplateEmailDtos.get(0).getId());
        assertNotNull(formTemplateEmailDtos.get(0).getActionName());
        assertNotNull(formTemplateEmailDtos.get(0).getEmailAddress());
        assertNotNull(formTemplateEmailDtos.get(0).getReplyEmailAddress());
        assertNotNull(formTemplateEmailDtos.get(0).getEmailSubject());
        assertNotNull(formTemplateEmailDtos.get(0).getEmailBody());
        assertNotNull(formTemplateEmailDtos.get(0).getFromName());
        assertNotNull(formTemplateEmailDtos.get(0).getFromAddress());
        assertNotNull(formTemplateEmailDtos.get(0).getAttachment());
        assertNotNull(formTemplateEmailDtos.get(0).getEnabled());
        assertEquals(formTemplateEmails.get(0).getId(), formTemplateEmailDtos.get(0).getId());
        assertEquals(formTemplateEmails.get(0).getActionName(), formTemplateEmailDtos.get(0).getActionName());
        assertEquals(formTemplateEmails.get(0).getEmailAddress(), formTemplateEmailDtos.get(0).getEmailAddress());
        assertEquals(formTemplateEmails.get(0).getReplyEmailAddress(), formTemplateEmails.get(0).getReplyEmailAddress());
        assertEquals(formTemplateEmails.get(0).getEmailSubject(), formTemplateEmailDtos.get(0).getEmailSubject());
        assertEquals(formTemplateEmails.get(0).getEmailBody(), formTemplateEmailDtos.get(0).getEmailBody());
        assertEquals(formTemplateEmails.get(0).getFromName(), formTemplateEmailDtos.get(0).getFromName());
        assertEquals(formTemplateEmails.get(0).getFromAddress(), formTemplateEmailDtos.get(0).getFromAddress());
        assertEquals(formTemplateEmails.get(0).getAttachment(), formTemplateEmailDtos.get(0).getAttachment());
        assertEquals(formTemplateEmails.get(0).getEnabled(), formTemplateEmailDtos.get(0).getEnabled());
    }

    private void assertFormTemplateActions(List<FormTemplateAction> formTemplateActions, List<FormTemplateActionDto> formTemplateActionDtos) {
        assertNotNull(formTemplateActionDtos);
        assertEquals(1, formTemplateActionDtos.size());
        assertNotNull(formTemplateActionDtos.get(0).getId());
        assertNotNull(formTemplateActionDtos.get(0).getActionName());
        assertNotNull(formTemplateActionDtos.get(0).getMessage());
        assertNotNull(formTemplateActionDtos.get(0).getEnabled());
        assertEquals(formTemplateActions.get(0).getId(), formTemplateActionDtos.get(0).getId());
        assertEquals(formTemplateActions.get(0).getActionName(), formTemplateActionDtos.get(0).getActionName());
        assertEquals(formTemplateActions.get(0).getMessage(), formTemplateActionDtos.get(0).getMessage());
        assertEquals(formTemplateActions.get(0).getEnabled(), formTemplateActionDtos.get(0).getEnabled());
    }

}