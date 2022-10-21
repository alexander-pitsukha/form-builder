package ca.caserm.formbuilder.service;

import ca.caserm.formbuilder.AbstractTests;
import ca.caserm.formbuilder.controller.data.FormTemplateDataView;
import ca.caserm.formbuilder.controller.data.FormView;
import ca.caserm.formbuilder.controller.data.FormTemplateView;
import ca.caserm.formbuilder.dto.FormDto;
import ca.caserm.formbuilder.dto.FormTemplateActionDto;
import ca.caserm.formbuilder.dto.FormTemplateDto;
import ca.caserm.formbuilder.dto.FormTemplateEmailDto;
import ca.caserm.formbuilder.entity.Form;
import ca.caserm.formbuilder.entity.FormTemplate;
import ca.caserm.formbuilder.mapper.FormMapper;
import ca.caserm.formbuilder.mapper.FormTemplateMapper;
import ca.caserm.formbuilder.repositoty.FormRepository;
import ca.caserm.formbuilder.repositoty.FormTemplateRepository;
import ca.caserm.formbuilder.service.impl.FormBuilderServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class FormBuilderServiceImplTests extends AbstractTests {

    @Autowired
    private FormBuilderService formBuilderService;

    @MockBean
    private FormRepository mockFormRepository;

    @MockBean
    private FormTemplateRepository mockFormTemplateRepository;

    @MockBean
    private DefaultConversionService defaultConversionService;

    @MockBean
    private FormTemplateMapper formTemplateMapper;

    @MockBean
    private FormMapper formMapper;

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public FormBuilderService formBuilderService(FormRepository mockFormRepository, FormTemplateRepository mockFormTemplateRepository,
                                                     DefaultConversionService defaultConversionService, FormMapper formMapper, FormTemplateMapper formTemplateMapper) {
            return new FormBuilderServiceImpl(mockFormRepository, mockFormTemplateRepository, defaultConversionService, formTemplateMapper, formMapper);
        }

    }

    @Test
    void testGetAllForms() throws IOException {
        List<FormDto> createdFormDtos = getObjectMapper().readValue(new ClassPathResource("json/form_dtos.json").getInputStream(), new TypeReference<>() {
        });

        when(mockFormRepository.findAll()).thenReturn(getObjectMapper().readValue(new ClassPathResource("json/forms.json").getInputStream(), new TypeReference<>() {
        }));
        when(defaultConversionService.convert(any(Form.class), eq(FormDto.class))).thenReturn(createdFormDtos.get(0), createdFormDtos.get(1));

        List<FormDto> formDtos = formBuilderService.getAllForms();

        assertNotNull(formDtos);
        assertEquals(2, formDtos.size());
        assertEquals(createdFormDtos.get(0).getId(), formDtos.get(0).getId());
        assertEquals(createdFormDtos.get(1).getId(), formDtos.get(1).getId());

        verify(mockFormRepository).findAll();
        verify(defaultConversionService, times(2)).convert(any(Form.class), eq(FormDto.class));
    }

    @Test
    void testGetAllFormsByAccountId() throws IOException {
        List<FormDto> createdFormDtos = getObjectMapper().readValue(new ClassPathResource("json/form_dtos.json").getInputStream(), new TypeReference<>() {
        });

        when(mockFormRepository.findAllByAccountId(anyLong())).thenReturn(getObjectMapper().readValue(new ClassPathResource("json/forms.json").getInputStream(), new TypeReference<>() {
        }));
        when(defaultConversionService.convert(any(Form.class), eq(FormDto.class))).thenReturn(createdFormDtos.get(0), createdFormDtos.get(1));

        List<FormDto> formDtos = formBuilderService.getAllFormsByAccountId(anyLong());

        assertNotNull(formDtos);
        assertEquals(2, formDtos.size());
        assertEquals(createdFormDtos.get(0).getId(), formDtos.get(0).getId());
        assertEquals(createdFormDtos.get(1).getId(), formDtos.get(1).getId());

        verify(mockFormRepository).findAllByAccountId(anyLong());
        verify(defaultConversionService, times(2)).convert(any(Form.class), eq(FormDto.class));
    }

    @Test
    void testFindAllFormViewsByAccountId() throws IOException {
        List<FormView> createdFormViews = Stream.of(
                        getObjectFromJson("json/form_with_id.json", Form.class),
                        getObjectFromJson("json/form_with_id.json", Form.class))
                .map(form -> new FormView() {

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
                }).collect(Collectors.toList());

        when(mockFormRepository.findAllFormViewsByAccountId(anyLong())).thenReturn(createdFormViews);

        List<FormView> formViews = formBuilderService.getAllFormViewsByAccountId(anyLong());

        assertNotNull(formViews);
        assertEquals(2, formViews.size());
        assertEquals(createdFormViews.get(0).getId(), formViews.get(0).getId());
        assertEquals(createdFormViews.get(0).getTitle(), formViews.get(0).getTitle());
        assertEquals(createdFormViews.get(0).getDescription(), formViews.get(0).getDescription());
        assertEquals(createdFormViews.get(0).getCreateDate(), formViews.get(0).getCreateDate());
        assertEquals(createdFormViews.get(1).getId(), formViews.get(1).getId());
        assertEquals(createdFormViews.get(1).getTitle(), formViews.get(1).getTitle());
        assertEquals(createdFormViews.get(1).getDescription(), formViews.get(1).getDescription());
        assertEquals(createdFormViews.get(1).getCreateDate(), formViews.get(1).getCreateDate());

        verify(mockFormRepository).findAllFormViewsByAccountId(anyLong());
    }

    @Test
    void testGetFormById() throws IOException {
        FormDto createdFormDto = getObjectFromJson("json/form_dto.json", FormDto.class);

        when(mockFormRepository.findById(anyLong())).thenReturn(Optional.of(getObjectFromJson("json/form_with_id.json", Form.class)));
        when(defaultConversionService.convert(any(Form.class), eq(FormDto.class))).thenReturn(createdFormDto);

        FormDto formDto = formBuilderService.getFormById(anyLong());

        assertNotNull(formDto);
        assertEquals(createdFormDto.getId(), formDto.getId());
        assertEquals(createdFormDto.getTitle(), formDto.getTitle());
        assertEquals(createdFormDto.getDescription(), formDto.getDescription());
        assertEquals(createdFormDto.getSchema(), formDto.getSchema());
        assertEquals(createdFormDto.getAccountId(), formDto.getAccountId());
        assertEquals(createdFormDto.getCreateDate(), formDto.getCreateDate());
        assertEquals(createdFormDto.getUpdateDate(), formDto.getUpdateDate());
        assertEquals(createdFormDto.getThemeColor(), formDto.getThemeColor());
        assertEquals(createdFormDto.getBackgroundColor(), formDto.getBackgroundColor());
        assertEquals(createdFormDto.getTextFont(), formDto.getTextFont());
        assertEquals(createdFormDto.getFormTemplateId(), formDto.getFormTemplateId());

        verify(mockFormRepository).findById(anyLong());
        verify(defaultConversionService).convert(any(Form.class), eq(FormDto.class));
    }

    @Test
    void testExistsFormById() {
        when(mockFormRepository.existsById(anyLong())).thenReturn(true);

        assertTrue(formBuilderService.existsFormById(1L));
    }

    @Test
    void testGetAllFormTemplates() throws IOException {
        List<FormTemplateDto> createdFormTemplateDtos = getObjectMapper().readValue(new ClassPathResource("json/form_template_dtos.json").getInputStream(), new TypeReference<>() {
        });

        when(mockFormTemplateRepository.findAll()).thenReturn(getObjectMapper().readValue(new ClassPathResource("json/form_templates.json").getInputStream(), new TypeReference<>() {
        }));
        when(defaultConversionService.convert(any(FormTemplate.class), eq(FormTemplateDto.class))).thenReturn(createdFormTemplateDtos.get(0), createdFormTemplateDtos.get(1));

        List<FormTemplateDto> formTemplateDtos = formBuilderService.getAllFormTemplates();

        assertNotNull(formTemplateDtos);
        assertEquals(2, formTemplateDtos.size());
        assertEquals(createdFormTemplateDtos.get(0).getId(), formTemplateDtos.get(0).getId());
        assertEquals(createdFormTemplateDtos.get(1).getId(), formTemplateDtos.get(1).getId());

        verify(mockFormTemplateRepository).findAll();
        verify(defaultConversionService, times(2)).convert(any(FormTemplate.class), eq(FormTemplateDto.class));
    }

    @Test
    void testGetAllFormTemplateDataViews() throws IOException {
        List<FormTemplateDataView> createdFormTemplateDataViews = Stream.of(
                        getObjectFromJson("json/form_template_with_id.json", FormTemplate.class),
                        getObjectFromJson("json/form_template_with_id.json", FormTemplate.class))
                .map(formTemplate -> new FormTemplateDataView() {

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
                }).collect(Collectors.toList());

        when(mockFormTemplateRepository.findAllFormTemplateDataViews()).thenReturn(createdFormTemplateDataViews);

        List<FormTemplateDataView> formTemplateDataViews = formBuilderService.getAllFormTemplateDataViews();

        assertNotNull(formTemplateDataViews);
        assertEquals(2, formTemplateDataViews.size());
        assertEquals(createdFormTemplateDataViews.get(0).getId(), formTemplateDataViews.get(0).getId());
        assertEquals(createdFormTemplateDataViews.get(0).getTitle(), formTemplateDataViews.get(0).getTitle());
        assertEquals(createdFormTemplateDataViews.get(0).getCreateDate(), formTemplateDataViews.get(0).getCreateDate());
        assertEquals(createdFormTemplateDataViews.get(0).getEnabled(), formTemplateDataViews.get(0).getEnabled());
        assertEquals(createdFormTemplateDataViews.get(1).getId(), formTemplateDataViews.get(1).getId());
        assertEquals(createdFormTemplateDataViews.get(1).getTitle(), formTemplateDataViews.get(1).getTitle());
        assertEquals(createdFormTemplateDataViews.get(1).getCreateDate(), formTemplateDataViews.get(1).getCreateDate());
        assertEquals(createdFormTemplateDataViews.get(1).getEnabled(), formTemplateDataViews.get(1).getEnabled());

        verify(mockFormTemplateRepository).findAllFormTemplateDataViews();
    }

    @Test
    void testGetAllFormTemplateViews() throws IOException {
        List<FormTemplateView> createdFormTemplateViews = Stream.of(
                        getObjectFromJson("json/form_template_with_id.json", FormTemplate.class),
                        getObjectFromJson("json/form_template_with_id.json", FormTemplate.class))
                .map(formTemplate -> new FormTemplateView() {

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
                }).collect(Collectors.toList());

        when(mockFormTemplateRepository.findAllFormTemplateViews()).thenReturn(createdFormTemplateViews);

        List<FormTemplateView> formTemplateViews = formBuilderService.getAllFormTemplateViews();

        assertNotNull(formTemplateViews);
        assertEquals(2, formTemplateViews.size());
        assertEquals(createdFormTemplateViews.get(0).getId(), formTemplateViews.get(0).getId());
        assertEquals(createdFormTemplateViews.get(0).getTitle(), formTemplateViews.get(0).getTitle());
        assertEquals(createdFormTemplateViews.get(0).getDescription(), formTemplateViews.get(0).getDescription());
        assertEquals(createdFormTemplateViews.get(1).getId(), formTemplateViews.get(1).getId());
        assertEquals(createdFormTemplateViews.get(1).getTitle(), formTemplateViews.get(1).getTitle());
        assertEquals(createdFormTemplateViews.get(1).getDescription(), formTemplateViews.get(1).getDescription());

        verify(mockFormTemplateRepository).findAllFormTemplateViews();
    }

    @Test
    void testGetFormTemplateById() throws IOException {
        FormTemplateDto createdFormTemplate = getObjectFromJson("json/form_template_dto_with_id.json", FormTemplateDto.class);

        when(mockFormTemplateRepository.findById(anyLong())).thenReturn(Optional.of(getObjectFromJson("json/form_template_with_id.json", FormTemplate.class)));
        when(defaultConversionService.convert(any(FormTemplate.class), eq(FormTemplateDto.class))).thenReturn(createdFormTemplate);

        FormTemplateDto formTemplate = formBuilderService.getFormTemplateById(anyLong());

        assertNotNull(formTemplate);
        assertEquals(createdFormTemplate.getId(), formTemplate.getId());
        assertEquals(createdFormTemplate.getTitle(), formTemplate.getTitle());
        assertEquals(createdFormTemplate.getDescription(), formTemplate.getDescription());
        assertEquals(createdFormTemplate.getSchema(), formTemplate.getSchema());
        assertEquals(createdFormTemplate.getAccountId(), formTemplate.getAccountId());
        assertEquals(createdFormTemplate.getCreateDate(), formTemplate.getCreateDate());
        assertEquals(createdFormTemplate.getUpdateDate(), formTemplate.getUpdateDate());
        assertEquals(createdFormTemplate.getThemeColor(), formTemplate.getThemeColor());
        assertEquals(createdFormTemplate.getBackgroundColor(), formTemplate.getBackgroundColor());
        assertEquals(createdFormTemplate.getTextFont(), formTemplate.getTextFont());
        assertEquals(createdFormTemplate.getEnabled(), formTemplate.getEnabled());

        verify(mockFormTemplateRepository).findById(anyLong());
        verify(defaultConversionService).convert(any(FormTemplate.class), eq(FormTemplateDto.class));
    }

    @Test
    void testGetFormTemplateByIdWithActions() throws IOException {
        FormTemplateDto createdFormTemplateDto = getObjectFromJson("json/form_template_dto_with_id.json", FormTemplateDto.class);
        FormTemplateEmailDto formTemplateEmailDto = getObjectFromJson("json/form_template_email_dto_with_id.json", FormTemplateEmailDto.class);
        formTemplateEmailDto.setFormTemplateId(createdFormTemplateDto.getId());
        FormTemplateActionDto formTemplateActionDto = getObjectFromJson("json/form_template_action_dto_with_id.json", FormTemplateActionDto.class);
        formTemplateActionDto.setFormTemplateId(createdFormTemplateDto.getId());

        createdFormTemplateDto.setFormTemplateEmailDtos(Collections.singletonList(formTemplateEmailDto));
        createdFormTemplateDto.setFormTemplateActionDtos(Collections.singletonList(formTemplateActionDto));

        when(mockFormTemplateRepository.findById(anyLong())).thenReturn(Optional.of(getObjectFromJson("json/form_template_with_id.json", FormTemplate.class)));
        when(defaultConversionService.convert(any(FormTemplate.class), eq(FormTemplateDto.class))).thenReturn(createdFormTemplateDto);

        FormTemplateDto formTemplateDto = formBuilderService.getFormTemplateById(anyLong());

        assertNotNull(formTemplateDto);
        assertEquals(createdFormTemplateDto.getId(), formTemplateDto.getId());
        assertEquals(createdFormTemplateDto.getTitle(), formTemplateDto.getTitle());
        assertEquals(createdFormTemplateDto.getDescription(), formTemplateDto.getDescription());
        assertEquals(createdFormTemplateDto.getFormTemplateEmailDtos().size(), formTemplateDto.getFormTemplateEmailDtos().size());
        assertEquals(createdFormTemplateDto.getFormTemplateActionDtos().size(), formTemplateDto.getFormTemplateActionDtos().size());

        verify(mockFormTemplateRepository).findById(anyLong());
        verify(defaultConversionService).convert(any(FormTemplate.class), eq(FormTemplateDto.class));
    }

    @Test
    void testExistsFormTemplateById() {
        when(mockFormTemplateRepository.existsById(anyLong())).thenReturn(true);

        assertTrue(formBuilderService.existsFormTemplateById(1L));
    }

}
