package com.questionnare.localization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties("local")
public class LocalizationConfiguration {


    private MessageSourceAccessor accessor;

    private String local;


    @Autowired
    public LocalizationConfiguration(MessageSource messageSource, @Value("${local.language}") String local){
        this.local = local;
        accessor = new MessageSourceAccessor(messageSource,new Locale(local));
    }


    public String getCurrentLanguage() {
        return  local;
    }


    public MessageSourceAccessor getAccessor() {
        return accessor;
    }

    public void setAccessor(MessageSourceAccessor accessor) {
        this.accessor = accessor;
    }

    public void setCurrentLanguage(String currentLanguage) {
        this.local = currentLanguage;
    }
}
