package com.example.My_Database.Domain.Entity.types;

public class StringAttr extends Attribute {
    public StringAttr(String name, String value) {
        this.name = name;
        this.value = new Value<>(value);
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
