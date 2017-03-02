package alpha58.com.domobvideodemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.independent_video_demo.R;
import com.pad.android_independent_video_sdk.IndependentVideoAvailableState;
import com.pad.android_independent_video_sdk.IndependentVideoListener;
import com.pad.android_independent_video_sdk.IndependentVideoManager;

public class MainActivity extends AppCompatActivity implements IndependentVideoListener {

    private Button bt_play_cache;
    private TextView tv_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_play_cache = (Button) findViewById(R.id.bt_paly_cache);
        tv_status = (TextView) findViewById(R.id.textView);
        tv_status.setBackgroundColor(Color.parseColor("#ff0000"));
        tv_status.setText("无缓存");
        bt_play_cache.setVisibility(View.GONE);
        //初始化视频sdk
        IndependentVideoManager.newInstance().init(this);
        //设置或更新用户id，开发者用户体系中，用户的唯一识别标识
        IndependentVideoManager.newInstance().updateUserID(this, "abcd");
        //是否使用多盟提示框，提示完成任务，默认为true
        IndependentVideoManager.newInstance().disableShowAlert(this, true);
        IndependentVideoManager.newInstance().addIndependentVideoListener(this);
    }

    /*******************************
     * IndependentVideoListener
     *************************************/
    @Override
    public void videoDidStartLoad() {
        //进入播放界面 - 视频开始加载
        Log.e(">>>>>", "demo videoDidStartLoad");
    }

    @Override
    public void videoDidFinishLoad(boolean isFinished) {
        //进入播放界面 - 视频加载完成
        Log.e(">>>>>", "demo videoDidFinishLoad");
    }

    @Override
    public void videoDidLoadError(String error) {
        //进入播放界面 - 视频加载失败
        Log.e(">>>>>", "demo videoDidLoadError " + error);
        show_error();
    }

    @Override
    public void videoDidClosed() {
        //退出整个播放界面，返回本应用
        Log.e(">>>>>", "demo videoDidClosed");
    }

    @Override
    public void videoCompletePlay() {
        //进入播放界面 - 视频播放完成，或手动关闭（视为用户完成了任务，可以获取奖励）
        Log.e(">>>>>", "demo videoCompletePlay");
        //在此可给用户奖励
        Toast.makeText(this, "恭喜你完成任务", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void videoPlayError(String error) {
        //进入播放界面 - 播放过程中出错
        Log.e(">>>>>", "demo videoPlayError");
    }

    @Override
    public void videoWillPresent() {
        //进入播放界面 - 视频开始播放
        Log.e(">>>>>", "demo videoWillPresent");
    }

    @Override
    public void videoVailable(IndependentVideoAvailableState availableState) {
        switch (availableState) {
            case VideoStateDownloading:
                Log.e(">>>>>", "demo VideoStateDownloading");
                show_caheing();
                break;
            case VideoStateFinishedCache:
                Log.e(">>>>>", "demo VideoStateFinishedCache");
                show_has_cache();
                break;
            case VideoStateNoExist:
                Log.e(">>>>>", "demo VideoStateNoExist");
                show_no_cache();
                break;
        }

    }

    /*****************************************************************************/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除监听
        IndependentVideoManager.newInstance().removeIndependentVideoListener(this);
    }

    /**
     * 直接播放视频
     *
     * @param view
     */
    public void play_video(View view) {
        //直接播放视频
        IndependentVideoManager.newInstance().presentIndependentVideo(this);
    }

    /**
     * 检查可用缓存
     *
     * @param view
     */
    public void check(View view) {
        IndependentVideoManager.newInstance().checkVideoAvailable(this);
    }

    /**
     * 播放可用缓存
     *
     * @param view
     */
    public void play_cache(View view) {
        IndependentVideoManager.newInstance().presentIndependentVideo(this);
    }

    public void show_caheing() {
        bt_play_cache.setVisibility(View.GONE);
        tv_status.setBackgroundColor(Color.parseColor("#ff0000"));
        tv_status.setText("缓存中");
    }

    public void show_error() {
        bt_play_cache.setVisibility(View.GONE);
        tv_status.setBackgroundColor(Color.parseColor("#ff0000"));
        tv_status.setText("错误");
    }

    public void show_no_cache() {
        bt_play_cache.setVisibility(View.GONE);
        tv_status.setBackgroundColor(Color.parseColor("#ff0000"));
        tv_status.setText("无缓存");
    }

    public void show_has_cache() {
        bt_play_cache.setVisibility(View.VISIBLE);
        tv_status.setBackgroundColor(Color.parseColor("#0000ff"));
        tv_status.setText("有缓存");
    }
}
