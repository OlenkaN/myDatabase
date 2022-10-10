package com.example.My_Database.Domain.Entity.types;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RealAttr extends Attribute {
    public RealAttr(String name, String value) {
        this.name = name;
        this.value = new Value<>(Float.parseFloat(value));
    }

    public RealAttr(String name) {
        this.name = name;
    }

    @Override
    public Types getType() {
        return Types.REAL;
    }

    @Override
    public Boolean validate(String val) {
        try {
            Float.parseFloat(val);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Value getValue(String val) {
        return new Value<>(Float.parseFloat(val));
    }

    @Override
    public Value getDefault() {
        return new Value<>(0.0);
    }
}
