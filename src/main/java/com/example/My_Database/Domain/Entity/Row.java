package com.example.My_Database.Domain.Entity;

import com.example.My_Database.Domain.Entity.types.Attribute;
import com.example.My_Database.Domain.Entity.types.Value;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;

@Getter
@Setter
public class Row {
    //map of column title and value
    private HashMap<String, Attribute> attributeHashMap;

    public Row(HashMap<String, Attribute> values) {
        this.attributeHashMap = values;
    }

    public Row() {
        this.attributeHashMap = new HashMap<>();
    }

/*    public Row(HashMap<String,Value> valueHashMap, Boolean isValue) {
        this.attributeHashMap = new HashMap<>();
       valueHashMap.entrySet().forEach(value -> this.attributeHashMap.put(value.getKey(),
              Attribute.getAttribute(value.getValue().getVal(), value.getType())));
    }*/


    public Boolean addAttr(Attribute attr) {
        if (attributeHashMap.containsKey(attr.name)) {
            throw new EntityExistsException(String.format("Column with this name: %s already exist", attr.getName()));

        }
        attributeHashMap.put(attr.name, attr);
        return true;
    }


    public Boolean deleteColumn(String key) {
        if (!attributeHashMap.containsKey(key)) {
            throw new EntityNotFoundException(String.format("Column with this name: %s not exist", key));
        }
        attributeHashMap.remove(key);
        return true;
    }

    public Attribute getAttr(String key) {
        return attributeHashMap.getOrDefault(key, null);
    }


}
