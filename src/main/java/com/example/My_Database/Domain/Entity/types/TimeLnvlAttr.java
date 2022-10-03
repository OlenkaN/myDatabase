package com.example.My_Database.Domain.Entity.types;
import org.joda.time.Interval;


public class TimeLnvlAttr extends Attribute {
    public TimeLnvlAttr(String name, String value) {
        super();
    }

    @Override
    public Types getType() {
        return null;
    }

    @Override
    public Boolean validate(String val) {
        return null;
    }

    @Override
    public Value getValue(String val) {
        return new Value<>(Interval.parse(val));
    }

    @Override
    public Value getDefault() {
        return null;
    }
}
