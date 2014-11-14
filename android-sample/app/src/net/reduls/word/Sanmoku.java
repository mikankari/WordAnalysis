package net.reduls.word;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.app.Activity;
import android.content.res.AssetManager; //アセット
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Sanmoku extends Activity implements TextWatcher
{
	//public String noun;
	public StringBuilder sb = new StringBuilder(); //歌詞読み込み用
	public StringBuilder noun = new StringBuilder(); //名詞抽出用
	public int total = 0; //名詞の総数を記録
	public String test = "test";
	//可変長配列
	public ArrayList<String> noun_word = new ArrayList<String>(); //抽出した名詞いれる
	//歌詞リスト
	int array = 15;
	int count = 0;
	public String[] category = new String[array];
	//歌詞の分類
	public int[] season = new int[4]; //0...春　1...夏　2...秋　3...冬
	public int[] weather = new int[4]; //0...晴れ　1...曇り　2...雨　3...雪
	public int[] time = new int[5]; //0...朝　1...昼　2...夕　3...夜　4...深夜
	
	//単語
	public ArrayList<String> spring = new ArrayList<String>();
	public ArrayList<String> summer = new ArrayList<String>();
	public ArrayList<String> autumn = new ArrayList<String>();
	public ArrayList<String> winter = new ArrayList<String>();
	
	public ArrayList<String> sunny = new ArrayList<String>();
	public ArrayList<String> cloudy = new ArrayList<String>();
	public ArrayList<String> rain = new ArrayList<String>();
	public ArrayList<String> snow = new ArrayList<String>();
	
	public ArrayList<String> morning = new ArrayList<String>();
	public ArrayList<String> noon = new ArrayList<String>();
	public ArrayList<String> evening = new ArrayList<String>();
	public ArrayList<String> night = new ArrayList<String>();
	public ArrayList<String> midnight = new ArrayList<String>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	Arrays.fill(season, 0);
    	Arrays.fill(weather, 0);
    	Arrays.fill(time, 0);
    	//季節 spring summer autumn winter
    	category[0] = "春　桜　さくら　サクラ　三月　四月　五月　蝶　卒業";
    	category[1] = "夏　梅雨　海　アイス　入道雲　六月　七月　八月　蛍　ホタル　ほたる　サマー　熱　花火　熱帯夜";
    	category[2] = "秋　落ち葉　枯れ葉　九月　十月　十一月　ハロウィン";
    	category[3] = "冬　枯れ木　雪　十二月　一月　二月　マフラー　イルミネーション　クリスマス　暖炉";
    	
    	//↓これらはサーバーにおくのか？↓
    	//季節　春
    	spring.add(new String("春")); spring.add(new String("桜")); spring.add(new String("さくら"));
    	spring.add(new String("サクラ")); spring.add(new String("三月")); spring.add(new String("四月"));
    	spring.add(new String("五月")); spring.add(new String("蝶")); spring.add(new String("卒業"));
    	//季節　夏
    	summer.add(new String("夏")); summer.add(new String("梅雨")); summer.add(new String("海"));
    	summer.add(new String("アイス")); summer.add(new String("入道雲")); summer.add(new String("六月"));
    	summer.add(new String("七月")); summer.add(new String("八月")); summer.add(new String("九月"));
    	summer.add(new String("蛍")); summer.add(new String("ホタル")); summer.add(new String("ほたる"));
    	summer.add(new String("サマー")); summer.add(new String("熱")); summer.add(new String("花火"));
    	summer.add(new String("熱帯夜"));
    	//季節　秋
    	autumn.add(new String("秋")); autumn.add(new String("落ち葉")); autumn.add(new String("枯れ葉"));
    	autumn.add(new String("九月")) ;autumn.add(new String("十月")); autumn.add(new String("十一月"));
    	autumn.add(new String("ハロウィン"));
    	//季節　冬
    	winter.add(new String("冬")); winter.add(new String("枯れ木")); winter.add(new String("雪"));
    	winter.add(new String("十二月")); winter.add(new String("一月")); winter.add(new String("二月"));
    	winter.add(new String("マフラー")); winter.add(new String("イルミネーション")); winter.add(new String("クリスマス"));
    	winter.add(new String("暖炉"));
    	
    	//天気sunny cloudy rain snow
    	category[4] = "晴　太陽　日ざし　日差し　陽射し　日光　暑";
    	category[5] = "曇り空　雲　曇り　積乱雲　霧";
    	category[6] = "雨　レイン　台風";
    	category[7] = "雪　冬　ソリ　スキー　暖炉";
    	
    	//晴れ
    	sunny.add(new String("晴")); sunny.add(new String("太陽")); sunny.add(new String("日差し"));
    	sunny.add(new String("陽射し")); sunny.add(new String("日光")); sunny.add(new String("暑"));
    	//曇り
    	cloudy.add(new String("曇り")); cloudy.add(new String("曇り空")); cloudy.add(new String("積乱雲"));
    	cloudy.add(new String("霧"));
    	//雨
    	rain.add(new String("雨")); rain.add(new String("レイン")); rain.add(new String("台風"));
    	//雪
    	snow.add(new String("雪")); snow.add(new String("冬")); snow.add(new String("ソリ"));
    	snow.add(new String("スキー")); snow.add(new String("暖炉"));
    	
    	//時間 morning noon evening night midnight
    	category[8] = "朝　明け方　太陽　日光　日差し　陽射し　おはよう";
    	category[9] = "昼　昼下がり";
    	category[10] = "夕　夕方";
    	category[11] = "夜　眠れない　おやすみ　朝まで　ナイト　ベッド　死　星　月　night";
    	category[12] = "深夜　午前二時　ベッド　ナイト";
    	
    	//朝
    	morning.add(new String("朝")); morning.add(new String("明け方")); morning.add(new String("太陽"));
    	morning.add(new String("日光")); morning.add(new String("陽射し")); morning.add(new String("日差し"));
    	morning.add(new String("おはよう"));
    	//昼
    	noon.add(new String("昼")); noon.add(new String("弁当")); noon.add(new String("正午"));
    	//夕
    	evening.add(new String("夕"));
    	//夜
    	night.add(new String("夜")); night.add(new String("おやすみ")); night.add(new String("ナイト"));
    	night.add(new String("ベッド")); night.add(new String("死")); night.add(new String("星"));
    	night.add(new String("月")); night.add(new String("night")); night.add(new String("寝"));
    	//深夜
    	midnight.add(new String("深夜")); midnight.add(new String("午前二時")); midnight.add(new String("ベッド"));
    	midnight.add(new String("ナイト")); midnight.add(new String("寝"));
    	
    	//場所
    	//山　海
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        View searchBar = findViewById(R.id.search_bar);
        ((EditText)searchBar).addTextChangedListener(this);

        String key = getIntent().getStringExtra("search.key");
        getIntent().putExtra("search.key","");
        if(key!=null && key.equals("")==false)
            ((EditText)searchBar).setText(key);
        
        //ボタンクリック処理
        //形態素解析ボタン
		Button analysbtn = (Button) this.findViewById(R.id.analysbtn);
		analysbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				analysbotton_OnClick(v);
			}
		});
		
		//歌詞解析ボタン
		Button analysisbtn = (Button) this.findViewById(R.id.analysisbtn);
		analysisbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				wordanalysisbotton_OnClick(v);
			}
		});
		//ボタンクリック処理ここまで
    }
    
	

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
    
    public void afterTextChanged(Editable s) {
        String key = s.toString();
        //String key = "YO! SAY! 夏が胸を刺激する 生足魅惑のマーメイド 出すとこだして たわわになったら 宝物の恋は やれ爽快 誤魔化し聞かない 薄着の曲線は 確信犯の しなやかなSTYLE 耐水性の 気持ちに切り替わる  瞬間のまぶしさは いかがなもの 心まで脱がされる 熱い風の誘惑に 負けちゃって構わないから 真夏は 不祥事も 君次第で 妖精たちが 夏を刺激する 生足魅惑のマーメイド 出すとこだして たわわになったら 宝物の恋が できそうかい 君じゃなくても バテ気味にもなるよ 暑いばっかの 街は憂鬱で スキを見せたら 不意に耳に入る サブいギャグなんかで 涼みたくない むせ返る熱帯夜を 彩る花火のように 打ち上げて散る思いなら  このまま抱き合って 焦がれるまで 妖精たちと 夏をしたくなる 熱い欲望はトルネイド 出すものだして 素直になりたい 君と僕となら it`s all right 都会のビルの上じゃ 感じなくなってる君を 冷えたワインの口づけで 酔わせて とろかせて 差し上げましょう 妖精たちが 夏を刺激する 生足ヘソ出しマーメイド 恋にかまけて お留守になるのも ダイスケ的にも オールオーケー YO! SAY! 夏を誰としたくなる 一人根の夜に you can say good bye 奥の方まで 乾く間ないほど 宝物の恋を しま鮮花";
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
    
    //形態素ボタンをクリックしたとき
	private void analysbotton_OnClick(View v) {
		AssetManager as = getResources().getAssets();

		InputStream is = null;
		BufferedReader br = null;

		//StringBuilder sb = new StringBuilder();
		try {
			try {
				//歌詞を読み込む
				//is = as.open("hotlimit.txt"); //HOT LIMIT 夏の歌
				//is = as.open("roshin_yuukai.txt"); //炉心融解　春で夜、深夜の歌
				//is = as.open("sakura_no_kisetsu.txt"); //桜の季節　春の歌
				is = as.open("utsukushiki_mono.txt"); //美しきもの　朝夜春夏秋冬全部の歌
				br = new BufferedReader(new InputStreamReader(is));

				String str;
				while ((str = br.readLine()) != null) {
					sb.append(str + "\n");
				}
			} finally {
				if (br != null)
					br.close();
			}
		} catch (IOException e) { // 例外処理
			Toast.makeText(this, "読み込み失敗", Toast.LENGTH_SHORT).show();
		}
		//解析用の文字列にファイルの中身（sb）をコピー
		String key =new String(sb);
		//↓リザルトエリアに表示しますよーのやつ？
    	LinearLayout resultArea = (LinearLayout)findViewById(R.id.search_result_area);
        resultArea.removeAllViews();
        
        TextView label = (TextView) this.findViewById(R.id.label);
        
        if(key.length() > 0) { 
            for(net.reduls.sanmoku.Morpheme e : net.reduls.sanmoku.Tagger.parse(key)) {
                TextView txt = new TextView(this);
                SpannableString spannable = DataFormatter.format("<"+e.surface+">\n"+e.feature);
                txt.setText(spannable, TextView.BufferType.SPANNABLE);
                //String word = new String(spannable, TextView.BufferType.SPANNABLE);
                resultArea.addView(txt); //結果の表示
                //e.surface　は　元の単語
                //e.feature　は　品詞
                
                //名詞を抽出
                if (e.feature.contains("名詞")) {//一致の場合trueが帰ってくる
                	Log.d("たしかめ","名詞と判断されました");
                	noun.append(e.surface + "　");
                	total++; //名詞の総数カウント
                	Log.d("たしかめ","可変長配列に追加");
                	noun_word.add(new String(e.surface));                	
                }
                //label.setText(new String(noun + "\n名詞は" + total + "個"));
                //label.setText(total+""); //label.setText(total);にするとエラー吐くから空の文字はいれる
            }
            
        }
        //test = noun_word.get(100);
        label.setText("名詞の個数" + total + "試しに表示→" +noun_word.get(54) + "\n" + noun);
        
	}//解析ボタンクリックした処理終わり
	//メモ
	//tf法
	//単語の頻度／文章で出現する総単語数
	private void wordanalysisbotton_OnClick(View v) {
	   	Arrays.fill(season, 0);
    	Arrays.fill(weather, 0);
    	Arrays.fill(time, 0);
	    for (int i = 0; i < noun_word.size(); i++){
	    	Log.d("たしかめ", "配列" +i+ "の名詞" );
	    	//季節
	    	for(int j = 0; j < spring.size(); j++){
	    		if (noun_word.get(i).contains(spring.get(j))){
	    			season[0]++;
	    		}
	    	}
	    	for(int j = 0; j < summer.size(); j++){
	    		if (noun_word.get(i).contains(summer.get(j))){
	    			season[1]++;
	    		}
	    	}
	    	for(int j = 0; j < autumn.size(); j++){
	    		if (noun_word.get(i).contains(autumn.get(j))){
	    			season[2]++;
	    		}
	    	}
	    	for(int j = 0; j < winter.size(); j++){
	    		if (noun_word.get(i).contains(winter.get(j))){
	    			season[3]++;
	    		}
	    	}
	    	
	    	//天気
	    	for(int j = 0; j < sunny.size(); j++){
	    		if (noun_word.get(i).contains(sunny.get(j))){
	    			weather[0]++;
	    		}
	    	}
	    	for(int j = 0; j < cloudy.size(); j++){
	    		if (noun_word.get(i).contains(cloudy.get(j))){
	    			weather[1]++;
	    		}
	    	}
	    	for(int j = 0; j < rain.size(); j++){
	    		if (noun_word.get(i).contains(rain.get(j))){
	    			weather[2]++;
	    		}
	    	}
	    	for(int j = 0; j < snow.size(); j++){
	    		if (noun_word.get(i).contains(snow.get(j))){
	    			weather[3]++;
	    		}
	    	}
	    	
	    	//時間
	    	for(int j = 0; j < morning.size(); j++){
	    		if (noun_word.get(i).contains(morning.get(j))){
	    			time[0]++;
	    		}
	    	}
	    	for(int j = 0; j < noon.size(); j++){
	    		if (noun_word.get(i).contains(noon.get(j))){
	    			time[1]++;
	    		}
	    	}
	    	for(int j = 0; j < evening.size(); j++){
	    		if (noun_word.get(i).contains(evening.get(j))){
	    			time[2]++;
	    		}
	    	}
	    	for(int j = 0; j < night.size(); j++){
	    		if (noun_word.get(i).contains(night.get(j))){
	    			time[3]++;
	    		}
	    	}
	    	for(int j = 0; j < midnight.size(); j++){
	    		if (noun_word.get(i).contains(midnight.get(j))){
	    			time[4]++;
	    		}
	    	}
	      }
	    TextView label2 = (TextView) this.findViewById(R.id.label2);
	    //ためしに表示
	    label2.setText("春の要素" + season[0] + 
	    			   " 夏の要素" + season[1] + 
	    			   " 秋の要素" + season[2] +
	    			   " 冬の要素" + season[3] +
	    			   " 晴れの要素" + weather[0] +
	    			   " 曇りの要素" + weather[1] +
	    			   " 雨の要素" + weather[2] +
	    			   " 雪の要素" + weather[3] +
	    			   " 朝の要素" + time[0] +
	    			   " 昼の要素" + time[1] +
	    			   "　夕方の要素" + time[2] +
	    			   " 夜の要素" + time[3] +
	    			   " 深夜の要素" + time[4]);
	    
	}
}