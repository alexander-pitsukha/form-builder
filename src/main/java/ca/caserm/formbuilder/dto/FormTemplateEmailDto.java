package ca.caserm.formbuilder.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormTemplateEmailDto extends AbstractDto {

    private String actionName;

    private String emailAddress;

    private String replyEmailAddress;

    private String emailSubject;

    private String emailBody;

    private String fromName;

    private String fromAddress;

    private String attachment;

    private Boolean enabled;

    private Long formTemplateId;

}
