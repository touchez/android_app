package com.example.a13162.activitytest.ViewModel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.a13162.activitytest.DataBase.AppDataBase;
import com.example.a13162.activitytest.entity.NfcUsageEntity;
import com.example.a13162.activitytest.utils.DatabaseInitializer;

import java.util.Date;
import java.util.List;

public class NfcUsageViewModel extends AndroidViewModel {

    public final LiveData<List<NfcUsageEntity>> nfcUsages;

    private AppDataBase mdb;

    public NfcUsageViewModel(Application application) {
        super(application);
        createDb();

        nfcUsages = mdb.nfcUsageDAO().getAll();
    }

    public void createDb() {
        mdb = AppDataBase.getInstance(this.getApplication());
    }

    public void insert(String xcxName, String xcxPath, Date date) {
        DatabaseInitializer.addNfcUsageEntity(mdb, xcxName, xcxPath, date);
    }

}
