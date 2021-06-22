package com.questionnare.localization;

import org.springframework.stereotype.Component;

@Component
public interface LocalizationService {

    String localize(String message,Object... args);
}
