package org.booklibrary.services;

import org.springframework.stereotype.Service;

@Service
public class IOServiceImpl implements IOService {

    @Override
    public void print(String source) {
        System.out.println(source);
    }
}
