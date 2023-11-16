package com.api.ICPAEcommerce.domain.file.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class AllowedContentTypesValidator implements ConstraintValidator<AllowedContentTypes, String> {

    private String allowedTypes[];

    public final void initialize(final AllowedContentTypes annotation) {

        allowedTypes = annotation.value();
    }

    public final boolean isValid(final String type,
                                 final ConstraintValidatorContext context) {

        if (type == null || type.isBlank()){
            return true;
        }

        if (allowedTypes.length == 0) {
            return true;
        }

        for (String allowedType : this.allowedTypes) {
            if (type.equals(allowedType)) {
                return true;
            }
        }

        var contextImpl = (ConstraintValidatorContextImpl) context;
        contextImpl.addMessageParameter("types", String.join(", ", this.allowedTypes));

        return false;
    }
}
