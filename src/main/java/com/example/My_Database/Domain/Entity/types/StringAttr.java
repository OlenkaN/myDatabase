package com.example.My_Database.Domain.Entity.types;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StringAttr extends Attribute {
    public StringAttr(String name, String value) {
        this.name = name;
        this.value = new Value<>(value);
    }

    public StringAttr(String name) {
        this.name = name;
    }

    @Override
    public Types getType() {
        return Types.STRING;
    }

    @Override
    public Boolean validate(String val) {
        return true;
    }

    @Override
    public Value getValue(String val) {
        return new Value<>(val);
    }

    @Override
    public Value getDefault() {
        return new Value<>("");
    }

}
