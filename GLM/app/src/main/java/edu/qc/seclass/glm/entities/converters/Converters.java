package edu.qc.seclass.glm.entities.converters;

import androidx.room.TypeConverter;

import edu.qc.seclass.glm.entities.GroceryItemType;

public class Converters {

    @TypeConverter
    public static GroceryItemType fromStringToGroceryItemType(String value) {
        return GroceryItemType.valueOf(value);
    }

    @TypeConverter
    public static String fromGroceryItemTypeToString(GroceryItemType type) {
        return type.toString();
    }

}
