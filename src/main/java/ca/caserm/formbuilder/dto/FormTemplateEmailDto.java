package ca.caserm.formbuilder.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormTemplateEmailDto extends AbstractDto {

    private String actionName;

    @NotNull
    @NotBlank
    private String emailAddress;

    private String replyEmailAddress;

    private String emailSubject;

    private String emailBody;

    private String fromName;

    private String fromAddress;

    private String attachment;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Long formTemplateId;

}
