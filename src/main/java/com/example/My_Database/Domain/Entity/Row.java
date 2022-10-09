package com.example.My_Database.Domain.Entity;

import com.example.My_Database.Domain.Entity.types.Attribute;
import com.example.My_Database.Domain.Entity.types.Value;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class Row {
    //map of column title and value
    private HashMap<String, Value> valueHashMap = new HashMap<>();

    public Row(HashMap<String, Value> values) {
        this.valueHashMap = values;
    }




    public boolean EqualTo(Row anotherRow){
        for (String key: valueHashMap.keySet()) {
            if(!anotherRow.valueHashMap.containsKey(key)){
                return false;
            }
            if(!anotherRow.valueHashMap.get(key).toString().equals(valueHashMap.get(key).toString())){
                return false;
            }
        }
        return anotherRow.valueHashMap.size() == valueHashMap.size();
    }



}
