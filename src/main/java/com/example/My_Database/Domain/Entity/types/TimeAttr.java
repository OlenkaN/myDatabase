package com.example.My_Database.Domain.Entity.types;

import lombok.NoArgsConstructor;

import java.time.LocalTime;
@NoArgsConstructor
public class TimeAttr extends Attribute {
    public TimeAttr(String name, String value) {
        this.name = name;
        this.value = new Value<>(LocalTime.parse(value));
    }

    @Override
    public Types getType() {
        return Types.TIME;
    }

    @Override
    public Boolean validate(String val) {
        try {
            LocalTime.parse(val);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Value getValue(String val) {
        return new Value<>(LocalTime.parse(val));
    }

    @Override
    public Value getDefault() {
        return new Value<>(LocalTime.parse("00:00"));
    }
}
