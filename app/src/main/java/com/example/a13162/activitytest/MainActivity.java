package com.example.a13162.activitytest;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PowerManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.a13162.activitytest.adapter.MyListViewAdapter;
import com.example.a13162.activitytest.utils.HttpRequestUtil;
import com.example.a13162.activitytest.utils.NotificationUtil;
import com.example.a13162.activitytest.viewmodel.NfcUsageViewModel;
import com.example.a13162.activitytest.entity.NfcUsageEntity;
import com.example.a13162.activitytest.service.NotifyService;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseNfcActivity {
    String mTagText;
    private BottomNavigationView bottomNavigationView;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private MenuItem menuItem;
    protected NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private FragmentTag tag;
    private FragamentNfc f;

    private boolean startFlag=false;     //用于判断是否是在外面扫的小程序
    public boolean isStartFlag=true;    //用于判断是否应该读取数据
    private PowerManager.WakeLock mWakelock; //用于锁屏唤醒
    private PowerManager pm;
    private String CHANNEL_ID = "default";
    private NfcUsageViewModel mViewModel;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.QRcode:
                // 创建IntentIntegrator对象
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                //设置自定义的扫描
                intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
                // 开始扫描
                intentIntegrator.initiateScan();

                Toast.makeText(this,"You clicker Backuo",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    @Override
    public void onNewIntent(Intent intent) {
        Log.d("touchez","a new intent");
        resolveIntent(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("touchez", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        SharedPreferences pref=getSharedPreferences("nfc", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String str=pref.getString("open","");
        if (str == null || str.equals("")) {
            editor.putString("open","open");
            editor.commit();
            str=pref.getString("open","");
        }
           if(str!=null&&!Data.getnfclist().contains(str)) {
               Data.getnfclist().add(str);
           }
           String string=pref.getString("pages/activeCheck/activeCheck?type=exsanguinate","");
           if(string!=null&&!Data.getnfclist().contains(string)) {
               Data.getnfclist().add(string);
           }
        String string1=pref.getString("pages/activeCheck/activeCheck?type=ct","");
        if(string1!=null&&!Data.getnfclist().contains(string1)) {
            Data.getnfclist().add(string1);
        }
        String string2=pref.getString("pages/medicineList/medicineList","");
        if(string2!=null&&!Data.getnfclist().contains(string2)) {
            Data.getnfclist().add(string2);
        }
        String string3=pref.getString("pages/activeCheck/activeCheck?type=xray","");
        if(string3!=null&&!Data.getnfclist().contains(string3)) {
            Data.getnfclist().add(string3);
        }
        String string4=pref.getString("pages/detail/detail?departmentid=2","");
        if(string4!=null&&!Data.getnfclist().contains(string4)) {
            Data.getnfclist().add(string4);
        }
        String string5=pref.getString("pages/jiuzheng/jiuzheng","");
        if(string5!=null&&!Data.getnfclist().contains(string5)) {
            Data.getnfclist().add(string5);
        }
        String string6=pref.getString("pages/index/index","");
        if(string6!=null&&!Data.getnfclist().contains(string6)) {
            Data.getnfclist().add(string6);
        }
        String string7=pref.getString("pages/daoyouji/daoyouji","");
        if(string7!=null&&!Data.getnfclist().contains(string7)) {
            Data.getnfclist().add(string7);
        }
        String string8=pref.getString("pages/daoyouji2/daoyouji2?guideMachineName=甘肃彩陶文化简介","");
        if(string8!=null&&!Data.getnfclist().contains(string8)) {
            Data.getnfclist().add(string8);
        }
        String string9=pref.getString("pages/daoyouji2/daoyouji2?guideMachineName=安特生考察路线图","");
        if(string9!=null&&!Data.getnfclist().contains(string9)) {
            Data.getnfclist().add(string9);
        }
        String string10=pref.getString("pages/openLockSuccess/openLockSuccess","");
        if(string10!=null&&!Data.getnfclist().contains(string10)) {
            Data.getnfclist().add(string10);
        }
        for(int i=0;i<Data.getnfclist().size();i++)
        {
            System.out.println(Data.getnfclist().get(i));
        }
        viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        f=new FragamentNfc();
        tag=new FragmentTag();
        List<Fragment> list = new ArrayList<>();
        list.add(TestFragment.newInstance("首页"));
        list.add(f);//nfc页面
        list.add(tag);//标签页面
        //list.add(TestFragment.newInstance(""));
        viewPagerAdapter.setList(list);
        viewPager.setAdapter(viewPagerAdapter);
        startFlag=true;

        mViewModel = new ViewModelProvider(this).get(NfcUsageViewModel.class);
        Log.i("touchez", "mViewModel is null? " + (mViewModel == null));

        //直接跳转小程序
        resolveIntent(getIntent());
        startFlag=false;



    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            menuItem = item;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
                /*case R.id.navigation_person:
                    viewPager.setCurrentItem(3);
                    return true;*/
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "取消扫描", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描内容:" + result.getContents(), Toast.LENGTH_LONG).show();
                viewPager.setCurrentItem(2);
                tag.showInputDialog(result.getContents());
                //tag.showInputDialog(mTagText);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    /**
     * 读取NFC标签文本数据
     */
    private void readNfcTag(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msgs[] = null;
            int contentSize = 0;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                    contentSize += msgs[i].toByteArray().length;
                }
            }
            try {
                if (msgs != null) {
                    NdefRecord record = msgs[0].getRecords()[0];
                    String textRecord = parseTextRecord(record);
                    mTagText = textRecord ;
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 解析NDEF文本数据，从第三个字节开始，后面的文本数据
     *
     * @param ndefRecord
     * @return
     */
    public static String parseTextRecord(NdefRecord ndefRecord) {
        /**
         * 判断数据是否为NDEF格式
         */
        //判断TNF
        if (ndefRecord.getTnf() != NdefRecord.TNF_WELL_KNOWN) {
            return null;
        }
        //判断可变的长度的类型
        if (!Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
            return null;
        }
        try {
            //获得字节数组，然后进行分析
            byte[] payload = ndefRecord.getPayload();
            //下面开始NDEF文本数据第一个字节，状态字节
            //判断文本是基于UTF-8还是UTF-16的，取第一个字节"位与"上16进制的80，16进制的80也就是最高位是1，
            //其他位都是0，所以进行"位与"运算后就会保留最高位
            String textEncoding = ((payload[0] & 0x80) == 0) ? "UTF-8" : "UTF-16";
            //3f最高两位是0，第六位是1，所以进行"位与"运算后获得第六位
            int languageCodeLength = payload[0] & 0x3f;
            //下面开始NDEF文本数据第二个字节，语言编码
            //获得语言编码
            String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            //下面开始NDEF文本数据后面的字节，解析出文本
            String textRecord = new String(payload, languageCodeLength + 1,
                    payload.length - languageCodeLength - 1, textEncoding);
            return textRecord;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) resp;
            String extraData =launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
        }
    }

    private void resolveIntent(final Intent intent){
        Log.i("touchez", "get intent");

        String action=intent.getAction();
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){

            //1.获取Tag对象
            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            //2.获取Ndef的实例
            Ndef ndef = Ndef.get(detectedTag);
            //mTagText = ndef.getType() + "\nmaxsize:" + ndef.getMaxSize() + "bytes\n\n";
            readNfcTag(intent);
            if(mTagText!=null)
                Log.d("abcd",mTagText);
            else
            {
                mTagText="This is a null tag!!!";
                Log.d("abcd","msg is null");
            }


            String xcx=null;
            String path=null;
            int i=0;
            if(mTagText!=null&&mTagText.indexOf("xcx:")==0){
                i=mTagText.indexOf("path:");
                if(i<0){
                    xcx=mTagText.substring(4);
                }else{
                    xcx=mTagText.substring(4,i);
                    i=i+5;
                    path=mTagText.substring(i);
                }
                Log.d("xcx","xcx id is "+xcx+" path is "+path);

            } else if (mTagText!=null&&mTagText.indexOf("alipays:")==0) {
                i=mTagText.indexOf("path:");
                if(i<0){
                    xcx=mTagText.substring(8);
                }else{
                    xcx=mTagText.substring(8,i);
                    i=i+5;
                    path=mTagText.substring(i);
                }
                Log.d("xcx","xcx id is "+xcx+" path is "+path);
            } else if (mTagText!=null&&mTagText.indexOf("hap:")==0) {
                i=mTagText.indexOf("path:");
                if(i<0){
                    xcx=mTagText.substring(4);
                }else{
                    xcx=mTagText.substring(4,i);
                    i=i+5;
                    path=mTagText.substring(i);
                }
                Log.d("xcx","xcx id is "+xcx+" path is "+path);
            }





            //在外面扫到小程序直接跳
            if(startFlag==true&&xcx!=null){


                startFlag=false;

                multiJump(mTagText, xcx, path);


                return;
            }

            Log.i("touchez", "getIsactive is: " + Data.getIsactive());

            if(Data.getIsactive()==0){
                viewPager.setCurrentItem(2);
                tag.showInputDialog(mTagText);
            }else if(Data.getIsactive()==1){
                if(xcx!=null){
                    final String str=xcx;
                    final String str1=path;
                    AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("发现一个小程序");
                    dialog.setMessage("是否跳转？");
                    dialog.setPositiveButton("跳转", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            multiJump(mTagText, str, str1);
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }else{
                    AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("NFC标签内容");
                    dialog.setMessage(mTagText);
                    dialog.show();
                }
            }
        }

        final Bundle extras = intent.getExtras();
        //通过通知来进行跳转
        if (extras != null) {
            final String jumpFromService = extras.getString("tagInfo");
            if (jumpFromService != null) {
                Log.i("touchez", "intent data is: " + jumpFromService);
                String[] res = resolveXCXIdAndPath(jumpFromService);
                jumpToXCX(res[0], res[1]);
            }
        }
    }

    private void multiJump(String mTagText, String xcx, String path) {
        if (mTagText!=null&&mTagText.indexOf("alipays:")==0) {
            jumpToZFBXCX(xcx, path);
        }else if (mTagText!=null&&mTagText.indexOf("hap:")==0) {
            jumpToQuickApp(xcx, path);
        }else {
            jumpToXCX(xcx,path);
        }
    }

    //跳转快应用
    private void jumpToQuickApp(String xcxid, String xcxpath) {
        String path = "hap://app/" + xcxid + "/" + xcxpath;
        if(xcxpath!=null){
            if(Data.getnfclist().contains(xcxpath)) {
                mViewModel.insert(xcxid, xcxpath);
                Uri data = Uri.parse(path);
                Intent intent = new Intent(Intent.ACTION_VIEW,data);
                //保证新启动的APP有单独的堆栈，如果希望新启动的APP和原有APP使用同一个堆栈则去掉该项
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivityForResult(intent, RESULT_OK);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "没有匹配的APP，请下载安装",Toast.LENGTH_SHORT).show();
                }
            }else {
                System.out.println("没找到");
            }
        }
        
    }

    // 跳转至支付宝小程序
    private void jumpToZFBXCX(String xcxid, String xcxpath) {
        String path = "alipays://platformapi/startapp?appId=" + xcxid + "&page=" + xcxpath;
        Uri data = Uri.parse(path);
        Intent intent = new Intent(Intent.ACTION_VIEW,data);
        //保证新启动的APP有单独的堆栈，如果希望新启动的APP和原有APP使用同一个堆栈则去掉该项
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivityForResult(intent, RESULT_OK);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "没有匹配的APP，请下载安装",Toast.LENGTH_SHORT).show();
        }
    }

    //跳转小程序
    private void jumpToXCX(String xcxid,String xcxpath){



        String appId = "wx22f60e47bd1eb936"; // 填应用AppId
        IWXAPI api = WXAPIFactory.createWXAPI(MainActivity.this, appId);

        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = xcxid; // 填小程序原始id
        if(xcxpath!=null){
            if(Data.getnfclist().contains(xcxpath)) {

                if (pm == null) {
                    initPowerManager();
                }

                //点亮屏幕
                if (!pm.isInteractive()) {
                    Log.i("touchez", "屏幕是黑的，准备点亮");
                    if (xcxpath.equals("pages/lock/index")) {
                        //针对共享单车开锁的情况特殊处理
                        Log.i("touchez", "点亮屏幕");
                        wakeScreen();
                        mTagText = mTagText.replace("pages/lock/index", "pages/openLockSuccess/openLockSuccess");
                        Log.i("touchez", "mTagText is: " + mTagText);

                        NotificationUtil notificationUtil = new NotificationUtil(this);
                        notificationUtil.showNotification(mTagText, "共享单车", "已成功开锁！");

                        HttpRequestUtil.callOpenLock();
                        onBackPressed();
                        return;
                    }else {
                        wakeScreen();
                        NotificationUtil notificationUtil = new NotificationUtil(this);
                        notificationUtil.showNotification(mTagText, "检测到nfc标签", "要前往touchez小程序？");
                        onBackPressed();
                        return;
                    }

                }

                System.out.println("找到了");
                req.path = xcxpath;
                req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
                api.sendReq(req);
                mViewModel.insert(xcxid, xcxpath);
            }
            else
            {
                System.out.println("没找到");
            }
        }
        //                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
       /* req.miniprogramType = WXLaunchMiniProgram.Req.MINIPROGRAM_TYPE_PREVIEW;// 可选打开 开发版，体验版和正式版
        api.sendReq(req);*/
    }
    /**
     * 解析小程序的id和path
     * @param mTagText tag中的信息
     * @return res[0]: xcxId, res[1]: xcxPath
     */
    private String[] resolveXCXIdAndPath(String mTagText) {
        String[] res = new String[2];

        int i=0;
        if(mTagText!=null&&mTagText.indexOf("xcx:")==0){
            i=mTagText.indexOf("path:");
            if(i<0){
                res[0]=mTagText.substring(4);
            }else{
                res[0]=mTagText.substring(4,i);
                i=i+5;
                res[1]=mTagText.substring(i);
            }
            Log.d("xcx","xcx id is "+res[0]+" path is "+res[1]);

        }

        return res;

    }

    private void wakeScreen() {
        if (pm == null) {
            initPowerManager();
        }

        if (mWakelock == null) {
            initWakeLock();
        }

        if (!pm.isInteractive()) {
            mWakelock.acquire(1000); // Wake up Screen and keep screen lighting
            mWakelock.release(); // release control.stop to keep screen lighting
        }

    }

    private void initPowerManager() {
        if (pm == null) {
            pm = (PowerManager)getSystemService(POWER_SERVICE);// init powerManager
        }
    }

    private void initWakeLock() {
        if (pm == null) {
            initPowerManager();
        }

        if (mWakelock == null) {
            mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP|
                    PowerManager.SCREEN_DIM_WAKE_LOCK,"touchez:wakeScreen()"); // this target for tell OS which app control screen
        }
    }
}

