package com.hoadeol.busybuddy.model;

public enum Priority {
  LOW, NORMAL, HIGH;

  public static Priority fromString(String priority) {
    for (Priority p : Priority.values()) {
      if (p.name().equalsIgnoreCase(priority)) {
        return p;
      }
    }
    throw new IllegalArgumentException("Unknown priority: " + priority);
  }
}
