package com.example.My_Database.Domain.Entity.types;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
public  class Attribute  {
    public String name;

    public Value value;

    public String getName() {
        return this.name;
    }

    public Types getType() {
        return null;
    }

    public Boolean validate(String val) {
        return null;
    }

    public Value getValue(String val) {
        return null;
    }

    public Value getDefault() {
        return null;
    }

    public static Attribute getAttribute(String name, Types type, String value) {
        switch (type) {
            case INTEGER:
                return new IntegerAttr(name, value);
            case REAL:
                return new RealAttr(name, value);
            case CHAR:
                return new CharAttr(name, value);
            case STRING:
                return new StringAttr(name, value);
            case TIME:
                return new TimeAttr(name, value);
            case TIME_LNVL:
                return new TimeLnvlAttr(name, value);
            default:
                return null;
        }
    }

    public static Attribute getAttribute(String name, Types type) {
        switch (type) {
            case INTEGER:
                return new IntegerAttr(name,"0");
            case REAL:
                return new RealAttr(name,"0.0");
            case CHAR:
                return new CharAttr(name,"");
            case STRING:
                return new StringAttr(name,"");
            case TIME:
                return new TimeAttr(name, Instant.now().toString());
            case TIME_LNVL:
                return new TimeLnvlAttr(name,new Interval(DateTime.now(), DateTime.now().plusDays(1)).toString());
            default:
                return null;
        }
    }


}
