package ca.caserm.formbuilder.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class FormTemplateDto extends AbstractDto {

    @NotNull
    @NotBlank
    private String title;

    private String description;

    private String schema;

    @NotNull
    private Long accountId;

    private String themeColor;

    private String backgroundColor;

    private String textFont;

    @NotNull
    private Boolean enabled;

    private List<FormTemplateEmailDto> formTemplateEmailDtos;

    private List<FormTemplateActionDto> formTemplateActionDtos;

}
