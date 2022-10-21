package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.dto.FormDto;
import ca.caserm.formbuilder.entity.Form;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FormToDtoConverter implements Converter<Form, FormDto> {

    @Override
    public FormDto convert(Form source) {
        final var formDto = new FormDto();
        formDto.setId(source.getId());
        formDto.setTitle(source.getTitle());
        formDto.setDescription(source.getDescription());
        formDto.setSchema(source.getSchema());
        formDto.setAccountId(source.getAccountId());
        formDto.setThemeColor(source.getThemeColor());
        formDto.setBackgroundColor(source.getBackgroundColor());
        formDto.setTextFont(source.getTextFont());
        Optional.ofNullable(source.getFormTemplate()).ifPresent(formTemplate -> formDto.setFormTemplateId(formTemplate.getId()));
        Optional.ofNullable(source.getCreateDate()).ifPresent(createDate -> formDto.setCreateDate(createDate.toLocalDate()));
        Optional.ofNullable(source.getUpdateDate()).ifPresent(updateDate -> formDto.setUpdateDate(updateDate.toLocalDate()));
        return formDto;
    }

}
