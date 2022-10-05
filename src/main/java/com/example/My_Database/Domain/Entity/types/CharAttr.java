package com.example.My_Database.Domain.Entity.types;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CharAttr extends Attribute {
    public CharAttr(String name, String val) {
        this.name = name;
        this.value = new Value<>(val.charAt(0));
    }

    @Override
    public Types getType() {
        return Types.CHAR;
    }

    @Override
    public Boolean validate(String val) {
        return val.length() == 1;
    }

    @Override
    public Value getValue(String val) {
        return new Value<>(val.charAt(0));
    }

    @Override
    public Value getDefault() {
        return new Value<>('0');
    }
}
