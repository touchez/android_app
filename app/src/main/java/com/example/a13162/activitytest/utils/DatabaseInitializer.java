package com.example.a13162.activitytest.utils;

import com.example.a13162.activitytest.DataBase.AppDataBase;
import com.example.a13162.activitytest.entity.NfcUsageEntity;

import java.util.Date;

public class DatabaseInitializer {

    public static NfcUsageEntity addNfcUsageEntity(final AppDataBase db, final String xcxName, final String xcxPath, final Date date) {

        NfcUsageEntity nfcUsageEntity = new NfcUsageEntity();
        nfcUsageEntity.xcxName = xcxName;
        nfcUsageEntity.xcxPath = xcxPath;
        nfcUsageEntity.date = date;

        db.nfcUsageDAO().insert(nfcUsageEntity);
        return nfcUsageEntity;
    }

}
