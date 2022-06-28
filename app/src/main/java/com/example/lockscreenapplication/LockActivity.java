package com.example.lockscreenapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

    /*** SharedPreferences에 접근할 때 쓸 Class ***/
    private AppLock appLock;

    EditText et_pwd1, et_pwd2, et_pwd3, et_pwd4;
    String pwd;

    /*** intent로 넘겨받은 type ***/
    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        /*** View 연결 ***/
        et_pwd1 = findViewById(R.id.et_pwd1);
        et_pwd2 = findViewById(R.id.et_pwd2);
        et_pwd3 = findViewById(R.id.et_pwd3);
        et_pwd4 = findViewById(R.id.et_pwd4);

        /*** EditText에 각 Listener 달아주기 ***/
        et_pwd1.addTextChangedListener(new MyTextWatcher(LockActivity.this, et_pwd2));
        et_pwd2.addTextChangedListener(new MyTextWatcher(LockActivity.this, et_pwd3));
        et_pwd3.addTextChangedListener(new MyTextWatcher(LockActivity.this, et_pwd4));
        et_pwd4.addTextChangedListener(new MyTextWatcher(LockActivity.this, null));

        appLock = new AppLock(this);

        /*** MainActivity에서 LockActivity를 왜 불렀는지 ***/
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
    }

    /*** type에 따라 ***/
    public void actionByIntentType() {
        switch(type){
            case INTENT_TYPE.SET_LOCK:      // 잠금 설정하려고 부름
                Toast.makeText(this, "잠금 설정 하려고 부르셨네요", Toast.LENGTH_SHORT).show();
                appLock.setPassword(pwd);   // 저장
                setResult(Activity.RESULT_OK);    // 다시 MainActivity로 이동
                finish(); // 해당 Activity 종료
                break;
            case INTENT_TYPE.SET_UNLOCK:    // 잠금 설정 비활성화하려고 부름
                break;
            case INTENT_TYPE.UNLOCK:        // 잠금 해제 하려고 부름
                Toast.makeText(this, "잠금 해제 하려고 부르셨네요", Toast.LENGTH_SHORT).show();
                if(!appLock.checkPassword(pwd)){
                    Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    clear();    // EditText 초기화
                } else {    // pwd 일치하면,
                    setResult(Activity.RESULT_OK);    // 다시 MainActivity로 이동
                    finish(); // 해당 Activity 종료
                    break;
                }
        }
    }

    /*** 4개 숫자 합치기 ***/
    public void fullPassword() {
        pwd = et_pwd1.getText().toString() + et_pwd2.getText().toString()
                + et_pwd3.getText().toString() + et_pwd4.getText().toString();
    }

    /*** EditText 초기화 ***/
    public void clear() {
        et_pwd1.setText("");
        et_pwd2.setText("");
        et_pwd3.setText("");
        et_pwd4.setText("");
        et_pwd1.requestFocus(); // 제일 첫 칸으로 커서 이동
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
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        /*** 마지막 숫자가 입력되면 ***/
        if (et_to == null){
            lockActivity.fullPassword();                    // 입력된 패스워드 네 자리 합치기
            lockActivity.actionByIntentType();              // Intent Type에 따라서 할 일 하러 감
        } else {                        /*** 1번째 ~ 3번째 숫자를 입력중이면 ***/
            et_to.requestFocus();                   // 다음 EditText로 커서 이동
        }
    }

    /*** 구현 X ***/
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    /*** 구현 X ***/
    @Override
    public void afterTextChanged(Editable editable) {

    }
}
