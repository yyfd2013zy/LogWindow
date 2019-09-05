package logwindow.vinda.com.logwindowlib.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import logwindow.vinda.com.logwindowlib.L;
import logwindow.vinda.com.logwindowlib.R;

/**
 * 全局悬浮窗，显示调试信息
 */
public class WindowUtils implements View.OnClickListener {
    private static final String LOG_TAG = "WindowUtils";
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static Context mContext = null;
    public static Boolean isShown = false;
    public LinearLayout ll;
    private ScrollView sv;
    private Button btn_hide_show, btn_clear_log, et_tag, et_clear_tag;
    private TextView tv_net_speed;
    private NetWorkSpeedUtils netWorkSpeedUtils;
    private Switch btn_aotu_scroll;
    private boolean autoScroll = true;//是否自动滑动到地步
    private ArrayList<String> tags = new ArrayList<>();
    private String filterLog = "";//过滤标签
    private int HANDLER_SHOW_INFO = 10;
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if (msg.what == HANDLER_SHOW_INFO) {
                Bundle bundle = (Bundle) msg.obj;
                String log_tag = bundle.getString("log_tag");
                String log_msg = bundle.getString("log_msg");
                if (ll != null) {
                    if (!tags.contains(log_tag)) {
                        tags.add(log_tag);
                    }
                    if (!TextUtils.isEmpty(filterLog)) {
                        if (log_tag.equals(filterLog)) {
                            showLog(log_tag, log_msg);
                        }
                    } else {
                        showLog(log_tag, log_msg);
                    }
                }
            }
        }
    };

    /**
     * 显示弹出框
     *
     * @param context
     */
    public void showPopupWindow(final Context context) {
        if (isShown) {
            L.i(LOG_TAG, "return cause already shown");
            return;
        }
        isShown = true;
        L.i(LOG_TAG, "showPopupWindow");
        // 获取应用的Context
        mContext = context.getApplicationContext();
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        mView = setUpView(context);
        final LayoutParams params = new LayoutParams();
        // 窗口类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = LayoutParams.TYPE_SYSTEM_ALERT;
        }

        params.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        mWindowManager.addView(mView, params);
        L.i(LOG_TAG, "add view");
    }

    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        L.i(LOG_TAG, "hide " + isShown + ", " + mView);
        if (isShown && null != mView) {
            L.i(LOG_TAG, "hidePopupWindow");
            mWindowManager.removeView(mView);
            isShown = false;
        }
    }

    private View setUpView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.log_pop_window,
                null);
        // 隐藏弹窗
        //WindowUtils.hidePopupWindow();
        // 点击窗口外部区域可消除
        // 这点的实现主要将悬浮窗设置为全屏大小，外层有个透明背景，中间一部分视为内容区域
        // 所以点击内容区域外部视为点击悬浮窗外部
        ll = view.findViewById(R.id.ll);// 非透明的内容区域
        sv = view.findViewById(R.id.sv);
        tv_net_speed = view.findViewById(R.id.tv_net_speed);
        netWorkSpeedUtils = new NetWorkSpeedUtils(context, mHnadler);
        netWorkSpeedUtils.startShowNetSpeed();
        btn_hide_show = view.findViewById(R.id.btn_hide_show);
        btn_hide_show.setOnClickListener(this);
        // 点击back键可消除
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        WindowUtils.hidePopupWindow();
                        return true;
                    default:
                        return false;
                }
            }
        });
        btn_aotu_scroll = view.findViewById(R.id.btn_aotu_scroll);
        btn_aotu_scroll.setChecked(true);
        btn_aotu_scroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autoScroll = isChecked;
            }
        });
        et_tag = view.findViewById(R.id.et_tag);
        et_tag.setOnClickListener(this);
        et_clear_tag = view.findViewById(R.id.et_clear_tag);
        et_clear_tag.setOnClickListener(this);
        btn_clear_log = view.findViewById(R.id.btn_clear_log);
        btn_clear_log.setOnClickListener(this);
        return view;
    }

    public void showInfo(String TAG, String msg) {
        Message message = new Message();
        message.what = HANDLER_SHOW_INFO;
        Bundle bundle = new Bundle();
        bundle.putString("log_tag", TAG);
        bundle.putString("log_msg", msg);
        message.obj = bundle;
        handler.sendMessage(message);
    }

    private void showLog(String TAG, String msg) {
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date currentDate = new Date();
        String displayTime1 = format1.format(currentDate);
        TextView textView = new TextView(mContext);
        textView.setTextColor(mContext.getResources().getColor(R.color.green_ying_guang));
        textView.setText(displayTime1 + "--->" + TAG + "===" + msg);
        textView.setTextSize(14);
        ll.addView(textView);
        if (autoScroll)//自动滚他妈的
            sv.fullScroll(ScrollView.FOCUS_DOWN);
    }

    private Handler mHnadler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    tv_net_speed.setText("当前网速： " + msg.obj.toString());
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void release() {
        WindowUtils.hidePopupWindow();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_hide_show) {
            WindowUtils.hidePopupWindow();

        } else if (i == R.id.btn_clear_log) {
            ll.removeAllViews();

        } else if (i == R.id.et_tag) {
            for (final String t : tags) {
                if (!TextUtils.isEmpty(t)) {
                    Button button = new Button(mContext);
                    button.setTextColor(mContext.getResources().getColor(R.color.black));
                    button.setText(t);
                    ll.addView(button);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            filterLog = t;
                        }
                    });
                }
            }

        } else if (i == R.id.et_clear_tag) {
            filterLog = "";

        }
    }

//   view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int x = (int) event.getX();
//                int y = (int) event.getY();
//                Rect rect = new Rect();
//                popupWindowView.getGlobalVisibleRect(rect);
//                if (!rect.contains(x, y)) {
//                    WindowUtils.hidePopupWindow();
//                }
//                return false;
//            }
//        });
}
