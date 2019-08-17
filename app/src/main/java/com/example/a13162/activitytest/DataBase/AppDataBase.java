package com.example.a13162.activitytest.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.a13162.activitytest.DAO.NfcUsageDAO;
import com.example.a13162.activitytest.entity.NfcUsageEntity;

@Database(entities = {NfcUsageEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private volatile static AppDataBase INSTANCE;

    public abstract NfcUsageDAO nfcUsageDAO();

    //抽象类不能实例化，无需写构造函数了
//    private AppDataBase(){}

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            AppDataBase.class, "database-name").build();
                }
            }
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
