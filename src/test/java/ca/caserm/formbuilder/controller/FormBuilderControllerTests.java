package ca.caserm.formbuilder.controller;

import ca.caserm.formbuilder.AbstractTests;
import ca.caserm.formbuilder.controller.data.Constants;
import ca.caserm.formbuilder.controller.data.FormTemplateDataView;
import ca.caserm.formbuilder.controller.data.FormTemplateView;
import ca.caserm.formbuilder.controller.data.FormView;
import ca.caserm.formbuilder.util.MessageCodeUtil;
import ca.caserm.formbuilder.dto.FormDto;
import ca.caserm.formbuilder.dto.FormTemplateDto;
import ca.caserm.formbuilder.entity.Form;
import ca.caserm.formbuilder.entity.FormTemplate;
import ca.caserm.formbuilder.service.FormBuilderService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FormBuilderController.class)
@Import(FormBuilderControllerTests.FormBuilderControllerTestsConfig.class)
@WithMockUser
class FormBuilderControllerTests extends AbstractTests {

    private static final Long ID = nextLong();

    @MockBean
    private FormBuilderService mockFormBuilderService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MessageCodeUtil messageCodeUtil;

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class FormBuilderControllerTestsConfig {

        @Bean
        public MessageCodeUtil messageCodeUtil(MessageSource messageSource) {
            return new MessageCodeUtil(messageSource);
        }
    }

    @Test
    void testGetAllForms() throws Exception {
        List<FormDto> formDtos = getObjectMapper().readValue(new ClassPathResource("json/form_dtos.json").getInputStream(), new TypeReference<>() {
        });

        given(mockFormBuilderService.getAllForms()).willReturn(formDtos);

        mockMvc.perform(get("/forms/customs/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseEntity.*", hasSize(2)))
                .andExpect(jsonPath("$.responseEntity[0].id", is(formDtos.get(0).getId())))
                .andExpect(jsonPath("$.responseEntity[1].id", is(formDtos.get(1).getId())));

        verify(mockFormBuilderService).getAllForms();
    }

