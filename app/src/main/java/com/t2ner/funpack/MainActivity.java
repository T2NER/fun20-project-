package com.t2ner.funpack;

import android.Manifest;
import android.app.*;
import android.os.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.Uri;
import android.provider.Settings;
import android.view.*;
import android.widget.*;
import android.hardware.*;
import java.io.*;
import java.util.*;

public class MainActivity extends Activity implements SensorEventListener {
    LinearLayout root; TextView result; Random rnd=new Random(); SensorManager sm; Sensor accel, magnet; boolean armed=false; float baseline=-1; MediaRecorder recorder; String audioPath; long startMs;
    String[] roasts={"You bring everyone so much joy when you leave the group chat.","Your confidence has excellent Wi-Fi and terrible evidence.","You are proof that autocorrect cannot fix everything.","Your comeback is still buffering.","You have the energy of a software update at 2 percent battery.","Your train of thought has replacement bus service.","You could lose an argument with a loading screen.","Your master plan appears to be mostly decorative."};
    String[] most={"Who is most likely to forget why they walked into a room?","Who is most likely to survive a zombie movie by pure accident?","Who is most likely to argue with GPS?","Who is most likely to become famous for something ridiculous?","Who is most likely to own 14 chargers and find none?","Who is most likely to laugh at the worst possible moment?"};
    String[] dares={"Speak like a movie trailer narrator for one minute.","Let someone choose your phone wallpaper for ten minutes.","Do your best robot dance for 20 seconds.","Invent a commercial for the nearest object.","Talk without using the letter E for 30 seconds.","Make up a dramatic weather report for this room."};
    String[] orb={"Absolutely.","Not today.","The odds look weirdly good.","Ask again after snacks.","Probably, but do not brag yet.","The universe has left you on read.","Yes, with one annoying complication.","Nope. Save yourself the trouble."};
    String[] bark={"I heard a snack wrapper.","This is my side of the couch now.","The mail carrier knows what they did.","Walk. Now. No further questions.","I require tribute in cheese.","I was not barking. I was making an announcement."};
    String[] baby={"I have filed a complaint about bedtime.","That spoon is unacceptable.","I demand the blue cup for reasons I cannot explain.","I am tired but I refuse to cooperate.","Please return the object I threw away.","This meeting could have been a nap."};

