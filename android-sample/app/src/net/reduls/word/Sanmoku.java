package net.reduls.word;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.util.Log;
import android.text.Html;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.text.TextWatcher;
import android.text.Editable;
import android.text.SpannableString;

import java.util.List;

import net.reduls.word.R;

public class Sanmoku extends Activity implements TextWatcher
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        View searchBar = findViewById(R.id.search_bar);
        ((EditText)searchBar).addTextChangedListener(this);

        String key = getIntent().getStringExtra("search.key");
        getIntent().putExtra("search.key","");
        if(key!=null && key.equals("")==false)
            ((EditText)searchBar).setText(key);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
    public void afterTextChanged(Editable s) {
        //String key = s.toString();
        String key = "今日はとくべつ　蝶々、ねずみ、草や花も"
        		+ "お面をかぶり　誰が誰だかわからないよる"
        		+ "今日はとくべつ　いなくなった人達がお顏をかえて"
        		+ "この世界に紛れこむの　みどり色の回轉木馬"
        		+ "あら！お馬の上にはあの日からいない"
        		+ "あの子がほほ笑む燈りを点せ"
        		+ "濡れている暇などない　ほら此方へ手招きして　さあお迎え　あなたの家はここ柔い異形が遊ぶ　じき夏が終わるよ嗚呼泣かないで　つぎも逢いにくるから季節がめぐる　いとしい誰も彼も輪に紛れてきれいでしょう　飴雪どうかどうか召し上がれおまつりが終われば　また廻り出すうき世のお庭とくべつな夏の日に誰そかれの優しい宵闇";
    	LinearLayout resultArea = (LinearLayout)findViewById(R.id.search_result_area);
        resultArea.removeAllViews();
        
        if(key.length() > 0) { 
            for(net.reduls.sanmoku.Morpheme e : net.reduls.sanmoku.Tagger.parse(key)) {
                TextView txt = new TextView(this);
                SpannableString spannable = DataFormatter.format("<"+e.surface+">\n"+e.feature);
                txt.setText(spannable, TextView.BufferType.SPANNABLE);
                resultArea.addView(txt);
            }
        }
    }
}