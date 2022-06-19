package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.dto.FormTemplateDto;
import ca.caserm.formbuilder.entity.FormTemplate;
import ca.caserm.formbuilder.entity.FormTemplateAction;
import ca.caserm.formbuilder.entity.FormTemplateEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component(DtoToFormTemplateConverter.COMPONENT_NAME)
public class DtoToFormTemplateConverter implements Converter<FormTemplateDto, FormTemplate> {

    public static final String COMPONENT_NAME = "dtoToFormTemplateConverter";

    @Autowired
    @Lazy
    private DefaultConversionService defaultConversionService;

    @Override
    public FormTemplate convert(FormTemplateDto source) {
        final var formTemplate = new FormTemplate();
        formTemplate.setTitle(source.getTitle());
        formTemplate.setDescription(source.getDescription());
        formTemplate.setSchema(source.getSchema());
        formTemplate.setAccountId(source.getAccountId());
        formTemplate.setThemeColor(source.getThemeColor());
        formTemplate.setBackgroundColor(source.getBackgroundColor());
        formTemplate.setTextFont(source.getTextFont());
        formTemplate.setEnabled(source.getEnabled());
        final List<FormTemplateEmail> formTemplateEmails = new ArrayList<>();
        Optional.ofNullable(source.getFormTemplateEmailDtos()).ifPresent(dtos -> dtos.forEach(dto -> {
            var formTemplateEmail = defaultConversionService.convert(dto, FormTemplateEmail.class);
            assert formTemplateEmail != null;
            formTemplateEmail.setFormTemplate(formTemplate);
            formTemplateEmails.add(formTemplateEmail);
        }));
        formTemplate.setFormTemplateEmails(formTemplateEmails);
        final List<FormTemplateAction> formTemplateActions = new ArrayList<>();
        Optional.ofNullable(source.getFormTemplateActionDtos()).ifPresent(dtos -> dtos.forEach(dto -> {
            var formTemplateAction = defaultConversionService.convert(dto, FormTemplateAction.class);
            assert formTemplateAction != null;
            formTemplateAction.setFormTemplate(formTemplate);
            formTemplateActions.add(formTemplateAction);
        }));
        formTemplate.setFormTemplateActions(formTemplateActions);
        return formTemplate;
    }

}
