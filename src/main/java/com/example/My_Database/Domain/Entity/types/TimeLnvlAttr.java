package com.example.My_Database.Domain.Entity.types;
import org.joda.time.DateTime;
import org.joda.time.Interval;


public class TimeLnvlAttr extends Attribute {
    public TimeLnvlAttr(String name, String value) {

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
        return new Value<>(new Interval(DateTime.now(), DateTime.now().plusDays(1)));
    }
}
