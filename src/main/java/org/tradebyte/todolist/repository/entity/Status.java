package org.tradebyte.todolist.repository.entity;

import lombok.Getter;

@Getter
public enum Status {
    DONE ("done"),
    NOT_DONE("not done"),
    PAST_DUE("past due");

    private final String value;

    Status(String value){
        this.value = value;
    }
    public static Status fromValue(String value) {
        for (Status s : Status.values()) {
            if (s.value.equalsIgnoreCase(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status value: " + value);
    }

}
