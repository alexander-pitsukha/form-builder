package ca.caserm.formbuilder.service;

import ca.caserm.formbuilder.AbstractTests;
import ca.caserm.formbuilder.FormBuilderApplication;
import ca.caserm.formbuilder.dto.FormDto;
import ca.caserm.formbuilder.dto.FormTemplateActionDto;
import ca.caserm.formbuilder.dto.FormTemplateDto;
import ca.caserm.formbuilder.dto.FormTemplateEmailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {FormBuilderApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
class FormBuilderServiceImplIntegrationTests extends AbstractTests {

    @Autowired
    private FormBuilderService formBuilderService;

    @Autowired
    private DefaultConversionService defaultConversionService;

    @BeforeEach
    void setUp() throws IOException {
        formBuilderService.saveFormTemplate(getObjectFromJson("json/form_template_dto_with_id.json", FormTemplateDto.class));
    }

    @Test
    void testSaveForm() throws IOException {
        FormDto formDto = getObjectFromJson("json/form_dto.json", FormDto.class);

        FormDto entry = formBuilderService.saveForm(formDto);

        assertNotNull(entry);
        assertNotNull(entry.getId());
        assertEquals(formDto.getTitle(), entry.getTitle());
        assertEquals(formDto.getDescription(), entry.getDescription());
        assertEquals(formDto.getSchema(), entry.getSchema());
        assertEquals(formDto.getAccountId(), entry.getAccountId());
        assertEquals(formDto.getThemeColor(), entry.getThemeColor());
        assertEquals(formDto.getBackgroundColor(), entry.getBackgroundColor());
        assertEquals(formDto.getTextFont(), entry.getTextFont());
        assertEquals(formDto.getFormTemplateId(), entry.getFormTemplateId());
    }

    @Test
    void testUpdateForm() throws IOException {
        FormDto formDto = formBuilderService.saveForm(getObjectFromJson("json/form_dto.json", FormDto.class));

        formDto.setTitle(UUID.randomUUID().toString());
        formDto.setDescription(UUID.randomUUID().toString());
        formDto.setSchema(UUID.randomUUID().toString());
        formDto.setAccountId(nextLong());
        formDto.setThemeColor(UUID.randomUUID().toString());
        formDto.setBackgroundColor(UUID.randomUUID().toString());
        formDto.setTextFont(UUID.randomUUID().toString());

        FormDto entry = formBuilderService.updateForm(formDto);

        assertNotNull(entry);
        assertNotNull(entry.getUpdateDate());
        assertEquals(formDto.getId(), entry.getId());
        assertEquals(formDto.getTitle(), entry.getTitle());
        assertEquals(formDto.getDescription(), entry.getDescription());
        assertEquals(formDto.getSchema(), entry.getSchema());
        assertEquals(formDto.getAccountId(), entry.getAccountId());
        assertEquals(formDto.getCreateDate(), entry.getCreateDate());
        assertEquals(formDto.getThemeColor(), entry.getThemeColor());
        assertEquals(formDto.getBackgroundColor(), entry.getBackgroundColor());
        assertEquals(formDto.getTextFont(), entry.getTextFont());
    }

    @Test
    void testDeleteForm() throws IOException {
        FormDto formDto = formBuilderService.saveForm(getObjectFromJson("json/form_dto.json", FormDto.class));

        formBuilderService.deleteForm(formDto.getId());
        formDto = formBuilderService.getFormById(formDto.getId());

        assertNull(formDto);
    }

    @Test
    void testSaveFormTemplate() throws IOException {
        FormTemplateDto formTemplateDto = getObjectFromJson("json/form_template_dto.json", FormTemplateDto.class);

        FormTemplateDto entry = formBuilderService.saveFormTemplate(formTemplateDto);

        assertNotNull(entry);
        assertNotNull(entry.getId());
        assertEquals(formTemplateDto.getTitle(), entry.getTitle());
        assertEquals(formTemplateDto.getDescription(), entry.getDescription());
        assertEquals(formTemplateDto.getSchema(), entry.getSchema());
        assertEquals(formTemplateDto.getAccountId(), entry.getAccountId());
        assertEquals(formTemplateDto.getThemeColor(), entry.getThemeColor());
        assertEquals(formTemplateDto.getBackgroundColor(), entry.getBackgroundColor());
        assertEquals(formTemplateDto.getTextFont(), entry.getTextFont());
        assertEquals(formTemplateDto.getEnabled(), entry.getEnabled());
    }

    @Test
    void testUpdateFormTemplate() throws IOException {
        FormTemplateDto formTemplateDto = formBuilderService.saveFormTemplate(getObjectFromJson("json/form_template_dto.json", FormTemplateDto.class));
        formTemplateDto.setTitle(UUID.randomUUID().toString());
        formTemplateDto.setDescription(UUID.randomUUID().toString());
        formTemplateDto.setSchema(UUID.randomUUID().toString());
        formTemplateDto.setAccountId(nextLong());
        formTemplateDto.setThemeColor(UUID.randomUUID().toString());
        formTemplateDto.setBackgroundColor(UUID.randomUUID().toString());
        formTemplateDto.setTextFont(UUID.randomUUID().toString());
        formTemplateDto.setEnabled(nextBoolean());

        FormTemplateDto entry = formBuilderService.updateFormTemplate(formTemplateDto);

        assertNotNull(entry);
        assertNotNull(entry.getUpdateDate());
        assertEquals(formTemplateDto.getId(), entry.getId());
        assertEquals(formTemplateDto.getTitle(), entry.getTitle());
        assertEquals(formTemplateDto.getDescription(), entry.getDescription());
        assertEquals(formTemplateDto.getSchema(), entry.getSchema());
        assertEquals(formTemplateDto.getAccountId(), entry.getAccountId());
        assertEquals(formTemplateDto.getCreateDate(), entry.getCreateDate());
        assertEquals(formTemplateDto.getThemeColor(), entry.getThemeColor());
        assertEquals(formTemplateDto.getBackgroundColor(), entry.getBackgroundColor());
        assertEquals(formTemplateDto.getTextFont(), entry.getTextFont());
        assertEquals(formTemplateDto.getEnabled(), entry.getEnabled());
    }

    @Test
    void testAddFormTemplateActions() throws IOException {
        FormTemplateDto formTemplateDto = getObjectFromJson("json/form_template_dto.json", FormTemplateDto.class);
        FormTemplateEmailDto formTemplateEmailDto = getObjectFromJson("json/form_template_email_dto.json", FormTemplateEmailDto.class);
        formTemplateEmailDto.setFormTemplateId(formTemplateDto.getId());
        FormTemplateActionDto formTemplateActionDto = getObjectFromJson("json/form_template_action_dto.json", FormTemplateActionDto.class);
        formTemplateActionDto.setFormTemplateId(formTemplateDto.getId());
        formTemplateDto.setFormTemplateEmailDtos(Collections.singletonList(formTemplateEmailDto));
        formTemplateDto.setFormTemplateActionDtos(Collections.singletonList(formTemplateActionDto));
        formTemplateDto = formBuilderService.saveFormTemplate(formTemplateDto);

        List<FormTemplateEmailDto> formTemplateEmailDtos = new ArrayList<>(formTemplateDto.getFormTemplateEmailDtos());
        formTemplateEmailDtos.add(formTemplateEmailDto);
        formTemplateDto.setFormTemplateEmailDtos(formTemplateEmailDtos);

        List<FormTemplateActionDto> formTemplateActionDtos = new ArrayList<>(formTemplateDto.getFormTemplateActionDtos());
        formTemplateActionDtos.add(formTemplateActionDto);
        formTemplateDto.setFormTemplateActionDtos(formTemplateActionDtos);

        FormTemplateDto entry = formBuilderService.updateFormTemplate(formTemplateDto);
        entry = formBuilderService.getFormTemplateById(entry.getId());

        assertFalse(entry.getFormTemplateEmailDtos().isEmpty());
        assertFalse(entry.getFormTemplateActionDtos().isEmpty());
        assertEquals(formTemplateDto.getFormTemplateEmailDtos().size(), entry.getFormTemplateEmailDtos().size());
        assertEquals(formTemplateDto.getFormTemplateActionDtos().size(), entry.getFormTemplateActionDtos().size());
    }

    @Test
    void testUpdateFormTemplateActions() throws IOException {
        FormTemplateDto formTemplateDto = getObjectFromJson("json/form_template_dto.json", FormTemplateDto.class);
        FormTemplateEmailDto formTemplateEmailDto = getObjectFromJson("json/form_template_email_dto_with_id.json", FormTemplateEmailDto.class);
        formTemplateEmailDto.setFormTemplateId(formTemplateDto.getId());
        FormTemplateActionDto formTemplateActionDto = getObjectFromJson("json/form_template_action_dto_with_id.json", FormTemplateActionDto.class);
        formTemplateActionDto.setFormTemplateId(formTemplateDto.getId());

        formTemplateDto.setFormTemplateEmailDtos(Collections.singletonList(formTemplateEmailDto));
        formTemplateDto.setFormTemplateActionDtos(Collections.singletonList(formTemplateActionDto));
        formTemplateDto = formBuilderService.saveFormTemplate(formTemplateDto);

        formTemplateEmailDto = formTemplateDto.getFormTemplateEmailDtos().get(0);
        formTemplateEmailDto.setActionName(UUID.randomUUID().toString());
        formTemplateEmailDto.setEmailAddress(UUID.randomUUID().toString());
        formTemplateEmailDto.setReplyEmailAddress(UUID.randomUUID().toString());
        formTemplateEmailDto.setEmailSubject(UUID.randomUUID().toString());
        formTemplateEmailDto.setEmailBody(UUID.randomUUID().toString());
        formTemplateEmailDto.setFromName(UUID.randomUUID().toString());
        formTemplateEmailDto.setFromAddress(UUID.randomUUID().toString());
        formTemplateEmailDto.setAttachment(UUID.randomUUID().toString());
        formTemplateEmailDto.setEnabled(!formTemplateEmailDto.getEnabled());

        formTemplateActionDto = formTemplateDto.getFormTemplateActionDtos().get(0);
        formTemplateActionDto.setActionName(UUID.randomUUID().toString());
        formTemplateActionDto.setMessage(UUID.randomUUID().toString());
        formTemplateActionDto.setEnabled(!formTemplateActionDto.getEnabled());

        FormTemplateDto formTemplate = formBuilderService.updateFormTemplate(formTemplateDto);
        FormTemplateEmailDto formTemplateEmail = formTemplate.getFormTemplateEmailDtos().get(0);
        FormTemplateActionDto formTemplateAction = formTemplate.getFormTemplateActionDtos().get(0);

        assertNotNull(formTemplateEmail);
        assertEquals(formTemplateEmailDto.getActionName(), formTemplateEmail.getActionName());
        assertEquals(formTemplateEmailDto.getEmailAddress(), formTemplateEmail.getEmailAddress());
        assertEquals(formTemplateEmailDto.getReplyEmailAddress(), formTemplateEmail.getReplyEmailAddress());
        assertEquals(formTemplateEmailDto.getEmailSubject(), formTemplateEmail.getEmailSubject());
        assertEquals(formTemplateEmailDto.getEmailBody(), formTemplateEmail.getEmailBody());
        assertEquals(formTemplateEmailDto.getFromName(), formTemplateEmail.getFromName());
        assertEquals(formTemplateEmailDto.getFromAddress(), formTemplateEmail.getFromAddress());
        assertEquals(formTemplateEmailDto.getAttachment(), formTemplateEmail.getAttachment());
        assertEquals(formTemplateEmailDto.getEnabled(), formTemplateEmail.getEnabled());

        assertNotNull(formTemplateAction);
        assertEquals(formTemplateActionDto.getActionName(), formTemplateAction.getActionName());
        assertEquals(formTemplateActionDto.getMessage(), formTemplateAction.getMessage());
        assertEquals(formTemplateActionDto.getEnabled(), formTemplateAction.getEnabled());
    }

    @Test
    void testDeleteAllFormTemplateActions() throws IOException {
        FormTemplateDto formTemplateDto = getObjectFromJson("json/form_template_dto.json", FormTemplateDto.class);
        FormTemplateEmailDto formTemplateEmailDto = getObjectFromJson("json/form_template_email_dto_with_id.json", FormTemplateEmailDto.class);
        formTemplateEmailDto.setFormTemplateId(formTemplateDto.getId());
        formTemplateDto.setFormTemplateEmailDtos(Collections.singletonList(formTemplateEmailDto));
        FormTemplateActionDto formTemplateActionDto = getObjectFromJson("json/form_template_action_dto_with_id.json", FormTemplateActionDto.class);
        formTemplateActionDto.setFormTemplateId(formTemplateDto.getId());
        formTemplateDto.setFormTemplateActionDtos(Collections.singletonList(formTemplateActionDto));
        formTemplateDto = formBuilderService.saveFormTemplate(formTemplateDto);

        formTemplateDto.setFormTemplateEmailDtos(Collections.emptyList());
        formTemplateDto.setFormTemplateActionDtos(Collections.emptyList());

        FormTemplateDto formTemplate = formBuilderService.updateFormTemplate(formTemplateDto);

        assertNotNull(formTemplate.getFormTemplateEmailDtos());
        assertNotNull(formTemplate.getFormTemplateActionDtos());
        assertTrue(formTemplate.getFormTemplateEmailDtos().isEmpty());
        assertTrue(formTemplate.getFormTemplateActionDtos().isEmpty());
    }

    @Test
    void testUpdateFormTemplateEnabled() throws IOException {
        FormTemplateDto formTemplateDto = getObjectFromJson("json/form_template_dto.json", FormTemplateDto.class);
        formTemplateDto = formBuilderService.saveFormTemplate(formTemplateDto);

        formBuilderService.updateFormTemplateEnabled(formTemplateDto.getId(), !formTemplateDto.getEnabled());

        FormTemplateDto entry = formBuilderService.getFormTemplateById(formTemplateDto.getId());

        assertNotNull(entry);
        assertEquals(formTemplateDto.getId(), entry.getId());
        assertEquals(formTemplateDto.getTitle(), entry.getTitle());
        assertEquals(formTemplateDto.getDescription(), entry.getDescription());
        assertEquals(formTemplateDto.getSchema(), entry.getSchema());
        assertEquals(formTemplateDto.getAccountId(), entry.getAccountId());
        assertEquals(formTemplateDto.getThemeColor(), entry.getThemeColor());
        assertEquals(formTemplateDto.getBackgroundColor(), entry.getBackgroundColor());
        assertEquals(formTemplateDto.getTextFont(), entry.getTextFont());
        assertEquals(!formTemplateDto.getEnabled(), entry.getEnabled());
    }

    @Test
    void testDeleteFormTemplate() throws IOException {
        FormTemplateDto formTemplateDto = getObjectFromJson("json/form_template_dto.json", FormTemplateDto.class);
        formTemplateDto = formBuilderService.saveFormTemplate(formTemplateDto);

        formBuilderService.deleteFormTemplate(formTemplateDto.getId());
        formTemplateDto = formBuilderService.getFormTemplateById(formTemplateDto.getId());

        assertNull(formTemplateDto);
    }

}
