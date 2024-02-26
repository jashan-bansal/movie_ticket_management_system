package com.self.tms.utils;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CommonUtils {

    public static Integer generateId() {
        return (new Random().nextInt());
    }
}
