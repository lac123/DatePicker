package com.example.datepicker;

import android.app.AlertDialog;
import android.content.Context;
import android.media.SoundPool;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.datepicker.util.DateUtil;
import com.example.datepicker.wheelview.ArrayWheelAdapter;
import com.example.datepicker.wheelview.OnWheelChangedListener;
import com.example.datepicker.wheelview.WheelView;

import java.text.ParseException;
import java.util.Calendar;

/**
 * 自定义时间滚动选择器
 *
 * @author luzhichao
 * @version 1.0,  2016-10-18 17:02
 * @since [sherlock/V1.0.0]
 */

public class DatePicker extends AlertDialog implements View.OnClickListener {
    public static final String TAG = DatePicker.class.getSimpleName();
    private static final int TOTAL_YEAR_AMOUNT = 200;
    private Context context;

    private WheelView yearWheelView;

    private WheelView monthWheelView;

    private WheelView dayWheelView;

    private LinearLayout longTerm;

    private TextView dialogTitle;

    private final int TOTAL_MONTH_AMOUNT = 12;
    public static final int CARD_EXPIRY_TYPE = 1;
    public static final int NORMAL_TYPE = 2;
    private OnSelectDateListener callback;
    private String date;

    //设置当前的年、月、日
    private int curYear, curMonth, curDay;
    //根据不同的月份和年份设置一个随不同情况变化而变化的成员变量 mDay
    private int mDay;
    //String[] yearParent = new String[TOTAL_YEAR_AMOUNT];
    private String[] yearParent;
    private String[] monthParent = new String[TOTAL_MONTH_AMOUNT];
    private String[] dayParent;
    private SoundPool soundPool;
    private int soundId;

    //构造函数
    public DatePicker(Context context, int type, String date, OnSelectDateListener callback) {
        super(context, R.style.AlertDialog);
        show();
        setContentView(R.layout.alert_dialog_date_choose_by_wheelview);

        initUIWidgets();//init widgets

        this.context = context;
        this.callback = callback;
        this.date = date;

        if (type == CARD_EXPIRY_TYPE) {
            dialogTitle.setText(String.format("%s", "请选择身份证有效期"));
            yearParent = new String[TOTAL_YEAR_AMOUNT];
        } else if (type == NORMAL_TYPE) {
            yearParent = new String[TOTAL_YEAR_AMOUNT];
            longTerm.setVisibility(View.GONE);
            dialogTitle.setText(String.format("%s", "请选择日期"));
        }
        initData();
    }

    /**
     * 初始化UI控件
     */
    private void initUIWidgets() {
        yearWheelView = (WheelView) findViewById(R.id.dialog_choose_year);
        monthWheelView = (WheelView) findViewById(R.id.dialog_choose_month);
        dayWheelView = (WheelView) findViewById(R.id.dialog_choose_day);
        longTerm = (LinearLayout) findViewById(R.id.ll_choose_id_long_term);
        dialogTitle = (TextView) findViewById(R.id.dialog_title);
        longTerm.setOnClickListener(this);
        findViewById(R.id.dialog_date_choose_cancel).setOnClickListener(this);
        findViewById(R.id.dialog_date_choose_sure).setOnClickListener(this);
    }

    /**
     * Builder 的构造函数
     *
     * @param context
     * @param builder
     */
    private DatePicker(Context context, Builder builder) {
        this(context, builder.type, builder.date, builder.callback);
    }

    @Override
    public void onClick(View view) {
        int which = view.getId();
        if (which == R.id.dialog_date_choose_cancel) {
            cancel();
        } else if (which == R.id.dialog_date_choose_sure) {
            String selectedDateString = String.format("%s%s%s%s%s", yearParent[yearWheelView.getCurrentItem()]
                    , "-", monthParent[monthWheelView.getCurrentItem()], "-", dayParent[dayWheelView.getCurrentItem()]);
            callback.onDateSelected(selectedDateString);
            dismiss();
        } else {
            callback.onDateSelected(String.format("%s", "长期"));
            dismiss();
        }
    }

