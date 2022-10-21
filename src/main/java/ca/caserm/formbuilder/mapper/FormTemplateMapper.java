package ca.caserm.formbuilder.mapper;

import ca.caserm.formbuilder.dto.FormTemplateDto;
import ca.caserm.formbuilder.entity.FormTemplate;
import ca.caserm.formbuilder.entity.FormTemplateAction;
import ca.caserm.formbuilder.entity.FormTemplateEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FormTemplateMapper {

    @Autowired
    private DefaultConversionService defaultConversionService;

    public void map(FormTemplateDto formTemplateDto, FormTemplate formTemplate) {
        formTemplate.setTitle(formTemplateDto.getTitle());
        formTemplate.setDescription(formTemplateDto.getDescription());
        formTemplate.setSchema(formTemplateDto.getSchema());
        formTemplate.setAccountId(formTemplateDto.getAccountId());
        formTemplate.setThemeColor(formTemplateDto.getThemeColor());
        formTemplate.setBackgroundColor(formTemplateDto.getBackgroundColor());
        formTemplate.setTextFont(formTemplateDto.getTextFont());
        formTemplate.setEnabled(formTemplateDto.getEnabled());
        Optional.ofNullable(formTemplateDto.getFormTemplateEmailDtos()).ifPresent(dtos -> {
            final List<FormTemplateEmail> formTemplateEmails = formTemplate.getFormTemplateEmails();
            Map<Long, FormTemplateEmail> mapFormTemplateEmails = formTemplateEmails.stream().collect(Collectors.toMap(FormTemplateEmail::getId,
                    formTemplateEmail -> formTemplateEmail));
            formTemplateEmails.clear();
            dtos.forEach(dto -> {
                if (dto.getId() == null) {
                    var formTemplateEmail = defaultConversionService.convert(dto, FormTemplateEmail.class);
                    assert formTemplateEmail != null;
                    formTemplateEmail.setFormTemplate(formTemplate);
                    formTemplateEmails.add(formTemplateEmail);
                } else {
                    formTemplateEmails.add(mapFormTemplateEmails.remove(dto.getId()));
                }
            });
        });
        Optional.ofNullable(formTemplateDto.getFormTemplateActionDtos()).ifPresent(dtos -> {
            final List<FormTemplateAction> formTemplateActions = formTemplate.getFormTemplateActions();
            Map<Long, FormTemplateAction> mapFormTemplateActions = formTemplateActions.stream().collect(Collectors.toMap(FormTemplateAction::getId,
                    formTemplateAction -> formTemplateAction));
            formTemplateActions.clear();
            dtos.forEach(dto -> {
                if (dto.getId() == null) {
                    var formTemplateAction = defaultConversionService.convert(dto, FormTemplateAction.class);
                    assert formTemplateAction != null;
                    formTemplateAction.setFormTemplate(formTemplate);
                    formTemplateActions.add(formTemplateAction);
                } else {
                    formTemplateActions.add(mapFormTemplateActions.remove(dto.getId()));
                }
            });
        });
    }

}
