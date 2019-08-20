package com.example.a13162.activitytest.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.a13162.activitytest.utils.DateConverter;

import java.util.Date;

@Entity
public class NfcUsageEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "xcx_name")
    public String xcxName;

    @ColumnInfo(name = "xcx_path")
    public String xcxPath;

    @ColumnInfo(name = "date")
    @TypeConverters(DateConverter.class)
    public Date date;

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name: ");
        stringBuilder.append(xcxName);
        stringBuilder.append("\n");
        stringBuilder.append("path: ");
        stringBuilder.append(xcxPath);
        stringBuilder.append("\n");
        stringBuilder.append("date: ");
        stringBuilder.append(date.toString());
        return stringBuilder.toString();
    }
}