    /**
     * 为选择器初始化界面
     */
    private void initData() {
        soundPool = new SoundPool.Builder().build();
        soundId = soundPool.load(context, R.raw.tock, 1);

        Calendar c = Calendar.getInstance();
        curYear = c.get(Calendar.YEAR);
        curMonth = c.get(Calendar.MONTH);
        curDay = c.get(Calendar.DAY_OF_MONTH);

        int yearStart = 1900;//开始年份
        for (int i = 0; i < TOTAL_YEAR_AMOUNT; i++) {
            yearParent[i] = String.valueOf(yearStart);
            yearStart++;
        }
        int monCount = 1;
        for (int i = 0; i < TOTAL_MONTH_AMOUNT; i++) {
            monthParent[i] = String.valueOf(monCount);
            monCount++;
        }

        if (Integer.valueOf(monthParent[monthWheelView.getCurrentItem()]) == 2) {
            if (isLeapYear(Integer.valueOf(yearParent[yearWheelView.getCurrentItem()]))) {
                mDay = 29;
                dayParent = new String[mDay];
                setDays(mDay);
            } else {
                //如果是闰年
                mDay = 28;
                dayParent = new String[mDay];
                setDays(mDay);
            }
        }
        if (Integer.valueOf(monthParent[monthWheelView.getCurrentItem()]) != 2 &&
                isSmallMonth(Integer.valueOf(monthParent[monthWheelView.getCurrentItem()]))) {
            mDay = 30;
            dayParent = new String[mDay];
            setDays(mDay);
        } else if (Integer.valueOf(monthParent[monthWheelView.getCurrentItem()]) != 2 &&
                !isSmallMonth(Integer.valueOf(monthParent[monthWheelView.getCurrentItem()]))) {
            mDay = 31;
            dayParent = new String[mDay];
            setDays(mDay);
        }
        yearWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                changeItem(yearWheelView, newValue);
            }
        });
        monthWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                changeItem(monthWheelView, newValue);
            }

        });
        dayWheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                playSound();
            }
        });

        initViews();
    }

    private void initViews() {
        yearWheelView.setVisibleItems(3);
        monthWheelView.setVisibleItems(3);
        dayWheelView.setVisibleItems(3);
        yearWheelView.setViewAdapter(new ArrayWheelAdapter<>(context, yearParent));
        monthWheelView.setViewAdapter(new ArrayWheelAdapter<>(context, monthParent));
        dayWheelView.setViewAdapter(new ArrayWheelAdapter<>(context, dayParent));
        try {
            DateUtil.sdfShort.parse(date);
            int incomingYear = Integer.valueOf(date.split("-")[0]);
            int incomingMonth = Integer.valueOf(date.split("-")[1]);
            int incomingDay = Integer.valueOf(date.split("-")[2]);

            int yearDifference = incomingYear - curYear;

            if (yearDifference != 0) {
                yearWheelView.setCurrentItem((curYear - 1900) + yearDifference);
            } else {
                yearWheelView.setCurrentItem(curYear - 1900);
            }

            monthWheelView.setCurrentItem(incomingMonth - 1);
            dayWheelView.setCurrentItem(incomingDay - 1);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
            yearWheelView.setCurrentItem(curYear - 1900);

            monthWheelView.setCurrentItem(curMonth);
            dayWheelView.setCurrentItem(curDay - 1);
        }
    }

    private void changeItem(WheelView wheelView, int newValue) {
        playSound();
        int newMonth = newValue + 1;
        if (wheelView == monthWheelView) {
            yearWheelView.getCurrentItem();
            if (newMonth == 2 && isLeapYear(Integer.valueOf(yearParent[yearWheelView.getCurrentItem()]))) {
                mDay = 29;
                refreshDay();

            } else if (newMonth == 2 && !isLeapYear(Integer.valueOf(yearParent[yearWheelView.getCurrentItem()]))) {
                mDay = 28;
                refreshDay();
            }
            if (newMonth != 2 && isSmallMonth(Integer.valueOf(monthParent[newValue]))) {
                mDay = 30;
                refreshDay();
            } else if (newMonth != 2 && !isSmallMonth(Integer.valueOf(monthParent[newValue]))) {
                mDay = 31;
                refreshDay();
            }
        }
        if (wheelView == yearWheelView) {
            if (isLeapYear(Integer.valueOf(yearParent[newValue]))) {
                if (Integer.valueOf(monthParent[monthWheelView.getCurrentItem()]) == 2) {
                    mDay = 29;
                    refreshDay();
                } else if (isSmallMonth(Integer.valueOf(monthParent[monthWheelView.getCurrentItem()]))) {
                    mDay = 30;
                    refreshDay();
                } else if (!isSmallMonth(Integer.valueOf(monthParent[monthWheelView.getCurrentItem()]))) {
                    mDay = 31;
                    refreshDay();
                }
            } else if (!isLeapYear(Integer.valueOf(yearParent[newValue]))) {
                if (Integer.valueOf(monthParent[monthWheelView.getCurrentItem()]) == 2) {
                    mDay = 28;
                    refreshDay();
                } else if (isSmallMonth(Integer.valueOf(monthParent[monthWheelView.getCurrentItem()]))) {
                    mDay = 30;
                    refreshDay();
                } else if (!isSmallMonth(Integer.valueOf(monthParent[monthWheelView.getCurrentItem()]))) {
                    mDay = 31;
                    refreshDay();
                }
            }
        }
    }

    /**
     * 播放音效
     * <p>
     * 其它音效地址：http://www.2gei.com/sound/class/system-alen__1/
     */
    private void playSound() {
        soundPool.play(
                soundId,
                1f, //左耳道音量【0~1】
                1f, //右耳道音量【0~1】
                0,    //播放优先级【0表示最低优先级】
                0,    //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1     //播放速度【1是正常，范围从0~2】
        );
    }

    /**
     * 根据不同的年份或者月份的变化来刷新天数
     */
    private void refreshDay() {
        //变更月份的时候，如果是从大月切换为小月或是2月，并且日期超过了即将要切换到的月份的最大天数，将日期最后一天的位置重置
        if (dayWheelView != null && dayWheelView.getCurrentItem() + 1 > mDay) {
            dayWheelView.setCurrentItem(mDay - 1);
        }
        dayParent = new String[mDay];
        setDays(mDay);
        dayWheelView.setViewAdapter(new ArrayWheelAdapter<>(context, dayParent));
    }

    /**
     * 判断是否为润年
     *
     * @param year
     * @return
     */
    private boolean isLeapYear(int year) {
        return ((year % 100 == 0 && year % 400 == 0) || (year % 100 != 0 && year % 4 == 0));
    }

    /**
     * 判断当前选中的月份是否为小月
     *
     * @param month
     * @return
     */
    private boolean isSmallMonth(int month) {
        return (month == 4 || month == 6 || month == 9 || month == 11);
    }

    /**
     * 根据获取的day来刷新数组中的数据
     *
     * @param day
     */
    private void setDays(int day) {
        int dayCount = 1;
        for (int i = 0; i < day; i++) {
            dayParent[i] = String.valueOf(dayCount);
            dayCount++;
        }
    }

    public static final class Builder {
        private Context context;
        private int type;
        private String date;
        private OnSelectDateListener callback;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setType(int val) {
            type = val;
            return this;
        }

        public Builder setDefaultDate(String val) {
            date = val;
            return this;
        }

        public Builder setSelectedCallback(OnSelectDateListener val) {
            callback = val;
            return this;
        }

        public DatePicker showDatePicker() {
            return new DatePicker(context, this);
        }
    }

    public interface OnSelectDateListener {
        void onDateSelected(String dateString);
    }
}
