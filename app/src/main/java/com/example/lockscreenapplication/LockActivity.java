package com.example.lockscreenapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class LockActivity extends AppCompatActivity{

    EditText et_pwd1, et_pwd2, et_pwd3, et_pwd4;
    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        /*** View 연결 ***/
        et_pwd1 = findViewById(R.id.et_pwd1);
        et_pwd2 = findViewById(R.id.et_pwd2);
        et_pwd3 = findViewById(R.id.et_pwd3);
        et_pwd4 = findViewById(R.id.et_pwd4);

        et_pwd1.addTextChangedListener(new MyTextWatcher(LockActivity.this, et_pwd2));
        et_pwd2.addTextChangedListener(new MyTextWatcher(LockActivity.this, et_pwd3));
        et_pwd3.addTextChangedListener(new MyTextWatcher(LockActivity.this, et_pwd4));
        et_pwd4.addTextChangedListener(new MyTextWatcher(LockActivity.this, null));

        pwd = et_pwd1.getText().toString() + et_pwd2.getText().toString()
                + et_pwd3.getText().toString() + et_pwd4.getText().toString();

        if (pwd.length() == 4) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(et_pwd4.getWindowToken(), 0);
        }
    }
}

class MyTextWatcher implements TextWatcher {
    private EditText et_to;
    private LockActivity lockActivity;

    public MyTextWatcher(LockActivity activity, EditText et_to){
        this.lockActivity = activity;
        this.et_to = et_to;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (et_to == null){
            lockActivity.setResult(Activity.RESULT_OK);
            lockActivity.finish();
        } else {
            et_to.requestFocus();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
