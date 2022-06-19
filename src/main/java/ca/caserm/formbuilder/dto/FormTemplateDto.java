package ca.caserm.formbuilder.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FormTemplateDto extends AbstractDto {

    private String title;

    private String description;

    private String schema;

    private Long accountId;

    private String themeColor;

    private String backgroundColor;

    private String textFont;

    private Boolean enabled;

    private List<FormTemplateEmailDto> formTemplateEmailDtos;

    private List<FormTemplateActionDto> formTemplateActionDtos;

}
