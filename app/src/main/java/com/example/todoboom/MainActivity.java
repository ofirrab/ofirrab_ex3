package com.example.todoboom;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.LauncherApps;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    private static String TAG ="MainActivity";
    private ArrayList<String> mNames = new ArrayList<>();
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText1 = (EditText)findViewById(R.id.input_text);
        Button button1 = (Button)findViewById(R.id.input_button);
        final TextView TextError = (TextView) findViewById(R.id.errorText);

        if(savedInstanceState != null  && savedInstanceState.getString("names") != null){

            String saved_string = savedInstanceState.getString("names");
            //Log.d(TAG, saved_string);

            String[] split_string = saved_string.split(",");
            mNames.addAll(Arrays.asList(split_string));
            initRecyclerView();
        }
        else{
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            String pref_name_string = sp.getString("mNames", null);  // We got a string
            if(pref_name_string != null && pref_name_string.length()>0){
                String[] pref_name_list = pref_name_string.split(",");
                mNames.addAll(Arrays.asList(pref_name_list));
                initRecyclerView();
            }
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_text = editText1.getText().toString();
                if(!new_text.equals("")){
                    mNames.add(new_text);
                    initRecyclerView();
                    editText1.setText("");
                    TextError.setText("");
                }
                else{
                    TextError.setText("You can't create an empty TODO item, oh silly!");
                }
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("mNames", list2string());
                editor.apply();
                initRecyclerView();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mNames.size()>0){
            outState.putString("names", list2string());
        }
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.my_recyclerview);
        final RecyclerViewAdaptor adaptor = new RecyclerViewAdaptor(this, mNames);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor.setOnItemClickListener(new RecyclerViewAdaptor.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                if(!mNames.get(position).contains("Pressed")){
                    String new_string = "Pressed " + mNames.get(position);
                    mNames.set(position, new_string);
                    adaptor.notifyItemChanged(position);

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("mNames", list2string());
                    editor.apply();
                    initRecyclerView();
                }
            }  // OnItemClick

            @Override
            public boolean onLongClick(int position) {
                Context context = getApplicationContext();
                //Log.d(TAG, my_new);
                openMyDialog(position);
                return true;
            }  // onLongClick
        });
    }  // initRecyclerView

    public void openMyDialog(final int position){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.setDeleteClickListener(new SimpleDialog.OnDeleteClickListener() {
            @Override
            public void onClickYes() {
                my_delete(position);
                Log.d(TAG, "user give a permission to delete");
            }
            @Override
            public void onClickNo() {
                // delete has been canceled
                Log.d(TAG, "delete has been canceled");
            }
        });
        simpleDialog.show(getSupportFragmentManager(), "dialog");
    }  //  openMyDialog

    private void my_delete(int position){
        mNames.remove(position);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("mNames", list2string());
        initRecyclerView();
        editor.apply();
    }

    private String list2string()
    {
        String new_string = "";
        if(mNames.size()>0){
            for (int i=0; i<mNames.size(); i++){
                new_string = new_string + mNames.get(i)+",";
            }
        }
        return new_string;
    }
}  //  MainActivity


