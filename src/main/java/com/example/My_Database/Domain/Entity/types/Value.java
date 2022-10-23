package com.example.My_Database.Domain.Entity.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Type;

@Setter
@Getter
@NoArgsConstructor
public class Value<T> {
    private T val;

    public Value(T value) {
        this.val = value;
    }


    @Override
    public String toString() {
        return val.toString();
    }
}
