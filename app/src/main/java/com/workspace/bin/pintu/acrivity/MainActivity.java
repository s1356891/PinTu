package com.workspace.bin.pintu.acrivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.workspace.bin.pintu.R;
import com.workspace.bin.pintu.Util.ScreenUtil;
import com.workspace.bin.pintu.adapter.GridAdapter;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_level;
    private GridView gridView;
    private LinearLayout title_container;
    private RelativeLayout relativeLayout;
    private GridAdapter adapter;
    private int[] datas = new int[]{R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5, R.drawable.image_6, R.drawable.image_7, R.drawable.image_8, R.drawable.image_9, R.drawable.image_10, R.drawable.image_11, R.drawable.image_12, R.drawable.image_13, R.drawable.image_14, R.drawable.a16};
    private PopupWindow mPopupWindow;
    private View view;
    private Button button1, button2, button3;
    private static final int RESULT_IMAGE = 100;
    private static final String IMAGE_TYPE = "image/*";
    private static final int RESULT_CAMERA = 100;
    public static String TEMP_IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/temp.png";
    private int mType=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        title_container = (LinearLayout) findViewById(R.id.title_container);
        tv_level = (TextView) findViewById(R.id.tv_level);
        gridView = (GridView) findViewById(R.id.gv);
        adapter = new GridAdapter(datas, this);
        gridView.setAdapter(adapter);
        title_container.setOnClickListener(this);
        view = getLayoutInflater().inflate(R.layout.popup_window, null);
        button1 = (Button) view.findViewById(R.id.btn_level1);
        button2 = (Button) view.findViewById(R.id.btn_level2);
        button3 = (Button) view.findViewById(R.id.btn_level3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == datas.length - 1) {
                    showDialog();
                } else {
                    Intent intent = new Intent(MainActivity.this,GameActivity.class);
                    intent.putExtra("picSelectedID", datas[position]);
                    intent.putExtra("mType", mType);
                    startActivity(intent);
                }
            }


        });
    }

    private void showDialog() {
        String[] items = {"图库", "相机"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_TYPE);
                    startActivityForResult(intent, RESULT_IMAGE);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri photoUri = Uri.fromFile(new File(TEMP_IMAGE_PATH));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent, RESULT_CAMERA);
                }
            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_container:
                showPopupWindow(relativeLayout);
                break;
            case R.id.btn_level1:
                tv_level.setText("2 X 2");
                mPopupWindow.dismiss();
                mType = 2;
                break;
            case R.id.btn_level2:
                tv_level.setText("3 X 3");
                mPopupWindow.dismiss();
                mType = 3;
                break;
            case R.id.btn_level3:
                tv_level.setText("4 X 4");
                mPopupWindow.dismiss();
                mType = 4;
                break;
        }

    }

    /*
    * 显示popupWindow
    * */
    private void showPopupWindow(View view) {
        int density = (int) ScreenUtil.getDeviceDisplay(this);
        mPopupWindow = new PopupWindow(this.view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        Drawable transparent = new ColorDrawable(0);
        mPopupWindow.setBackgroundDrawable(transparent);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(view, Gravity.CENTER, location[0] - 40 * density, location[1] + 30 * density);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("mType", mType);
            if (requestCode == RESULT_IMAGE&&data!=null) {
                Cursor cursor = this.getContentResolver().query(data.getData(), null, null, null, null);
                cursor.moveToFirst();
                String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                intent.putExtra("mPicPath", imagePath);
                cursor.close();
            } else if (requestCode == RESULT_CAMERA) {
                intent.putExtra("mPicPath", TEMP_IMAGE_PATH);
            }
            startActivity(intent);
        }

    }
}
