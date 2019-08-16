package com.example.a13162.activitytest;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class FragmentTag extends Fragment {

    private String[] data={};
    private Button button;
    private int i=0;
    private int k=0;
    private MainActivity activity;

    public TagAdapter adapter;



    public FragmentTag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("abcd","create fg view");
        SharedPreferences pref=getContext().getSharedPreferences("data", MODE_PRIVATE);
        i=pref.getInt("number",0);

        Log.d("first i",i+"");
        //防止多次切换重复读取
        if(i> Data.getTagList().size()){
            Log.d("abcd","i is "+i);
            String editTextTitle,editTextContent;
            for(k=1;k<=i;k++) {
                editTextTitle=pref.getString("title"+k, "");
                editTextContent=pref.getString("content"+k, "");
                TagClass item=new TagClass(editTextTitle,editTextContent);
                Data.tagListAdd(item);
            }
        }
        View view=inflater.inflate(R.layout.fragment_tag_layout, container, false);
        adapter=new TagAdapter(getActivity(), R.layout.tag_item, Data.getTagList());
        ListView listView=(ListView) view.findViewById(R.id.tag_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TagClass tag=(TagClass) Data.getTagList().get(position);
                Toast.makeText(getActivity(),tag.getTitle(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),tag.getText(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TagPageActivity.class);
                intent.putExtra("extra_data",position);
                startActivity(intent);
            }
        });
        button=view.findViewById(R.id.tagaddbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
        return view;
    }
    public void showInputDialog(){
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View textEntryView = factory.inflate(R.layout.dialog,null);

        final EditText editTextTitle = (EditText)textEntryView.findViewById(R.id.editTextTitle);
        final EditText editTextContent = (EditText)textEntryView.findViewById(R.id.editTextContent);

        AlertDialog.Builder inputDialog=new AlertDialog.Builder(getActivity());
        inputDialog.setTitle("请输入要存取的id信息");
        inputDialog.setView(textEntryView);
        inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getActivity(),editTextContent.getText().toString(),Toast.LENGTH_SHORT).show();
                TagClass item=new TagClass(editTextTitle.getText().toString(),editTextContent.getText().toString());
                Data.tagListAdd(item);

                saveData(editTextTitle.getText().toString(),editTextContent.getText().toString());

                adapter.notifyDataSetChanged();

            }
        }).show();
    }

    public void showInputDialog(String text){
        final String atext=text;
        getAvailableActivity(new IActivityEnabledListener() {
            @Override
            public void onActivityEnabled(FragmentActivity activity) {
               LayoutInflater factory = LayoutInflater.from(getActivity());
                final View textEntryView = factory.inflate(R.layout.dialog,null);

                final EditText editTextTitle = (EditText)textEntryView.findViewById(R.id.editTextTitle);
                final EditText editTextContent = (EditText)textEntryView.findViewById(R.id.editTextContent);

                AlertDialog.Builder inputDialog=new AlertDialog.Builder(getActivity());
                inputDialog.setTitle("请输入要存取的id信息");
                inputDialog.setView(textEntryView);
                editTextTitle.setText("nfc tag");
                editTextContent.setText(atext);
                inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TagClass item=new TagClass(editTextTitle.getText().toString(),editTextContent.getText().toString());
                        Data.tagListAdd(item);

                        saveData(editTextTitle.getText().toString(),editTextContent.getText().toString());

                        adapter.notifyDataSetChanged();

                    }
                }).show();
            }
        });


    }

    private void saveData(String title,String content){

        i++;
        SharedPreferences.Editor editor= getContext().getSharedPreferences("data", MODE_PRIVATE).edit();
        Log.d("abcde","i is "+i);
        editor.putString("title"+i,title);
        editor.putString("content"+i,content);
        editor.putInt("number",i);
        editor.commit();
        Log.d("after commit i",i+"");

    }

    //用来解决getActivity()返回null的问题
    protected IActivityEnabledListener aeListener;

    protected interface IActivityEnabledListener{
        void onActivityEnabled(FragmentActivity activity);
    }

    protected void getAvailableActivity(IActivityEnabledListener listener){
        if (getActivity() == null){
            aeListener = listener;

        } else {
            listener.onActivityEnabled(getActivity());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (aeListener != null){
            aeListener.onActivityEnabled((FragmentActivity) activity);
            aeListener = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("abcd","ft attach");
        if (aeListener != null){
            aeListener.onActivityEnabled((FragmentActivity) activity);
            aeListener = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        SharedPreferences pref=getContext().getSharedPreferences("data", MODE_PRIVATE);
        i=pref.getInt("number",0);
        Log.d("abcd","ft onresume");
        Log.d("resume i",i+"");
    }
}
