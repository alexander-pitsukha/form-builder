package ca.caserm.formbuilder.controller;

import ca.caserm.formbuilder.controller.data.Constants;
import ca.caserm.formbuilder.controller.data.FormTemplateDataView;
import ca.caserm.formbuilder.controller.data.FormTemplateView;
import ca.caserm.formbuilder.controller.data.FormView;
import ca.caserm.formbuilder.controller.data.ResponseWrapper;
import ca.caserm.formbuilder.util.MessageCodeUtil;
import ca.caserm.formbuilder.dto.FormDto;
import ca.caserm.formbuilder.dto.FormTemplateDto;
import ca.caserm.formbuilder.service.FormBuilderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * FormBuilderController controller.
 */
@RestController
@RequestMapping("forms")
@RequiredArgsConstructor
public class FormBuilderController {

    private final FormBuilderService formBuilderService;
    private final MessageCodeUtil messageCodeUtil;

    /**
     * Get all forms.
     *
     * @return List of FormDto
     */
    @GetMapping("customs")
    public ResponseEntity<ResponseWrapper<List<FormDto>>> getAllForms() {
        ResponseWrapper<List<FormDto>> responseWrapper = new ResponseWrapper<>();
        List<FormDto> formDtos = formBuilderService.getAllForms();
        if (!CollectionUtils.isEmpty(formDtos)) {
            responseWrapper.setResponseEntity(formDtos);
        } else {
            responseWrapper.setError(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_NONE));
        }
        return ResponseEntity.ok(responseWrapper);
    }

    /**
     * Get all forms by accountId.
     *
     * @param accountId Account Id
     * @return List of FormDto
     */
    @GetMapping("customs/by/accountId/{accountId}")
    public ResponseEntity<ResponseWrapper<List<FormDto>>> getAllFormsByAccountId(@PathVariable Long accountId) {
        ResponseWrapper<List<FormDto>> responseWrapper = new ResponseWrapper<>();
        List<FormDto> formDtos = formBuilderService.getAllFormsByAccountId(accountId);
        if (!CollectionUtils.isEmpty(formDtos)) {
            responseWrapper.setResponseEntity(formDtos);
        } else {
            responseWrapper.setError(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_NONE));
        }
        return ResponseEntity.ok(responseWrapper);
    }

    /**
     * Get all form views by accountId.
     *
     * @param accountId Account Id
     * @return List of Form
     */
    @GetMapping("customs/views/by/accountId/{accountId}")
    public ResponseEntity<ResponseWrapper<List<FormView>>> getAllFormViewsByAccountId(@PathVariable Long accountId) {
        ResponseWrapper<List<FormView>> responseWrapper = new ResponseWrapper<>();
        List<FormView> formViews = formBuilderService.getAllFormViewsByAccountId(accountId);
        if (!CollectionUtils.isEmpty(formViews)) {
            responseWrapper.setResponseEntity(formViews);
        } else {
            responseWrapper.setError(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_NONE));
        }
        return ResponseEntity.ok(responseWrapper);
    }

    /**
     * Get form by id.
     *
     * @param id Form Id
     * @return FormDto
     */
    @GetMapping("customs/{id}")
    public ResponseEntity<ResponseWrapper<FormDto>> getFormById(@PathVariable Long id) {
        return Optional.ofNullable(formBuilderService.getFormById(id)).map(formDto -> {
            ResponseWrapper<FormDto> responseWrapper = new ResponseWrapper<>();
            responseWrapper.setResponseEntity(formDto);
            return ResponseEntity.ok(responseWrapper);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Save form.
     *
     * @param formDto FormDto
     * @return FormDto
     */
    @PostMapping("customs")
    public ResponseEntity<ResponseWrapper<FormDto>> saveForm(@RequestBody @Validated FormDto formDto) {
        ResponseWrapper<FormDto> responseWrapper = new ResponseWrapper<>();
        var entryDto = formBuilderService.saveForm(formDto);
        responseWrapper.setResponseEntity(entryDto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entryDto.getId()).toUri()).body(responseWrapper);
    }

    /**
     * Update form.
     *
     * @param id      Form id
     * @param formDto FormDto
     * @return FormDto
     */
    @PutMapping("customs/{id}")
    public ResponseEntity<ResponseWrapper<FormDto>> updateForm(@PathVariable Long id, @RequestBody @Validated FormDto formDto) {
        ResponseWrapper<FormDto> responseWrapper = new ResponseWrapper<>();
        if (!formBuilderService.existsFormById(id)) {
            responseWrapper.setError(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORM_NOT_EXIST, new Object[]{id}));
            return new ResponseEntity<>(responseWrapper, HttpStatus.BAD_REQUEST);
        }
        var entryDto = formBuilderService.updateForm(formDto);
        responseWrapper.setResponseEntity(entryDto);
        return ResponseEntity.ok(responseWrapper);
    }

    /**
     * Delete form.
     *
     * @param id Form Id
     * @return Void
     */
    @DeleteMapping("customs/{id}")
    public ResponseEntity<ResponseWrapper<HttpStatus>> deleteForm(@PathVariable Long id) {
        ResponseWrapper<HttpStatus> responseWrapper = new ResponseWrapper<>();
        if (!formBuilderService.existsFormById(id)) {
            responseWrapper.setError(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORM_NOT_EXIST, new Object[]{id}));
            return new ResponseEntity<>(responseWrapper, HttpStatus.BAD_REQUEST);
        }
        formBuilderService.deleteForm(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all form templates.
     *
     * @return List of FormTemplateDto
     */
    @GetMapping("templates")
    public ResponseEntity<ResponseWrapper<List<FormTemplateDto>>> getAllFormTemplates() {
        ResponseWrapper<List<FormTemplateDto>> responseWrapper = new ResponseWrapper<>();
        List<FormTemplateDto> formTemplateDtos = formBuilderService.getAllFormTemplates();
        if (!CollectionUtils.isEmpty(formTemplateDtos)) {
            responseWrapper.setResponseEntity(formTemplateDtos);
        } else {
            responseWrapper.setError(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_TEMPLATES_NONE));
        }
        return ResponseEntity.ok(responseWrapper);
    }

    /**
     * Get all form template data views.
     *
     * @return List of FormTemplateDataView
     */
    @GetMapping("templates/data/views/")
    public ResponseEntity<ResponseWrapper<List<FormTemplateDataView>>> getAllFormTemplateDataViews() {
        ResponseWrapper<List<FormTemplateDataView>> responseWrapper = new ResponseWrapper<>();
        List<FormTemplateDataView> formTemplateDataViews = formBuilderService.getAllFormTemplateDataViews();
        if (!CollectionUtils.isEmpty(formTemplateDataViews)) {
            responseWrapper.setResponseEntity(formTemplateDataViews);
        } else {
            responseWrapper.setError(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_TEMPLATES_NONE));
        }
        return ResponseEntity.ok(responseWrapper);
    }

    /**
     * Get all form template views.
     *
     * @return List of FormTemplateView
     */
    @GetMapping("templates/views")
    public ResponseEntity<ResponseWrapper<List<FormTemplateView>>> getAllFormTemplateViews() {
        ResponseWrapper<List<FormTemplateView>> responseWrapper = new ResponseWrapper<>();
        List<FormTemplateView> formTemplateViews = formBuilderService.getAllFormTemplateViews();
        if (!CollectionUtils.isEmpty(formTemplateViews)) {
            responseWrapper.setResponseEntity(formTemplateViews);
        } else {
            responseWrapper.setError(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_TEMPLATES_NONE));
        }
        return ResponseEntity.ok(responseWrapper);
    }

    /**
     * Get form template by id.
     *
     * @param id FormTemplate Id
     * @return FormTemplateDto
     */
    @GetMapping("templates/{id}")
    public ResponseEntity<ResponseWrapper<FormTemplateDto>> getFormTemplateById(@PathVariable Long id) {
        return Optional.ofNullable(formBuilderService.getFormTemplateById(id)).map(formTemplateDto -> {
            ResponseWrapper<FormTemplateDto> responseWrapper = new ResponseWrapper<>();
            responseWrapper.setResponseEntity(formTemplateDto);
            return ResponseEntity.ok(responseWrapper);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Save form template.
     *
     * @param formTemplateDto FormTemplateDto
     * @return FormTemplateDto
     */
    @PostMapping("templates")
    public ResponseEntity<ResponseWrapper<FormTemplateDto>> saveFormTemplate(@RequestBody @Validated FormTemplateDto formTemplateDto) {
        ResponseWrapper<FormTemplateDto> responseWrapper = new ResponseWrapper<>();
        var entryDto = formBuilderService.saveFormTemplate(formTemplateDto);
        responseWrapper.setResponseEntity(entryDto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entryDto.getId()).toUri()).body(responseWrapper);
    }

    /**
     * Update form template.
     *
     * @param id              FormTemplate Id
     * @param formTemplateDto FormTemplateDto
     * @return FormTemplateDto
     */
    @PutMapping("templates/{id}")
    public ResponseEntity<ResponseWrapper<FormTemplateDto>> updateFormTemplate(@PathVariable Long id, @RequestBody @Validated FormTemplateDto formTemplateDto) {
        ResponseWrapper<FormTemplateDto> responseWrapper = new ResponseWrapper<>();
        if (!formBuilderService.existsFormTemplateById(id)) {
            responseWrapper.setError(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_TEMPLATE_NOT_EXIST, new Object[]{id}));
            return new ResponseEntity<>(responseWrapper, HttpStatus.BAD_REQUEST);
        }
        var entryDto = formBuilderService.updateFormTemplate(formTemplateDto);
        responseWrapper.setResponseEntity(entryDto);
        return ResponseEntity.ok(responseWrapper);
    }

    /**
     * Update form template enabled.
     *
     * @param id      FormTemplate Id
     * @param enabled Enabled status
     * @return Void
     */
    @PutMapping("templates/enabled/{id}")
    public ResponseEntity<ResponseWrapper<HttpStatus>> updateFormTemplateEnabled(@PathVariable Long id, @RequestParam Boolean enabled) {
        ResponseWrapper<HttpStatus> responseWrapper = new ResponseWrapper<>();
        if (!formBuilderService.existsFormTemplateById(id)) {
            responseWrapper.setError(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_TEMPLATE_NOT_EXIST, new Object[]{id}));
            return new ResponseEntity<>(responseWrapper, HttpStatus.BAD_REQUEST);
        }
        formBuilderService.updateFormTemplateEnabled(id, enabled);
        return new ResponseEntity<>(responseWrapper, HttpStatus.NO_CONTENT);
    }

    /**
     * Delete form template.
     *
     * @param id FormTemplate Id
     * @return Void
     */
    @DeleteMapping("templates/{id}")
    public ResponseEntity<ResponseWrapper<HttpStatus>> deleteFormTemplate(@PathVariable Long id) {
        ResponseWrapper<HttpStatus> responseWrapper = new ResponseWrapper<>();
        if (!formBuilderService.existsFormTemplateById(id)) {
            responseWrapper.setError(messageCodeUtil.getFullErrorMessageByBundleCode(Constants.MSG_FORMS_TEMPLATE_NOT_EXIST, new Object[]{id}));
            return new ResponseEntity<>(responseWrapper, HttpStatus.BAD_REQUEST);
        }
        formBuilderService.deleteFormTemplate(id);
        return new ResponseEntity<>(responseWrapper, HttpStatus.NO_CONTENT);
    }

}
