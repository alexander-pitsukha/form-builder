package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.dto.FormTemplateActionDto;
import ca.caserm.formbuilder.dto.FormTemplateDto;
import ca.caserm.formbuilder.dto.FormTemplateEmailDto;
import ca.caserm.formbuilder.entity.FormTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component(FormTemplateToDtoConverter.COMPONENT_NAME)
public class FormTemplateToDtoConverter implements Converter<FormTemplate, FormTemplateDto> {

    public static final String COMPONENT_NAME = "formTemplateToDtoConverter";

    @Autowired
    @Lazy
    private DefaultConversionService defaultConversionService;

    @Override
    public FormTemplateDto convert(FormTemplate source) {
        final var formTemplateDto = new FormTemplateDto();
        formTemplateDto.setId(source.getId());
        formTemplateDto.setTitle(source.getTitle());
        formTemplateDto.setDescription(source.getDescription());
        formTemplateDto.setSchema(source.getSchema());
        formTemplateDto.setAccountId(source.getAccountId());
        formTemplateDto.setThemeColor(source.getThemeColor());
        formTemplateDto.setBackgroundColor(source.getBackgroundColor());
        formTemplateDto.setTextFont(source.getTextFont());
        formTemplateDto.setEnabled(source.getEnabled());
        final List<FormTemplateEmailDto> formTemplateEmailDtos = new ArrayList<>();
        Optional.ofNullable(source.getFormTemplateEmails()).ifPresent(emails -> emails.forEach(email ->
                formTemplateEmailDtos.add(defaultConversionService.convert(email, FormTemplateEmailDto.class))));
        formTemplateDto.setFormTemplateEmailDtos(formTemplateEmailDtos);
        final List<FormTemplateActionDto> formTemplateActionDtos = new ArrayList<>();
        Optional.ofNullable(source.getFormTemplateActions()).ifPresent(actions -> actions.forEach(action ->
                formTemplateActionDtos.add(defaultConversionService.convert(action, FormTemplateActionDto.class))));
        formTemplateDto.setFormTemplateActionDtos(formTemplateActionDtos);
        Optional.ofNullable(source.getCreateDate()).ifPresent(createDate -> formTemplateDto.setCreateDate(createDate.toLocalDate()));
        Optional.ofNullable(source.getUpdateDate()).ifPresent(updateDate -> formTemplateDto.setUpdateDate(updateDate.toLocalDate()));
        return formTemplateDto;
    }

}
