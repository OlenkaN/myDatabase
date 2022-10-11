package com.example.My_Database.Domain.Entity;

import com.example.My_Database.Domain.Entity.types.Attribute;
import com.example.My_Database.Domain.Entity.types.Types;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;

@Getter
@Setter
public class AttributeRequest {
    private String name;
    private Types type;

    public Attribute getAttr() {
        return Attribute.getAttribute(name, type);
    }
}
