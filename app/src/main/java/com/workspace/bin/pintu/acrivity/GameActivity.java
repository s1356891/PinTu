package com.workspace.bin.pintu.acrivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.workspace.bin.pintu.R;
import com.workspace.bin.pintu.Util.GameUtil;
import com.workspace.bin.pintu.Util.ImagesUtil;
import com.workspace.bin.pintu.Util.ScreenUtil;
import com.workspace.bin.pintu.adapter.GameAdapter;
import com.workspace.bin.pintu.adapter.GridAdapter;
import com.workspace.bin.pintu.bean.ItemBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    public static Bitmap mLastBitmap;
    private RelativeLayout frameLayout;
    private List<Bitmap> mBitmapItemLists = new ArrayList<>();
    private GridView gridView;
    private GameAdapter adapter;
    public static int TYPE=2;
    public static int COUNT_INDEX = 0;
    public static int TIMER_INDEX = 0;
    private Button button1,button2, button3;
    private TextView tv_step, tv_time;
    private Bitmap mPicSelected;
    private boolean mIsShowImg;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    TIMER_INDEX++;
                    tv_time.setText("" + TIMER_INDEX);
                    break;
                case 2:
                    COUNT_INDEX++;
                    tv_step.setText(""+COUNT_INDEX);
                    break;
            }
        }
    };
    private int mResId;
    private String mPicPath;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private ImageView mImageView;
    private Bitmap picSelectedTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
        initData();
        initAction();
    }

    private void handlerImage(Bitmap picSelectedTemp) {
        int screenWidth = ScreenUtil.getScreenSize(this).widthPixels;
        int screenHeight = ScreenUtil.getScreenSize(this).heightPixels;
        mPicSelected = new ImagesUtil().resizeBitmap(screenWidth, (float) (screenHeight*0.6), picSelectedTemp);
    }

    private void initAction() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (GameUtil.isMoveable(position)) {
                    GameUtil.swapItems(GameUtil.mItemBeans.get(position), GameUtil.mBlankItemBean);
                    recreateData();
                    adapter.notifyDataSetChanged();
                    handler.sendEmptyMessage(2);
                    if (GameUtil.isSuccess()) {
                        recreateData();
                        mBitmapItemLists.remove(TYPE * TYPE - 1);
                        mBitmapItemLists.add(mLastBitmap);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(GameActivity.this, "拼图成功", Toast.LENGTH_LONG).show();
                        mTimer.cancel();
                        mTimerTask.cancel();
                        gridView.setEnabled(false);
                    }
                }

            }
        });

    }

    private void initData() {
        mResId = getIntent().getExtras().getInt("picSelectedID");
        mPicPath = getIntent().getExtras().getString("mPicPath");
        if (mResId != 0) {
            picSelectedTemp = BitmapFactory.decodeResource(getResources(), mResId);
        } else {
            picSelectedTemp = BitmapFactory.decodeFile(mPicPath);
            Log.d("111", mPicPath);
        }
        TYPE = getIntent().getExtras().getInt("mType", 2);
        generateGame();
        addImageView();
    }

    private void initView() {
        button1 = (Button) findViewById(R.id.btn_original);
        button2 = (Button) findViewById(R.id.btn_reset);
        button3 = (Button) findViewById(R.id.btn_back);
        tv_step = (TextView) findViewById(R.id.tv_step);
        tv_time = (TextView) findViewById(R.id.tv_time);
        gridView = (GridView) findViewById(R.id.gv_game);
        frameLayout = (RelativeLayout) findViewById(R.id.game_container);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_original:
                if (mIsShowImg) {
                    mImageView.setVisibility(View.GONE);
                } else {
                    mImageView.setVisibility(View.VISIBLE);
                }
                mIsShowImg = !mIsShowImg;
                break;
            case R.id.btn_reset:
                clearConfig();
                generateGame();
                break;
            case R.id.btn_back:
                GameActivity.this.finish();
                break;
        }
    }

    /*
  * 生成游戏
  * */
    private void generateGame() {
        mBitmapItemLists.clear();
        tv_time.setText("" + TIMER_INDEX);
        tv_step.setText(""+COUNT_INDEX);
        gridView.setNumColumns(TYPE);
        handlerImage(picSelectedTemp);
        ImagesUtil.createInitBitmaps(TYPE,mPicSelected,this);
        GameUtil.getPuzzleGenerator();
        for (ItemBean itembean:GameUtil.mItemBeans) {
            mBitmapItemLists.add(itembean.getBitmap());
        }
        Log.d("111", "size" + mBitmapItemLists.size());
        adapter = new GameAdapter(mBitmapItemLists, this);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mTimer = new Timer(true);
        mTimerTask = new TimerTask(){
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
        mTimer.schedule(mTimerTask,0,1000);

    }

    @Override
    protected void onStop() {
        super.onStop();
        clearConfig();
        this.finish();
    }
    /*
    * 清除设置
    * */
    private void clearConfig() {
        GameUtil.mItemBeans.clear();
        mTimer.cancel();
        mTimerTask.cancel();
        TIMER_INDEX = 0;
        COUNT_INDEX = 0;
        /*
        * 删除照片
        * */
        if (mPicPath != null) {
            File file = new File(MainActivity.TEMP_IMAGE_PATH);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /*
    * 重新获取图片
    * */

    private void recreateData() {
        mBitmapItemLists.clear();
        for (ItemBean item:GameUtil.mItemBeans) {
            mBitmapItemLists.add(item.getBitmap());
        }
    }


    /*
    * 显示原图
    * */
    private void addImageView() {
//        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_game);
        mImageView = new ImageView(this);
        mImageView.setImageBitmap(mPicSelected);
        int x = mPicSelected.getWidth();
        int y = mPicSelected.getHeight();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(x, y);
//        mImageView.setLayoutParams(gridView.getLayoutParams());
        params.resolveLayoutDirection(RelativeLayout.ALIGN_PARENT_TOP);
        mImageView.setLayoutParams(params);
        frameLayout.addView(mImageView);
        mImageView.setVisibility(View.INVISIBLE);
    }

}
