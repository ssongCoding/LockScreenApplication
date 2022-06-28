package com.example.lockscreenapplication;


/*** MainActivity.class - LockActivity.class로 왔다 갔다 할 때 쓸 Flag ***/
// final = 상수; 값 초기화 후 변경 불가능
public class INTENT_TYPE {
    public static final String TYPE = "type";
    public static final int SET_LOCK = 1;      // PIN 설정
    public static final int SET_UNLOCK = 2;   // PIN 비활성화
    public static final int UNLOCK = 3;       // 잠금 해제
}
