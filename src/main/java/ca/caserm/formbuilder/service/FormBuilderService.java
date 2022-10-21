package ca.caserm.formbuilder.service;

import ca.caserm.formbuilder.controller.data.FormTemplateDataView;
import ca.caserm.formbuilder.controller.data.FormView;
import ca.caserm.formbuilder.controller.data.FormTemplateView;
import ca.caserm.formbuilder.dto.FormDto;
import ca.caserm.formbuilder.dto.FormTemplateDto;

import java.util.List;

public interface FormBuilderService {

    List<FormDto> getAllForms();

    List<FormDto> getAllFormsByAccountId(Long accountId);

    List<FormView> getAllFormViewsByAccountId(Long accountId);

    FormDto getFormById(Long id);

    boolean existsFormById(Long id);

    FormDto saveForm(FormDto formDto);

    FormDto updateForm(FormDto formDto);

    void deleteForm(Long id);

    List<FormTemplateDto> getAllFormTemplates();

    List<FormTemplateDataView> getAllFormTemplateDataViews();

    List<FormTemplateView> getAllFormTemplateViews();

    FormTemplateDto getFormTemplateById(Long id);

    boolean existsFormTemplateById(Long id);

    FormTemplateDto saveFormTemplate(FormTemplateDto formTemplateDto);

    FormTemplateDto updateFormTemplate(FormTemplateDto formTemplateDto);

    void updateFormTemplateEnabled(Long id, Boolean enabled);

    void deleteFormTemplate(Long id);

}
