package ca.caserm.formbuilder.mapper;

import ca.caserm.formbuilder.dto.FormDto;
import ca.caserm.formbuilder.entity.Form;
import org.springframework.stereotype.Component;

@Component
public class FormMapper {

    public void map(FormDto formDto, Form form) {
        form.setTitle(formDto.getTitle());
        form.setDescription(formDto.getDescription());
        form.setSchema(formDto.getSchema());
        form.setAccountId(formDto.getAccountId());
        form.setThemeColor(formDto.getThemeColor());
        form.setBackgroundColor(formDto.getBackgroundColor());
        form.setTextFont(formDto.getTextFont());
    }
}
