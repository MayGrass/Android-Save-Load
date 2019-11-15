package tw.org.iii.saveloadfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView content;
    private File sdroot, approot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //要讀取跟存取外部資料的權限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    42687);
        } else {
            init();
        }
    }

    //允許或拒絕的反應
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 42687) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            } else {
                finish();
            }
        }
    }

    private void init() {
        content = findViewById(R.id.content);
        sp = getSharedPreferences("DCH", MODE_PRIVATE);
        editor = sp.edit();

        sdroot = Environment.getExternalStorageDirectory();
        Log.v("DCH", sdroot.getAbsolutePath());

        approot = new File(sdroot, "Android/data/'" + getPackageName());
        //如果不存在就建一個Package
        if (!approot.exists()) {
            approot.mkdirs();
        }
    }

    public void test1(View view) {
        //存資料data/data/appname/shared_preds
        editor.putString("username", "DCH");
        editor.putBoolean("sound", false);
        editor.putInt("stage", 4);
        editor.commit();
        Toast.makeText(this,"save ok", Toast.LENGTH_SHORT).show();
    }

    public void test2(View view) {
        boolean isSound = sp.getBoolean("sound", true);
        String username = sp.getString("username", "nobody");
        int stage = sp.getInt("stage", 1);
        Log.v("DCH", username + ":" + stage + ":" + isSound);
    }

    //以下開始都需要讀寫權限
    public void test3(View view) {
        try {
            //資料存在data/data/appname/files裡
            FileOutputStream fout = openFileOutput("DCH.txt", MODE_APPEND); //創檔名DCH.txt (不一定需要副檔名)
            fout.write("Hello World\n".getBytes()); //字串轉Byte
            fout.flush();
            fout.close();
            Toast.makeText(this, "Save OK", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.v("DCH", e.toString());
        }
    }

    public void test4(View view) {
        try(FileInputStream fin = openFileInput("DCH.txt")) { //自動關閉auto close，不用另外寫.close
            StringBuffer sb = new StringBuffer();
            byte[] buf = new byte[1024]; int len;
            while ((len = fin.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
                //Log.v("DCH", "=>" + (char)c); //預設是ANCII CODE 轉成char才可閱讀
            }
            content.setText(sb.toString());
        } catch (Exception e) {
            Log.v("DCH", e.toString());
        }
    }

    public void test5(View view) {
        //寫入檔案
        File file1 = new File(sdroot, "DCH.ok");
        try {
            FileOutputStream fout = new FileOutputStream(file1);
            fout.write("Hello DCH".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this, "Save OK5", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.v("DCH", e.toString());
        }
    }

    public void test6(View view) {
        //寫入檔案
        File file1 = new File(approot, "DCH.ok");
        try {
            FileOutputStream fout = new FileOutputStream(file1);
            fout.write("Hello DCH".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this, "Save OK6", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.v("DCH", e.toString());
        }
    }
}
