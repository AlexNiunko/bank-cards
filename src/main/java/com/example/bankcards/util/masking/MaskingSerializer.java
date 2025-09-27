package com.example.bankcards.util.masking;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class MaskingSerializer extends StdSerializer<String> implements ContextualSerializer {

    private Masked masked;

    public MaskingSerializer() {
        super(String.class);
    }

    public MaskingSerializer(Masked masked) {
        super(String.class);
        this.masked = masked;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {

        if (value == null) {
            gen.writeNull();
            return;
        }

        String maskedValue = applyMask(value);
        gen.writeString(maskedValue);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) {
        if (property != null) {
            Masked annotation = property.getAnnotation(Masked.class);
            if (annotation != null) {
                return new MaskingSerializer(annotation);
            }
        }
        return this;
    }

    private String applyMask(String value) {
        if (masked == null) {
            return value;
        }

        return switch (masked.type()) {
            case CREDIT_CARD -> maskCreditCard(value);
            case PHONE -> maskPhone(value);
            case EMAIL -> maskEmail(value);
            case CUSTOM -> maskCustom(value);
        };
    }

    private String maskCreditCard(String cardNumber) {
        String lastFour = cardNumber.substring(cardNumber.length() - 4);
        String maskedPart = String.valueOf(masked.maskChar()).repeat(cardNumber.length() - 4);
        return maskedPart + lastFour;
    }

    private String maskPhone(String phone) {
        if (phone == null || phone.length() <= 5) {
            return phone;
        }

        String digits = phone.replaceAll("[^\\d+]", "");

        int visiblePrefix = Math.min(masked.visiblePrefix(), digits.length() - masked.visibleSuffix());
        int visibleSuffix = Math.min(masked.visibleSuffix(), digits.length() - visiblePrefix);

        String prefix = digits.substring(0, visiblePrefix);
        String suffix = digits.substring(digits.length() - visibleSuffix);
        String maskedPart = String.valueOf(masked.maskChar()).repeat(digits.length() - visiblePrefix - visibleSuffix);

        return String.join(prefix, maskedPart, suffix);
    }

    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }

        int atIndex = email.indexOf('@');
        String localPart = email.substring(0, atIndex);
        String domain = email.substring(atIndex);

        if (localPart.length() <= 2) {
            return String.valueOf(masked.maskChar()).repeat(localPart.length()) + domain;
        }

        String maskedLocal = localPart.charAt(0) +
                             String.valueOf(masked.maskChar()).repeat(localPart.length() - 2) +
                             localPart.charAt(localPart.length() - 1);

        return maskedLocal + domain;
    }


    private String maskCustom(String value) {
        if (value == null) {
            return null;
        }

        int visiblePrefix = Math.min(masked.visiblePrefix(), value.length());
        int visibleSuffix = Math.min(masked.visibleSuffix(), value.length() - visiblePrefix);

        String prefix = value.substring(0, visiblePrefix);
        String suffix = value.substring(value.length() - visibleSuffix);
        String maskedPart = String.valueOf(masked.maskChar()).repeat(value.length() - visiblePrefix - visibleSuffix);

        return prefix + maskedPart + suffix;
    }
}
