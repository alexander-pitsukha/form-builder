package ca.caserm.formbuilder.dto;

import ca.caserm.formbuilder.controller.data.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public abstract class AbstractDto {

    private Long id;

    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private LocalDate createDate;

    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private LocalDate updateDate;

}
