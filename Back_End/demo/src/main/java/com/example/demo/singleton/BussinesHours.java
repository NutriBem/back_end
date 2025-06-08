package com.example.demo.singleton;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BussinesHours {
    public static List<LocalTime> hours() {
        return List.of(
                LocalTime.of(8, 0),
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                LocalTime.of(13, 0),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0),
                LocalTime.of(16, 0),
                LocalTime.of(17, 0),
                LocalTime.of(18, 0));
    }
}
