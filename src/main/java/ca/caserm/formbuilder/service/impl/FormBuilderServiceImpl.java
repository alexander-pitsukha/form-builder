package ca.caserm.formbuilder.service.impl;

import ca.caserm.formbuilder.controller.data.FormTemplateDataView;
import ca.caserm.formbuilder.controller.data.FormView;
import ca.caserm.formbuilder.controller.data.FormTemplateView;
import ca.caserm.formbuilder.dto.FormDto;
import ca.caserm.formbuilder.dto.FormTemplateDto;
import ca.caserm.formbuilder.entity.Form;
import ca.caserm.formbuilder.entity.FormTemplate;
import ca.caserm.formbuilder.mapper.FormMapper;
import ca.caserm.formbuilder.mapper.FormTemplateMapper;
import ca.caserm.formbuilder.repositoty.FormRepository;
import ca.caserm.formbuilder.repositoty.FormTemplateRepository;
import ca.caserm.formbuilder.service.FormBuilderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FormBuilderServiceImpl service.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FormBuilderServiceImpl implements FormBuilderService {

    private final FormRepository formRepository;
    private final FormTemplateRepository formTemplateRepository;
    private final DefaultConversionService defaultConversionService;
    private final FormTemplateMapper formTemplateMapper;
    private final FormMapper formMapper;

    /**
     * Get all forms.
     *
     * @return List of FormDto
     */
    @Override
    public List<FormDto> getAllForms() {
        List<Form> forms = formRepository.findAll();
        return forms.stream().map(form -> defaultConversionService.convert(form, FormDto.class)).collect(Collectors.toList());
    }

    /**
     * Get all forms by accountId.
     *
     * @param accountId Account Id
     * @return List of FormDto
     */
    @Override
    public List<FormDto> getAllFormsByAccountId(final Long accountId) {
        List<Form> forms = formRepository.findAllByAccountId(accountId);
        return forms.stream().map(form -> defaultConversionService.convert(form, FormDto.class)).collect(Collectors.toList());
    }

    /**
     * Get all form views by accountId.
     *
     * @param accountId Account Id
     * @return List of FormView
     */
    @Override
    public List<FormView> getAllFormViewsByAccountId(final Long accountId) {
        return formRepository.findAllFormViewsByAccountId(accountId);
    }

    /**
     * Get form by id.
     *
     * @param id Form Id
     * @return FormDto
     */
    @Override
    public FormDto getFormById(final Long id) {
        var form = formRepository.findById(id).orElse(null);
        return form != null ? defaultConversionService.convert(form, FormDto.class) : null;
    }

    /**
     * Exists form by id.
     *
     * @param id Form Id
     * @return result of exists
     */
    @Override
    public boolean existsFormById(final Long id) {
        return formRepository.existsById(id);
    }

    /**
     * Save form.
     *
     * @param formDto FormDto
     * @return FormDtoDto
     */
    @Override
    @Transactional
    public FormDto saveForm(final FormDto formDto) {
        var form = defaultConversionService.convert(formDto, Form.class);
        assert form != null;
        form.setFormTemplate(formTemplateRepository.getById(formDto.getFormTemplateId()));
        return defaultConversionService.convert(formRepository.save(form), FormDto.class);
    }

    /**
     * Update form.
     *
     * @param formDto FormDto
     * @return FormDto
     */
    @Override
    @Transactional
    public FormDto updateForm(final FormDto formDto) {
        var form = formRepository.findById(formDto.getId()).orElseThrow();
        formMapper.map(formDto, form);
        form.setFormTemplate(formTemplateRepository.getById(formDto.getFormTemplateId()));
        return formDto;
    }

    /**
     * Delete form.
     *
     * @param id Integer
     */
    @Override
    @Transactional
    public void deleteForm(final Long id) {
        formRepository.deleteById(id);
    }

    /**
     * Get all form templates.
     *
     * @return List of FormTemplateDto
     */
    @Override
    public List<FormTemplateDto> getAllFormTemplates() {
        List<FormTemplate> formTemplates = formTemplateRepository.findAll();
        return formTemplates.stream().map(formTemplate -> defaultConversionService.convert(formTemplate, FormTemplateDto.class)).collect(Collectors.toList());
    }

    /**
     * Get all form template data views.
     *
     * @return List of FormTemplateDataView
     */
    @Override
    public List<FormTemplateDataView> getAllFormTemplateDataViews() {
        return formTemplateRepository.findAllFormTemplateDataViews();
    }

    /**
     * Get all form template views.
     *
     * @return List of FormTemplateView
     */
    @Override
    public List<FormTemplateView> getAllFormTemplateViews() {
        return formTemplateRepository.findAllFormTemplateViews();
    }

    /**
     * Get form template by id.
     *
     * @param id FormTemplate Id
     * @return FormTemplateDto
     */
    @Override
    public FormTemplateDto getFormTemplateById(final Long id) {
        var formTemplate = formTemplateRepository.findById(id).orElse(null);
        return formTemplate != null ? defaultConversionService.convert(formTemplate, FormTemplateDto.class) : null;
    }

    /**
     * Exists form template by id.
     *
     * @param id FormTemplate Id
     * @return result of exists
     */
    @Override
    public boolean existsFormTemplateById(final Long id) {
        return formTemplateRepository.existsById(id);
    }

    /**
     * Save form template.
     *
     * @param formTemplateDto FormTemplateDto
     * @return FormTemplateDto
     */
    @Override
    @Transactional
    public FormTemplateDto saveFormTemplate(final FormTemplateDto formTemplateDto) {
        var formTemplate = defaultConversionService.convert(formTemplateDto, FormTemplate.class);
        assert formTemplate != null;
        return defaultConversionService.convert(formTemplateRepository.save(formTemplate), FormTemplateDto.class);
    }

    /**
     * Update form template.
     *
     * @param formTemplateDto FormTemplateDto
     * @return FormTemplateDto
     */
    @Override
    @Transactional
    public FormTemplateDto updateFormTemplate(final FormTemplateDto formTemplateDto) {
        var formTemplate = formTemplateRepository.findById(formTemplateDto.getId()).orElseThrow();
        formTemplateMapper.map(formTemplateDto, formTemplate);
        return formTemplateDto;
    }

    /**
     * Update form template enabled.
     *
     * @param id      FormTemplate Id
     * @param enabled Enabled status
     */
    @Override
    @Transactional
    public void updateFormTemplateEnabled(final Long id, final Boolean enabled) {
        formTemplateRepository.updateEnabled(id, enabled, LocalDateTime.now());
    }

    /**
     * Delete form template.
     *
     * @param id FormTemplate Id
     */
    @Override
    @Transactional
    public void deleteFormTemplate(final Long id) {
        formTemplateRepository.deleteById(id);
    }

}
