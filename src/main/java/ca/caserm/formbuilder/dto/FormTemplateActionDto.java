package ca.caserm.formbuilder.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormTemplateActionDto extends AbstractDto {

    @NotNull
    @NotBlank
    private String actionName;

    private String message;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Long formTemplateId;

}
