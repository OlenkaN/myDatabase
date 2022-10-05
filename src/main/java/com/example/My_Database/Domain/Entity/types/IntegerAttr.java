package com.example.My_Database.Domain.Entity.types;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IntegerAttr extends Attribute {
    public IntegerAttr(String name, String val) {
        this.name = name;
        this.value = new Value<>(Integer.parseInt(val));    }

    @Override
    public Types getType() {
        return Types.INTEGER;
    }

    @Override
    public Boolean validate(String val) {
        try {
            Integer.parseInt(val);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Value getValue(String val) {
        return new Value<>(Integer.parseInt(val));
    }

    @Override
    public Value getDefault() {
         return new Value<>(0);
    }
}
