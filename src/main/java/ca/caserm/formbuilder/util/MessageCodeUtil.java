package ca.caserm.formbuilder.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class MessageCodeUtil {

    public static final String ERROR_MESSAGE = "error.message";
    private static final Logger LOGGER = Logger.getLogger(MessageCodeUtil.class.getName());
    private static final Locale LOCALE = Locale.CANADA;
    private final MessageSource messageSource;

    public String getFullErrorMessageByBundleCode(String bundleCode, Object[] objects) {
        try {
            return messageSource.getMessage(bundleCode, objects, LOCALE);
        } catch (NoSuchMessageException e) {
            LOGGER.log(Level.WARNING, "Unknown bundle code {}", bundleCode);
        }
        return messageSource.getMessage(ERROR_MESSAGE, objects, LOCALE);
    }

    public String getFullErrorMessageByBundleCode(String bundleCode) {
        return getFullErrorMessageByBundleCode(bundleCode, null);
    }

}
