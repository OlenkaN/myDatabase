package com.example.My_Database.Domain.Entity.types;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;

@Setter
@Getter
public class Value<T> {
    private T val;
    private Types type;

    public Value(T value) {
        this.val = value;
    }

    public Value(T value,  Types type) {
        this.val = value;
        this.type = getType();
    }


    @Override
    public String toString() {
        return val.toString();
    }
}
