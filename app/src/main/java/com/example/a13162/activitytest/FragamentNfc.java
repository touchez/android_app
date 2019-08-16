package com.example.a13162.activitytest;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentHostCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragamentNfc extends Fragment {
    private int count = 0;
    private Handler handler=new Handler();
    private int number=0;
    private int flag=0;
    String mTagText;
    FragmentHostCallback mHost;
    private ImageView imageView;
    private Button button;
    private Switch switch1;
    private Switch switch2;
    private Switch switch3;
    private ArrayAdapter<String> adapter;
    private Runnable runnable=new Runnable(){
         public void run(){
             SharedPreferences pref=getContext().getSharedPreferences("nfc", MODE_PRIVATE);
             String switch1true=pref.getString("pages/activeCheck/activeCheck?type=exsanguinate","");
             if(Data.getnfclist().contains("pages/activeCheck/activeCheck?type=exsanguinate")&&(!switch1.isChecked()))
             {
                 System.out.println(switch1true);
                 switch1.setChecked(true);
                 /*deleteData("switch1True");*/
             }
            handler.postDelayed(this,100);//每一秒更新一次
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_nfc_layout, container, false);
        handler.postDelayed(runnable,100);
        for (int i = 0; i < Data.getnfclist().size(); i++) {
            System.out.println(Data.getnfclist().get(i));
        }
        System.out.println("分割线——————————————");
        switch1=view.findViewById(R.id.switch1);
        SharedPreferences pref=getContext().getSharedPreferences("nfc", MODE_PRIVATE);
        String switch1true=pref.getString("pages/activeCheck/activeCheck?type=exsanguinate","");
        if(Data.getnfclist().contains("pages/activeCheck/activeCheck?type=exsanguinate")&&(!switch1.isChecked()))
        {
            System.out.println(switch1true);
            switch1.setChecked(true);
            /*deleteData("switch1True");*/
        }
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    if(!Data.getnfclist().contains("pages/activeCheck/activeCheck?type=exsanguinate")) {
                        Data.getnfclist().add("pages/activeCheck/activeCheck?type=exsanguinate");//医疗
                        saveData("pages/activeCheck/activeCheck?type=exsanguinate");
                        Data.getnfclist().add("pages/activeCheck/activeCheck?type=ct");//医疗
                        saveData("pages/activeCheck/activeCheck?type=ct");
                        Data.getnfclist().add("pages/medicineList/medicineList");//医疗
                        saveData("pages/medicineList/medicineList");
                        Data.getnfclist().add("pages/activeCheck/activeCheck?type=xray");//医疗
                        saveData("pages/activeCheck/activeCheck?type=xray");
                        Data.getnfclist().add("pages/detail/detail?departmentid=2");//医疗
                        saveData("pages/detail/detail?departmentid=2");
                        Data.getnfclist().add("pages/jiuzheng/jiuzheng");//医疗
                        saveData("pages/jiuzheng/jiuzheng");
                        saveData("switch1True");
                        //deleteData("switch1False");
                        for (int i = 0; i < Data.getnfclist().size(); i++) {
                            System.out.println(Data.getnfclist().get(i));
                        }
                    }
                }
                else
                {
                    int temp1=100;
                    temp1=Data.getnfclist().indexOf("pages/activeCheck/activeCheck?type=exsanguinate");
                    if(temp1!=100)
                    {
                        Data.getnfclist().remove(temp1);
                        deleteData("pages/activeCheck/activeCheck?type=exsanguinate");
                    }
                    int temp2=100;
                    temp2=Data.getnfclist().indexOf("pages/activeCheck/activeCheck?type=ct");
                    if(temp2!=100)
                    {
                        Data.getnfclist().remove(temp2);
                        deleteData("pages/activeCheck/activeCheck?type=ct");
                    }
                    int temp3=100;
                    temp3=Data.getnfclist().indexOf("pages/medicineList/medicineList");
                    if(temp3!=100)
                    {
                        Data.getnfclist().remove(temp3);
                        deleteData("pages/medicineList/medicineList");
                    }
                    int temp4=100;
                    temp4=Data.getnfclist().indexOf("pages/activeCheck/activeCheck?type=xray");
                    if(temp4!=100)
                    {
                        Data.getnfclist().remove(temp4);
                        deleteData("pages/activeCheck/activeCheck?type=xray");
                    }
                    int temp5=100;
                    temp5=Data.getnfclist().indexOf("pages/detail/detail?departmentid=2");
                    if(temp5!=100)
                    {
                        Data.getnfclist().remove(temp5);
                        deleteData("pages/detail/detail?departmentid=2");
                    }
                    int temp6=100;
                    temp6=Data.getnfclist().indexOf("pages/jiuzheng/jiuzheng");
                    if(temp6!=100)
                    {
                        Data.getnfclist().remove(temp6);
                        deleteData("pages/jiuzheng/jiuzheng");
                    }
                    for(int i=0;i<Data.getnfclist().size();i++)
                    {
                        System.out.println(Data.getnfclist().get(i));
                    }
                    /*deleteData("switch1True");*/
                    //saveData("switch1False");
                }
            }
        });
        switch2=view.findViewById(R.id.switch2);
        String switch2true=pref.getString("pages/index/index","");
        if(Data.getnfclist().contains("pages/index/index"))
        {
            switch2.setChecked(true);
            deleteData("switch2True");
        }
        //deleteData("switch2True");
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    if(!Data.getnfclist().contains("pages/index/index")) {
                        Data.getnfclist().add("pages/index/index");//单车
                        saveData("pages/index/index");
                        for (int i = 0; i < Data.getnfclist().size(); i++) {
                            System.out.println(Data.getnfclist().get(i));
                        }
                        saveData("switch2True");
                    }
                }
                else
                {
                    int tmp=100;
                    tmp=Data.getnfclist().indexOf("pages/index/index");
                    deleteData("pages/index/index");
                    if(tmp!=100)
                    {
                        Data.getnfclist().remove(tmp);
                    }
                    for(int i=0;i<Data.getnfclist().size();i++)
                    {
                        System.out.println(Data.getnfclist().get(i));
                    }
                    deleteData("switch2True");
                }
            }
        });
        switch3=view.findViewById(R.id.switch3);
        String switch3true=pref.getString("pages/daoyouji/daoyouji","");
        if(Data.getnfclist().contains("pages/daoyouji/daoyouji"))
        {
            switch3.setChecked(true);
            deleteData("switch3True");
        }
        //deleteData("switch3True");
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    if(!Data.getnfclist().contains("pages/daoyouji/daoyouji")) {
                        Data.getnfclist().add("pages/daoyouji/daoyouji");//旅游
                        saveData("pages/daoyouji/daoyouji");
                        Data.getnfclist().add("pages/daoyouji2/daoyouji2?guideMachineName=甘肃彩陶文化简介");//旅游
                        saveData("pages/daoyouji2/daoyouji2?guideMachineName=甘肃彩陶文化简介");
                        Data.getnfclist().add("pages/daoyouji2/daoyouji2?guideMachineName=安特生考察路线图");//旅游
                        saveData("pages/daoyouji2/daoyouji2?guideMachineName=安特生考察路线图");
                        for (int i = 0; i < Data.getnfclist().size(); i++) {
                            System.out.println(Data.getnfclist().get(i));
                        }
                        saveData("switch3True");
                    }
                }
                if(!isChecked)
                {
                    int tmp=100;
                    tmp=Data.getnfclist().indexOf("pages/daoyouji/daoyouji");
                    deleteData("pages/daoyouji/daoyouji");
                    if(tmp!=100)
                    {
                        Data.getnfclist().remove(tmp);
                    }
                    int tmp1=100;
                    tmp1=Data.getnfclist().indexOf("pages/daoyouji2/daoyouji2?guideMachineName=甘肃彩陶文化简介");
                    deleteData("pages/daoyouji2/daoyouji2?guideMachineName=甘肃彩陶文化简介");
                    if(tmp1!=100)
                    {
                        Data.getnfclist().remove(tmp1);
                    }
                    int tmp2=100;
                    tmp2=Data.getnfclist().indexOf("pages/daoyouji2/daoyouji2?guideMachineName=安特生考察路线图");
                    deleteData("pages/daoyouji2/daoyouji2?guideMachineName=安特生考察路线图");
                    if(tmp2!=100)
                    {
                        Data.getnfclist().remove(tmp2);
                    }
                    for(int i=0;i<Data.getnfclist().size();i++)
                    {
                        System.out.println(Data.getnfclist().get(i));
                    }
                    System.out.println();
                    deleteData("switch3True");
                }
            }
        });
        //Data.getnfclist().add("xcx:gh_f4803b06a633path:pages/index/index");//单车

       /* Data.getnfclist().add("xcx:gh_f4803b06a633path:pages/daoyouji/daoyouji");//旅游
        Data.getnfclist().add("xcx:gh_f4803b06a633path:pages/daoyouji2/daoyouji2?guideMachineName=甘肃彩陶文化简介");//旅游
        Data.getnfclist().add("xcx:gh_f4803b06a633path:pages/daoyouji2/daoyouji2?guideMachineName=安特生考察路线图");//旅游*/

       /* Data.getnfclist().add("xcx:gh_f4803b06a633path:pages/activeCheck/activeCheck?type=exsanguinate");//医疗
        Data.getnfclist().add("xcx:gh_f4803b06a633path:pages/activeCheck/activeCheck?type=ct");//医疗
        Data.getnfclist().add("xcx:gh_f4803b06a633path:pages/medicineList/medicineList");//医疗
        Data.getnfclist().add("xcx:gh_f4803b06a633path:pages/activeCheck/activeCheck?type=xray");//医疗
        Data.getnfclist().add("xcx:gh_f4803b06a633path:pages/detail/detail?departmentid=2");//医疗
        Data.getnfclist().add("xcx:gh_f4803b06a633path:pages/jiuzheng/jiuzheng");//医疗*/
        for(int i=0;i<Data.getnfclist().size();i++)
        {
            System.out.println(Data.getnfclist().get(i));
        }
        /*imageView= view.findViewById(R.id.nfc_view);
        adapter=new TagAdapter(getActivity(), R.layout.tag_item, Data.getTagList1());
        ListView listView=(ListView) view.findViewById(R.id.tag_list1);
        listView.setAdapter(adapter);
        button= view.findViewById(R.id.nfcbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(Data.getTagList().get(0).toString());
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View textEntryView = factory.inflate(R.layout.dialog,null);
                final EditText editTextTitle = (EditText)textEntryView.findViewById(R.id.editTextTitle);
                final EditText editTextContent = (EditText)textEntryView.findViewById(R.id.editTextContent);
                AlertDialog.Builder inputDialog=new AlertDialog.Builder(getActivity());
                inputDialog.setTitle("请输入增加的合法标签信息");
                inputDialog.setView(textEntryView);
                inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(),editTextContent.getText().toString(),Toast.LENGTH_SHORT).show();
                        TagClass item=new TagClass(editTextTitle.getText().toString(),editTextContent.getText().toString());
                        Data.tagListAdd1(item);
                        System.out.println(Data.getTagList1());
                        //saveData(editTextTitle.getText().toString(),editTextContent.getText().toString());
                        adapter.notifyDataSetChanged();
                    }
                }).show();
            }
        });*/
       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Data.getIsactive()){
                    case 1:
                        imageView.setImageResource(R.drawable.nfckaifa);
                        Toast.makeText(getActivity(),"开发者模式已开启",Toast.LENGTH_SHORT).show();
                        Data.change();
                        break;
                    case 0:
                        imageView.setImageResource(R.drawable.nfcyingyong);
                        Toast.makeText(getActivity(),"应用者模式已开启",Toast.LENGTH_SHORT).show();
                        Data.change();

                        break;
                    default:
                        break;
                }
            }
        });*/
        return view;
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
                    mTagText += textRecord + "\n\ntext\n" + contentSize + " bytes";
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
    private void saveData(String title){
        number++;
        SharedPreferences.Editor editor= getContext().getSharedPreferences("nfc", MODE_PRIVATE).edit();
        Log.d("abcde","i is "+number);
        editor.putString(title,title);
        editor.commit();
        Log.d("after commit i",number+"");
    }
    private void deleteData(String title){
        number--;
        SharedPreferences.Editor editor=getContext().getSharedPreferences("nfc", MODE_PRIVATE).edit();
        editor.remove(title);
        editor.commit();
    }

}
