package ca.caserm.formbuilder.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormTemplateActionDto extends AbstractDto {

    private String actionName;

    private String message;

    private Boolean enabled;

    private Long formTemplateId;

}
