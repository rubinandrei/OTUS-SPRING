package com.questionnare.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {

    private MessageSourceAccessor accessor;

    @Autowired
    public LocalizationServiceImpl(LocalizationConfiguration localizationConfiguration) {
        accessor = localizationConfiguration.getAccessor() ;
    }

    @Override
    public String localize(String messageType, Object... args) {
        return accessor.getMessage(messageType,args);
    }
}
