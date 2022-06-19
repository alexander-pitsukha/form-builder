package ca.caserm.formbuilder.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormDto extends AbstractDto {

    private String title;

    private String description;

    private String schema;

    private Long accountId;

    private String themeColor;

    private String backgroundColor;

    private String textFont;

    private Long formTemplateId;

}