    @Test
    void testGetAllForms_ErrorMessage() throws Exception {
        given(mockFormBuilderService.getAllForms()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/forms/customs/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", is(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_NONE))));

        verify(mockFormBuilderService).getAllForms();
    }

    @Test
    void testGetAllFormsByAccountId() throws Exception {
        List<FormDto> formDtos = getObjectMapper().readValue(new ClassPathResource("json/form_dtos.json").getInputStream(), new TypeReference<>() {
        });

        given(mockFormBuilderService.getAllFormsByAccountId(anyLong())).willReturn(formDtos);

        mockMvc.perform(get("/forms/customs/by/accountId/{accountId}", ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseEntity.*", hasSize(2)))
                .andExpect(jsonPath("$.responseEntity[0].accountId", is(formDtos.get(0).getAccountId())))
                .andExpect(jsonPath("$.responseEntity[1].accountId", is(formDtos.get(1).getAccountId())));

        verify(mockFormBuilderService).getAllFormsByAccountId(anyLong());
    }

    @Test
    void testGetAllFormsByAccountId_ErrorMessage() throws Exception {
        given(mockFormBuilderService.getAllFormsByAccountId(anyLong())).willReturn(Collections.emptyList());

        mockMvc.perform(get("/forms/customs/by/accountId/{accountId}", ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", is(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_NONE))));

        verify(mockFormBuilderService).getAllFormsByAccountId(anyLong());
    }

    @Test
    void testGetAllFormViewsByAccountId() throws Exception {
        Form form = getObjectFromJson("json/form_with_id.json", Form.class);
        List<FormView> formViews = Collections.singletonList(new FormView() {
            @Override
            public Long getId() {
                return form.getId();
            }

            @Override
            public String getTitle() {
                return form.getTitle();
            }

            @Override
            public String getDescription() {
                return form.getDescription();
            }

            @Override
            public LocalDateTime getCreateDate() {
                return form.getCreateDate();
            }
        });
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);

        given(mockFormBuilderService.getAllFormViewsByAccountId(anyLong())).willReturn(formViews);

        mockMvc.perform(get("/forms/customs/views/by/accountId/{accountId}", ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseEntity.*", hasSize(1)))
                .andExpect(jsonPath("$.responseEntity[0].id", is(form.getId())))
                .andExpect(jsonPath("$.responseEntity[0].title", is(form.getTitle())))
                .andExpect(jsonPath("$.responseEntity[0].description", is(form.getDescription())))
                .andExpect(jsonPath("$.responseEntity[0].createDate", is(form.getCreateDate().format(formatter))));

        verify(mockFormBuilderService).getAllFormViewsByAccountId(anyLong());
    }

    @Test
    void testGetAllFormViewsByAccountId_ErrorMessage() throws Exception {
        given(mockFormBuilderService.getAllFormViewsByAccountId(anyLong())).willReturn(Collections.emptyList());

        mockMvc.perform(get("/forms/customs/views/by/accountId/{accountId}", ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", is(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_NONE))));

        verify(mockFormBuilderService).getAllFormViewsByAccountId(anyLong());
    }

    @Test
    void testGetFormById() throws Exception {
        FormDto formDto = getObjectFromJson("json/form_dto_with_id.json", FormDto.class);

        given(mockFormBuilderService.getFormById(anyLong())).willReturn(formDto);

        mockMvc.perform(get("/forms/customs/{id}", formDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseEntity.id", is(formDto.getId())));

        verify(mockFormBuilderService).getFormById(anyLong());
    }

    @Test
    void testGetFormById_NotFound() throws Exception {
        given(mockFormBuilderService.getFormById(anyLong())).willReturn(null);

        mockMvc.perform(get("/forms/customs/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(mockFormBuilderService).getFormById(anyLong());
    }

    @Test
    void testSaveForm() throws Exception {
        FormDto formDto = getObjectFromJson("json/form_dto_with_id.json", FormDto.class);

        given(mockFormBuilderService.saveForm(any(FormDto.class))).willReturn(formDto);

        mockMvc.perform(post("/forms/customs/")
                        .with(csrf())
                        .content(asJsonString(formDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, containsString(formDto.getId().toString())))
                .andExpect(jsonPath("$.responseEntity.id", is(formDto.getId())))
                .andExpect(jsonPath("$.responseEntity.title", is(formDto.getTitle())));

        verify(mockFormBuilderService).saveForm(any(FormDto.class));
    }

    @Test
    void testUpdateForm() throws Exception {
        FormDto formDto = getObjectFromJson("json/form_dto_with_id.json", FormDto.class);

        given(mockFormBuilderService.existsFormById(anyLong())).willReturn(true);
        given(mockFormBuilderService.updateForm(any(FormDto.class))).willReturn(formDto);

        mockMvc.perform(put("/forms/customs/{id}", formDto.getId())
                        .with(csrf())
                        .content(asJsonString(formDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseEntity.id", is(formDto.getId())))
                .andExpect(jsonPath("$.responseEntity.title", is(formDto.getTitle())));

        verify(mockFormBuilderService).existsFormById(anyLong());
        verify(mockFormBuilderService).updateForm(any(FormDto.class));
    }

    @Test
    void testUpdateForm_BadRequest() throws Exception {
        given(mockFormBuilderService.existsFormById(anyLong())).willReturn(false);

        mockMvc.perform(put("/forms/customs/{id}", ID)
                        .with(csrf())
                        .content(asJsonString(getObjectFromJson("json/form_dto_with_id.json", FormDto.class)))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORM_NOT_EXIST, new Object[]{ID}))));

        verify(mockFormBuilderService).existsFormById(anyLong());
    }

    @Test
    void testDeleteForm() throws Exception {
        given(mockFormBuilderService.existsFormById(anyLong())).willReturn(true);
        willDoNothing().given(mockFormBuilderService).deleteForm(anyLong());

        mockMvc.perform(delete("/forms/customs/{id}", ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(mockFormBuilderService).existsFormById(anyLong());
        verify(mockFormBuilderService).deleteForm(anyLong());
    }

    @Test
    void testDeleteForm_BadRequest() throws Exception {
        given(mockFormBuilderService.existsFormById(anyLong())).willReturn(false);

        mockMvc.perform(delete("/forms/customs/{id}", ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORM_NOT_EXIST, new Object[]{ID}))));

        verify(mockFormBuilderService).existsFormById(anyLong());
    }

    @Test
    void testGetAllFormTemplates() throws Exception {
        List<FormTemplateDto> formTemplateDtos = getObjectMapper().readValue(new ClassPathResource("json/form_template_dtos.json").getInputStream(), new TypeReference<>() {
        });

        given(mockFormBuilderService.getAllFormTemplates()).willReturn(formTemplateDtos);

        mockMvc.perform(get("/forms/templates/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseEntity.*", hasSize(2)))
                .andExpect(jsonPath("$.responseEntity[0].id", is(formTemplateDtos.get(0).getId())))
                .andExpect(jsonPath("$.responseEntity[1].id", is(formTemplateDtos.get(1).getId())));

        verify(mockFormBuilderService).getAllFormTemplates();
    }

    @Test
    void testGetAllFormTemplates_ErrorMessage() throws Exception {
        given(mockFormBuilderService.getAllFormTemplates()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/forms/templates/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", is(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_TEMPLATES_NONE))));

        verify(mockFormBuilderService).getAllFormTemplates();
    }

    @Test
    void testGetAllFormTemplateDataViews() throws Exception {
        FormTemplate formTemplate = getObjectFromJson("json/form_template_with_id.json", FormTemplate.class);
        List<FormTemplateDataView> formTemplateDataViews = Collections.singletonList(new FormTemplateDataView() {
            @Override
            public Long getId() {
                return formTemplate.getId();
            }

            @Override
            public String getTitle() {
                return formTemplate.getTitle();
            }

            @Override
            public LocalDateTime getCreateDate() {
                return formTemplate.getCreateDate();
            }

            @Override
            public Boolean getEnabled() {
                return formTemplate.getEnabled();
            }
        });
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);

        given(mockFormBuilderService.getAllFormTemplateDataViews()).willReturn(formTemplateDataViews);

        mockMvc.perform(get("/forms/templates/data/views/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseEntity.*", hasSize(1)))
                .andExpect(jsonPath("$.responseEntity[0].id", is(formTemplate.getId())))
                .andExpect(jsonPath("$.responseEntity[0].title", is(formTemplate.getTitle())))
                .andExpect(jsonPath("$.responseEntity[0].createDate", is(formTemplate.getCreateDate().format(formatter))))
                .andExpect(jsonPath("$.responseEntity[0].enabled", is(formTemplate.getEnabled())));

        verify(mockFormBuilderService).getAllFormTemplateDataViews();
    }

    @Test
    void testGetAllFormTemplateDataViews_ErrorMessage() throws Exception {
        given(mockFormBuilderService.getAllFormTemplateDataViews()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/forms/templates/data/views/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", is(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_TEMPLATES_NONE))));

        verify(mockFormBuilderService).getAllFormTemplateDataViews();
    }

    @Test
    void testGetAllFormTemplateViews() throws Exception {
        FormTemplate formTemplate = getObjectFromJson("json/form_template_with_id.json", FormTemplate.class);
        List<FormTemplateView> formTemplateViews = Collections.singletonList(new FormTemplateView() {
            @Override
            public Long getId() {
                return formTemplate.getId();
            }

            @Override
            public String getTitle() {
                return formTemplate.getTitle();
            }

            @Override
            public String getDescription() {
                return formTemplate.getDescription();
            }
        });

        given(mockFormBuilderService.getAllFormTemplateViews()).willReturn(formTemplateViews);

        mockMvc.perform(get("/forms/templates/views")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseEntity.*", hasSize(1)))
                .andExpect(jsonPath("$.responseEntity[0].id", is(formTemplate.getId())))
                .andExpect(jsonPath("$.responseEntity[0].title", is(formTemplate.getTitle())))
                .andExpect(jsonPath("$.responseEntity[0].description", is(formTemplate.getDescription())));

        verify(mockFormBuilderService).getAllFormTemplateViews();
    }

    @Test
    void testGetAllFormTemplateViews_ErrorMessage() throws Exception {
        given(mockFormBuilderService.getAllFormTemplateViews()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/forms/templates/views")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", is(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_TEMPLATES_NONE))));

        verify(mockFormBuilderService).getAllFormTemplateViews();
    }

    @Test
    void testGetFormTemplateById() throws Exception {
        FormTemplateDto formTemplateDto = getObjectFromJson("json/form_template_dto_with_id.json", FormTemplateDto.class);

        given(mockFormBuilderService.getFormTemplateById(anyLong())).willReturn(formTemplateDto);

        mockMvc.perform(get("/forms/templates/{id}", formTemplateDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseEntity.id", is(formTemplateDto.getId())))
                .andExpect(jsonPath("$.responseEntity.title", is(formTemplateDto.getTitle())));

        verify(mockFormBuilderService).getFormTemplateById(anyLong());
    }

    @Test
    void testGetFormTemplateById_NotFound() throws Exception {
        given(mockFormBuilderService.getFormTemplateById(anyLong())).willReturn(null);

        mockMvc.perform(get("/forms/templates/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(mockFormBuilderService).getFormTemplateById(anyLong());
    }

    @Test
    void testSaveFormTemplate() throws Exception {
        FormTemplateDto formTemplateDto = getObjectFromJson("json/form_template_dto_with_id.json", FormTemplateDto.class);

        given(mockFormBuilderService.saveFormTemplate(any(FormTemplateDto.class))).willReturn(formTemplateDto);

        mockMvc.perform(post("/forms/templates/")
                        .with(csrf())
                        .content(asJsonString(getObjectFromJson("json/form_template_dto.json", FormTemplateDto.class)))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, containsString(formTemplateDto.getId().toString())))
                .andExpect(jsonPath("$.responseEntity.id", is(formTemplateDto.getId())))
                .andExpect(jsonPath("$.responseEntity.title", is(formTemplateDto.getTitle())));

        verify(mockFormBuilderService).saveFormTemplate(any(FormTemplateDto.class));
    }

    @Test
    void testUpdateFormTemplate() throws Exception {
        FormTemplateDto formTemplateDto = getObjectFromJson("json/form_template_dto.json", FormTemplateDto.class);
        formTemplateDto.setId(Long.MAX_VALUE);

        given(mockFormBuilderService.existsFormTemplateById(anyLong())).willReturn(true);
        given(mockFormBuilderService.updateFormTemplate(any(FormTemplateDto.class))).willReturn(formTemplateDto);

        mockMvc.perform(put("/forms/templates/{id}", formTemplateDto.getId())
                        .with(csrf())
                        .content(asJsonString(formTemplateDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseEntity.id", is(formTemplateDto.getId())))
                .andExpect(jsonPath("$.responseEntity.title", is(formTemplateDto.getTitle())));

        verify(mockFormBuilderService).existsFormTemplateById(anyLong());
        verify(mockFormBuilderService).updateFormTemplate(any(FormTemplateDto.class));
    }

    @Test
    void testUpdateFormTemplate_BadRequest() throws Exception {
        FormTemplateDto formTemplateDto = getObjectFromJson("json/form_template_dto.json", FormTemplateDto.class);
        formTemplateDto.setId(1L);

        given(mockFormBuilderService.existsFormTemplateById(anyLong())).willReturn(false);

        mockMvc.perform(put("/forms/templates/{id}", ID)
                        .with(csrf())
                        .content(asJsonString(formTemplateDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_TEMPLATE_NOT_EXIST, new Object[]{ID}))));

        verify(mockFormBuilderService).existsFormTemplateById(anyLong());
    }

    @Test
    void testUpdateFormTemplateEnabled() throws Exception {
        given(mockFormBuilderService.existsFormTemplateById(anyLong())).willReturn(true);
        willDoNothing().given(mockFormBuilderService).updateFormTemplateEnabled(anyLong(), anyBoolean());

        mockMvc.perform(put("/forms/templates/enabled/{id}", ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("enabled", String.valueOf(nextBoolean())))
                .andExpect(status().isNoContent());

        verify(mockFormBuilderService).existsFormTemplateById(anyLong());
        verify(mockFormBuilderService).updateFormTemplateEnabled(anyLong(), anyBoolean());
    }

    @Test
    void testUpdateFormTemplateEnabled_BadRequest() throws Exception {
        given(mockFormBuilderService.existsFormTemplateById(anyLong())).willReturn(false);

        mockMvc.perform(put("/forms/templates/enabled/{id}", ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("enabled", String.valueOf(nextBoolean())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_TEMPLATE_NOT_EXIST, new Object[]{ID}))));

        verify(mockFormBuilderService).existsFormTemplateById(anyLong());
    }

    @Test
    void testDeleteFormTemplate() throws Exception {
        given(mockFormBuilderService.existsFormTemplateById(anyLong())).willReturn(true);
        willDoNothing().given(mockFormBuilderService).deleteFormTemplate(anyLong());

        mockMvc.perform(delete("/forms/templates/{id}", ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(mockFormBuilderService).existsFormTemplateById(anyLong());
        verify(mockFormBuilderService).deleteFormTemplate(anyLong());
    }

    @Test
    void testDeleteFormTemplate_BadRequest() throws Exception {
        given(mockFormBuilderService.existsFormTemplateById(anyLong())).willReturn(false);

        mockMvc.perform(delete("/forms/templates/{id}", ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_TEMPLATE_NOT_EXIST, new Object[]{ID}))));

        verify(mockFormBuilderService).existsFormTemplateById(anyLong());
    }

    private String asJsonString(final Object obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
