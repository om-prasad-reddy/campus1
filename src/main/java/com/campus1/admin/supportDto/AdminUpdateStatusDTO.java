package com.campus1.admin.supportDto;

import lombok.Data;

@Data


public class AdminUpdateStatusDTO {
    private String field;
    private String value;

    // Getters and Setters
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
