package validators;

import com.got.validator.ValidationType;
import com.got.validator.rules.ValidatorBase;
import javafx.scene.control.Control;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidator extends ValidatorBase {
    public DateValidator(Control control, String message, ValidationType validationType) {
        super(control, message, validationType);
    }

    @Override
    protected void evaluateRules() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        formatter.setLenient(false);
        try {
            formatter.parse(this.getText());
            this.hasErrors.set(false);
        } catch (ParseException e) {
            this.hasErrors.set(true);
        }
    }
}
