package com.example.a13162.activitytest.utils;

import android.os.AsyncTask;

import com.example.a13162.activitytest.database.AppDataBase;
import com.example.a13162.activitytest.entity.NfcUsageEntity;

import java.util.Date;

public class DatabaseInitializer {

    public static void populateAsync(final AppDataBase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void insertAsync(final AppDataBase db, final String xcxName, final String xcxPath) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute(xcxName, xcxPath);
    }

    public static NfcUsageEntity addNfcUsageEntity(final AppDataBase db, final String xcxName, final String xcxPath, final Date date) {

        NfcUsageEntity nfcUsageEntity = new NfcUsageEntity();
        nfcUsageEntity.xcxName = xcxName;
        nfcUsageEntity.xcxPath = xcxPath;
        nfcUsageEntity.date = date;

        db.nfcUsageDAO().insert(nfcUsageEntity);
        return nfcUsageEntity;
    }

    public static NfcUsageEntity addNfcUsageEntity(final AppDataBase db, final String xcxName, final String xcxPath) {

        NfcUsageEntity nfcUsageEntity = new NfcUsageEntity();
        nfcUsageEntity.xcxName = xcxName;
        nfcUsageEntity.xcxPath = xcxPath;
        nfcUsageEntity.date = new Date();

        db.nfcUsageDAO().insert(nfcUsageEntity);
        return nfcUsageEntity;
    }

    private static class PopulateDbAsync extends AsyncTask<String, Void, Void> {

        private final AppDataBase mDb;

        PopulateDbAsync(AppDataBase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final String... params) {
            addNfcUsageEntity(mDb, params[0], params[1]);
//            populateWithTestData(mDb);
            return null;
        }

    }

}
