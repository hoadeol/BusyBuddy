package com.hoadeol.busybuddy.util;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DummyDataInitializer {

  private final DummyDataGenerator dummyDataGenerator;

  @PostConstruct
  public void init() {
    dummyDataGenerator.createDummyData();
  }
}