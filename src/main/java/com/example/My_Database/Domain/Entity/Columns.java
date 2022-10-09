package com.example.My_Database.Domain.Entity;

import com.example.My_Database.Domain.Entity.types.Attribute;
import com.example.My_Database.utils.Result;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Getter
public class Columns {
    HashMap<String, Attribute> columnsName;

    public Columns() {
        this.columnsName = new HashMap<>();
    }

    public Columns(List<Attribute> attributeList) {
        this.columnsName = new HashMap<>();
        attributeList.forEach(attribute -> this.columnsName.put(attribute.getName(), attribute));
    }

    public Result addAttr(Attribute attr) {
        if (columnsName.containsKey(attr.getName())) {
            return Result.Fail("Duplicate attribute");
        }
        columnsName.put(attr.getName(), attr);
        return Result.Success();
    }

    public Collection<Attribute> listAttributes() {
        return columnsName.values();
    }

    public String[] listColumnNames() {
        return new ArrayList<>(columnsName.keySet()).toArray(String[]::new);
    }

    public Result deleteAttr(String key) {
        if (!columnsName.containsKey(key)) {
            return Result.Fail("No such attribute");
        }
        columnsName.remove(key);
        return Result.Success();
    }

    public Attribute getAttr(String key) {
        return columnsName.getOrDefault(key, null);
    }


}