    @Override public void onCreate(Bundle b){ super.onCreate(b); build(); }
    TextView tv(String s,int sp){ TextView v=new TextView(this); v.setText(s); v.setTextSize(sp); v.setTextColor(Color.WHITE); v.setPadding(18,14,18,14); return v; }
    Button btn(String s){ Button b=new Button(this); b.setText(s); b.setAllCaps(false); b.setTextSize(17); b.setPadding(12,12,12,12); return b; }
    EditText input(String hint){ EditText e=new EditText(this); e.setHint(hint); e.setHintTextColor(0xffaab0bb); e.setTextColor(Color.WHITE); e.setSingleLine(false); return e; }
    void build(){
      root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(28,28,28,28); root.setBackgroundColor(0xff111318);
      ScrollView sv=new ScrollView(this); sv.addView(root); setContentView(sv);
      root.addView(tv(getString(getResources().getIdentifier("app_name","string",getPackageName())),30));
      result=tv("Ready.",22); String k=BuildConfig.APP_KEY;
      switch(k){
        case "truthTap": truth(); break; case "roastButton": roast(); break; case "bsMeter": bs(); break; case "fakeCall": fakeCall(false); break;
        case "mostLikely": simpleRandom("NEXT PROMPT",most,"Party prompt generator — keep it friendly."); break; case "dareDrop": simpleRandom("DROP A DARE",dares,"Original party dares."); break;
        case "settleIt": settle(); break; case "screenCrack": crack(); break; case "voiceWarp": voice(); break; case "prankDeck": prank(); break;
        case "ageGuess": photoNovelty(true); break; case "faceScore": photoNovelty(false); break; case "barkBack": audioTranslate(true); break; case "babbleBack": audioTranslate(false); break;
        case "spookyRadar": spooky(); break; case "spinDecide": spin(); break; case "askOrb": ask(); break; case "escapeCall": fakeCall(true); break;
        case "tapReflex": reflex(); break; case "pocketGuard": guard(); break;
      }
    }
    void note(String s){ root.addView(tv(s,15)); }
    void truth(){ note("Entertainment simulation — not a real lie detector."); Button b=btn("SCAN"); root.addView(b); root.addView(result); b.setOnClickListener(v->{ result.setText("Scanning…"); new Handler().postDelayed(()->result.setText(rnd.nextBoolean()?"TRUTH-ish ✓":"SUSPICIOUS ✕"),1100);}); }
    void roast(){ note("Original randomized roasts. No copied celebrity or meme audio."); Button b=btn("ROAST ME"); root.addView(b); root.addView(result); b.setOnClickListener(v->result.setText(roasts[rnd.nextInt(roasts.length)])); }
    void bs(){ note("Entertainment simulation — not a factual detector."); Button b=btn("CHECK BS LEVEL"); root.addView(b); root.addView(result); b.setOnClickListener(v->result.setText((10+rnd.nextInt(91))+"% BS")); }
    void simpleRandom(String label,String[] arr,String n){ note(n); Button b=btn(label); root.addView(b); root.addView(result); b.setOnClickListener(v->result.setText(arr[rnd.nextInt(arr.length)])); }
    void fakeCall(boolean quick){ note("Simulated call screen. It does not place or receive a real telephone call."); EditText who=input("Caller name"); who.setText(quick?"Emergency Exit":"Alex"); root.addView(who); Button b=btn(quick?"START 10-SECOND ESCAPE CALL":"SIMULATE CALL IN 3 SECONDS"); root.addView(b); root.addView(result); b.setOnClickListener(v->{int sec=quick?10:3; result.setText("Call in "+sec+" seconds…"); new Handler().postDelayed(()->showCall(who.getText().toString()),sec*1000L);}); }
    void showCall(String who){ root.removeAllViews(); root.setGravity(Gravity.CENTER); root.addView(tv("SIMULATED INCOMING CALL",17)); root.addView(tv(who.length()==0?"Unknown Caller":who,34)); Button a=btn("Answer"); Button d=btn("Decline"); root.addView(a); root.addView(d); a.setOnClickListener(v->{Toast.makeText(this,"Simulated call answered",Toast.LENGTH_SHORT).show(); build();}); d.setOnClickListener(v->build()); }
    void settle(){ note("A random tie-breaker, not advice."); EditText a=input("Choice A"); EditText b=input("Choice B"); root.addView(a); root.addView(b); Button go=btn("SETTLE IT"); root.addView(go); root.addView(result); go.setOnClickListener(v->{String x=a.getText().toString(),y=b.getText().toString(); if(x.isEmpty()||y.isEmpty()) result.setText("Enter both choices."); else result.setText("Decision: "+(rnd.nextBoolean()?x:y));}); }
    void crack(){ note("Clearly labeled visual prank. Your screen is not damaged."); Button b=btn("CRACK THE SCREEN"); root.addView(b); root.addView(result); b.setOnClickListener(v->{ root.removeAllViews(); root.addView(tv("SIMULATED CRACK — TAP TO RESET",16)); CrackView cv=new CrackView(this); root.addView(cv,new LinearLayout.LayoutParams(-1,1100)); cv.setOnClickListener(x->build());}); }
    class CrackView extends View { Paint p=new Paint(1); CrackView(Context c){super(c);p.setColor(Color.WHITE);p.setStrokeWidth(4);} protected void onDraw(Canvas c){super.onDraw(c); c.drawColor(Color.BLACK); float cx=getWidth()*.52f, cy=getHeight()*.42f; Random r=new Random(7); for(int i=0;i<22;i++){Path path=new Path(); path.moveTo(cx,cy); float x=cx,y=cy; for(int j=0;j<6;j++){x+=(r.nextFloat()-.5f)*260; y+=80+r.nextFloat()*100; path.lineTo(x,y);} c.drawPath(path,p);} }}
    boolean mic(){ if(Build.VERSION.SDK_INT>=23 && checkSelfPermission(Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED){ requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},44); return false;} return true; }
    void voice(){ note("Audio stays on this device. Grant microphone only when recording."); Button rec=btn("RECORD 4 SECONDS"); Button low=btn("PLAY LOW"); Button high=btn("PLAY HIGH"); root.addView(rec);root.addView(low);root.addView(high);root.addView(result); rec.setOnClickListener(v->{if(mic())recordFour();}); low.setOnClickListener(v->playWarp(.75f,.75f)); high.setOnClickListener(v->playWarp(1.35f,1.35f)); }
    void recordFour(){ try{ audioPath=new File(getCacheDir(),"clip.m4a").getAbsolutePath(); recorder=new MediaRecorder(); recorder.setAudioSource(MediaRecorder.AudioSource.MIC); recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC); recorder.setOutputFile(audioPath); recorder.prepare(); recorder.start(); result.setText("Recording…"); new Handler().postDelayed(()->{try{recorder.stop(); recorder.release(); recorder=null; result.setText("Recorded. Try a playback effect.");}catch(Exception e){result.setText("Recording stopped.");}},4000);}catch(Exception e){result.setText("Could not record: "+e.getMessage());}}
    void playWarp(float speed,float pitch){ if(audioPath==null){result.setText("Record a clip first.");return;} try{MediaPlayer mp=new MediaPlayer();mp.setDataSource(audioPath);mp.prepare(); if(Build.VERSION.SDK_INT>=23)mp.setPlaybackParams(new PlaybackParams().setSpeed(speed).setPitch(pitch));mp.start();mp.setOnCompletionListener(MediaPlayer::release);}catch(Exception e){result.setText("Playback failed.");}}
    void prank(){ note("Harmless local effects only."); String[] labels={"DRAMATIC BEEP","VIBRATE","FLASH SCREEN","BUZZER"}; for(String s:labels){Button b=btn(s);root.addView(b);b.setOnClickListener(v->{String t=((Button)v).getText().toString(); if(t.equals("VIBRATE")){android.os.Vibrator vib=(android.os.Vibrator)getSystemService(VIBRATOR_SERVICE); if(Build.VERSION.SDK_INT>=26)vib.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE)); else vib.vibrate(500);} else if(t.equals("FLASH SCREEN")){root.setBackgroundColor(rnd.nextBoolean()?Color.WHITE:Color.RED);new Handler().postDelayed(()->root.setBackgroundColor(0xff111318),300);} else new ToneGenerator(AudioManager.STREAM_MUSIC,90).startTone(t.equals("BUZZER")?ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD:ToneGenerator.TONE_PROP_BEEP,500);});} }
    void photoNovelty(boolean age){ note(age?"Entertainment-only age guess. It does not perform biometric age analysis.":"Novelty score only. It does not judge real attractiveness or identity."); Button pick=btn("PICK A PHOTO");root.addView(pick);root.addView(result);pick.setOnClickListener(v->{Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.setType("image/*");i.addCategory(Intent.CATEGORY_OPENABLE);startActivityForResult(i, age?71:72);}); }
    @Override protected void onActivityResult(int r,int c,Intent d){super.onActivityResult(r,c,d);if(c==RESULT_OK&&d!=null&&d.getData()!=null){int h=Math.abs(d.getData().toString().hashCode());if(r==71)result.setText("Fun guess: "+(18+(h%55))+" years");else if(r==72)result.setText("Fun score: "+(55+(h%46))+" / 100");}}
    void audioTranslate(boolean dog){ note((dog?"Dog":"Baby")+" translator is entertainment only; no claim of real language translation."); Button b=btn("RECORD 3 SECONDS & TRANSLATE");root.addView(b);root.addView(result);b.setOnClickListener(v->{if(!mic())return; result.setText("Listening…"); try{audioPath=new File(getCacheDir(),"translate.m4a").getAbsolutePath(); recorder=new MediaRecorder();recorder.setAudioSource(MediaRecorder.AudioSource.MIC);recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);recorder.setOutputFile(audioPath);recorder.prepare();recorder.start();new Handler().postDelayed(()->{try{recorder.stop();recorder.release();recorder=null;}catch(Exception e){} String[] a=dog?bark:baby;result.setText("Translation: “"+a[rnd.nextInt(a.length)]+"”");},3000);}catch(Exception e){result.setText("Microphone unavailable.");}}); }
    void spooky(){ note("Entertainment simulation. Sensor motion influences the display; it does not detect ghosts."); root.addView(result); sm=(SensorManager)getSystemService(SENSOR_SERVICE); magnet=sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD); if(magnet!=null){sm.registerListener(this,magnet,SensorManager.SENSOR_DELAY_UI);result.setText("Radar active — move around.");}else result.setText("No magnetometer found; simulation mode: "+(20+rnd.nextInt(80))+"% activity"); }
    void spin(){ note("One choice is selected randomly."); EditText e=input("Choices, one per line");root.addView(e);Button b=btn("SPIN / PICK");root.addView(b);root.addView(result);b.setOnClickListener(v->{String[] a=e.getText().toString().split("\\n");ArrayList<String> z=new ArrayList<>();for(String s:a)if(!s.trim().isEmpty())z.add(s.trim());result.setText(z.isEmpty()?"Add some choices.":"Picked: "+z.get(rnd.nextInt(z.size())));}); }
    void ask(){ note("Playful random answers, not predictions or advice."); EditText e=input("Ask anything…");root.addView(e);Button b=btn("ASK THE ORB");root.addView(b);root.addView(result);b.setOnClickListener(v->result.setText(e.getText().toString().trim().isEmpty()?"Ask a question first.":orb[rnd.nextInt(orb.length)])); }
    void reflex(){ note("Tap only after GO appears."); Button b=btn("START");root.addView(b);root.addView(result);b.setOnClickListener(v->{b.setEnabled(false);result.setText("Wait…");long delay=1200+rnd.nextInt(2800);new Handler().postDelayed(()->{startMs=System.currentTimeMillis();b.setText("GO!");b.setEnabled(true);b.setOnClickListener(x->{long ms=System.currentTimeMillis()-startMs;result.setText(ms+" ms");b.setText("START AGAIN");b.setOnClickListener(y->build());});},delay);}); }
    void guard(){ note("Works while this screen remains open. No background tracking or location collection."); Button b=btn("ARM");root.addView(b);root.addView(result);sm=(SensorManager)getSystemService(SENSOR_SERVICE);accel=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);b.setOnClickListener(v->{armed=!armed;baseline=-1;b.setText(armed?"DISARM":"ARM");result.setText(armed?"Armed — movement will trigger the local alarm.":"Disarmed.");if(armed&&accel!=null)sm.registerListener(this,accel,SensorManager.SENSOR_DELAY_GAME);else if(sm!=null)sm.unregisterListener(this);}); }
    @Override public void onSensorChanged(SensorEvent e){float m=(float)Math.sqrt(e.values[0]*e.values[0]+e.values[1]*e.values[1]+e.values[2]*e.values[2]); if(BuildConfig.APP_KEY.equals("spookyRadar")){int x=Math.min(100,Math.max(0,(int)(m*2)));result.setText("Spooky activity: "+x+"%\nSimulation only.");} else if(armed){if(baseline<0)baseline=m; if(Math.abs(m-baseline)>3.2f){armed=false;result.setText("MOVEMENT DETECTED");new ToneGenerator(AudioManager.STREAM_ALARM,100).startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,2500);if(sm!=null)sm.unregisterListener(this);}} }
    @Override public void onAccuracyChanged(Sensor s,int a){}
    @Override protected void onPause(){
        super.onPause();
        if(sm!=null) sm.unregisterListener(this);
    }
    @Override protected void onResume(){
        super.onResume();
        if(sm==null) return;
        if(BuildConfig.APP_KEY.equals("spookyRadar") && magnet!=null){
            sm.registerListener(this,magnet,SensorManager.SENSOR_DELAY_UI);
        } else if(BuildConfig.APP_KEY.equals("pocketGuard") && armed && accel!=null){
            sm.registerListener(this,accel,SensorManager.SENSOR_DELAY_GAME);
        }
    }
    @Override protected void onDestroy(){super.onDestroy();if(sm!=null)sm.unregisterListener(this);if(recorder!=null){try{recorder.release();}catch(Exception e){}}}
}
