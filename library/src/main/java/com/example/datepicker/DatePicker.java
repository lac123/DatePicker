package com.example.datepicker;

import android.app.AlertDialog;
import android.content.Context;
import android.media.SoundPool;
import android.view.View;
import android.widget.TextView;

import com.example.datepicker.wheelview.WheelView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 时间选择控件
 *
 * @author luzhichao
 * @version 2.3.0, 2017/9/4
 * @since [Sherlock/V2.1.2]
 */

public class DatePicker extends AlertDialog implements View.OnClickListener {
    public static final String TAG = DatePicker.class.getSimpleName();
    private Context context;

    private int curYear = Calendar.getInstance().get(Calendar.YEAR);
    private int curMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    private WheelView firstYearWheelView;

    private WheelView firstMonthWheelView;

    private WheelView firstDayWheelView;

    private WheelView secondYearWheelView;

    private WheelView secondMonthWheelView;

    private WheelView secondDayWheelView;

    public static final int NORMAL_TYPE = 0;
    public static final int CARD_EXPIRY_TYPE = 1;

    private String defaultDate;

    //根据不同的月份和年份设置一个随不同情况变化而变化的成员变量 mFirstDayCount
    private int mFirstDayCount;
    private int mSecondDayCount;

    //当前默认在哪一天
    private int mFirstDefaultDayIndex;

    private int mSecondDefaultDayIndex;

    private SoundPool soundPool;
    private int soundId;

    private OnSelectDateListener callback;

    private int mPickerType;

    //是否为时间区间的格式
    private boolean isDoubleDate;

    //构造函数
    private DatePicker(Context context, Builder builder) {
        super(context, R.style.AlertDialog);
        this.context = context;

        show();

        setPickerType(builder.type);
        setDoubleDate(builder.isDoubleDate);
        setDefaultDate(builder.defaultDate);
        setEnsureCallback(builder.callback);

        if (builder.isDoubleDate) {
            setContentView(R.layout.alert_dialog_double_date_picker);
        } else {
            setContentView(R.layout.alert_dialog_single_date_picker);
        }

        //初始化组件
        initUIWidgets();
    }

    /**
     * 初始化UI控件
     */
    private void initUIWidgets() {

        firstYearWheelView = (WheelView) findViewById(R.id.dialog_choose_year);
        firstMonthWheelView = (WheelView) findViewById(R.id.dialog_choose_month);
        firstDayWheelView = (WheelView) findViewById(R.id.dialog_choose_day);

        TextView longTerm = (TextView) findViewById(R.id.tv_long_term);
        TextView dialogTitle = (TextView) findViewById(R.id.dialog_title);

        if (isDoubleDate) {
            secondYearWheelView = (WheelView) findViewById(R.id.second_dialog_choose_year);
            secondMonthWheelView = (WheelView) findViewById(R.id.second_dialog_choose_month);
            secondDayWheelView = (WheelView) findViewById(R.id.second_dialog_choose_day);
        }

        //click listener
        longTerm.setOnClickListener(this);
        findViewById(R.id.ensure_text_view).setOnClickListener(this);
        findViewById(R.id.cancel_text_view).setOnClickListener(this);

        if (mPickerType == CARD_EXPIRY_TYPE) {
            longTerm.setVisibility(View.VISIBLE);
            dialogTitle.setText(String.format("%s", "请选择身份证有效期"));
        } else {
            dialogTitle.setText(String.format("%s", "请选择日期"));
        }

        //为选择器初始化界面数据
        initViews();
    }

    /**
     * 为选择器初始化界面
     */
    private void initViews() {
        soundPool = new SoundPool.Builder().build();
        soundId = soundPool.load(context, R.raw.tock, 1);


        firstYearWheelView.setData(getYearData());
        firstMonthWheelView.setData(getMonthData());

        if (isDoubleDate) {
            secondYearWheelView.setData(getYearData());
            secondMonthWheelView.setData(getMonthData());
        }

        if (isDoubleDate) {
            String firstDefaultDate = null;
            String secondDefaultDate = null;

            //捕获异常,预防传入的默认值是空的
            try {
                firstDefaultDate = defaultDate.split("-")[0];
                secondDefaultDate = defaultDate.split("-")[1];
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                setDefaultDate(firstDefaultDate, firstYearWheelView, firstMonthWheelView, firstDayWheelView);
                setDefaultDate(secondDefaultDate, secondYearWheelView, secondMonthWheelView, secondDayWheelView);
            }

        } else {
            setDefaultDate(defaultDate, firstYearWheelView, firstMonthWheelView, firstDayWheelView);
        }

        firstYearWheelView.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                changeItem(firstYearWheelView, firstDayWheelView, id);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });
        firstMonthWheelView.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                changeItem(firstMonthWheelView, firstDayWheelView, id);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });
        firstDayWheelView.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                changeItem(firstDayWheelView, firstDayWheelView, id);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        if (isDoubleDate) {
            secondYearWheelView.setOnSelectListener(new WheelView.OnSelectListener() {
                @Override
                public void endSelect(int id, String text) {
                    changeItem(secondYearWheelView, secondDayWheelView, id);
                }

                @Override
                public void selecting(int id, String text) {

                }
            });

            secondMonthWheelView.setOnSelectListener(new WheelView.OnSelectListener() {
                @Override
                public void endSelect(int id, String text) {
                    changeItem(secondMonthWheelView, secondDayWheelView, id);
                }

                @Override
                public void selecting(int id, String text) {

                }
            });

            secondDayWheelView.setOnSelectListener(new WheelView.OnSelectListener() {
                @Override
                public void endSelect(int id, String text) {
                    changeItem(secondDayWheelView, secondDayWheelView, id);
                }

                @Override
                public void selecting(int id, String text) {

                }
            });
        }
    }

    /**
     * 设置默认的日期
     *
     * @param defaultDate
     * @param yearWV
     * @param monthWV
     * @param dayWV
     */
    private void setDefaultDate(String defaultDate, WheelView yearWV, WheelView monthWV, WheelView dayWV) {
        try {
            int incomeYear;
            int incomeMonth;
            int incomeDay;

            if (isDoubleDate) {
                String[] numList = defaultDate.split("/");
                incomeYear = Integer.valueOf(numList[0]);
                incomeMonth = Integer.valueOf(numList[1]);
                incomeDay = Integer.valueOf(numList[2]);
            } else {
                String[] numList = defaultDate.split("-");
                incomeYear = Integer.valueOf(numList[0]);
                incomeMonth = Integer.valueOf(numList[1]);
                incomeDay = Integer.valueOf(numList[2]);
            }

            int yearDifference = incomeYear - curYear;

            if (yearDifference != 0) {
                yearWV.setDefault((curYear - 1900) + yearDifference);
            } else {
                yearWV.setDefault(curYear - 1900);
            }

            monthWV.setDefault(incomeMonth - 1);
            initDayCount(yearWV, monthWV, dayWV);
            if (yearWV == firstYearWheelView) {
                mFirstDefaultDayIndex = incomeDay - 1;
            } else {
                mSecondDefaultDayIndex = incomeDay - 1;
            }
            dayWV.setDefault(incomeDay - 1);
        } catch (Exception e) {
            e.printStackTrace();
            yearWV.setDefault(curYear - 1900);

            monthWV.setDefault(curMonth);
            initDayCount(yearWV, monthWV, dayWV);
            if (yearWV == firstYearWheelView) {
                mFirstDefaultDayIndex = curDay - 1;
            } else {
                mSecondDefaultDayIndex = curDay - 1;
            }
            dayWV.setDefault(curDay - 1);
        }
    }

    /**
     * 初始化日期的天数
     */
    private void initDayCount(WheelView yearWV, WheelView monthWV, WheelView dayWV) {
        if (Integer.valueOf(monthWV.getSelectedText()) == 2) {
            if (isLeapYear(Integer.valueOf(yearWV.getSelectedText()))) {
                if (yearWV == firstYearWheelView) {
                    mFirstDayCount = 29;
                } else {
                    mSecondDayCount = 29;
                }
                refreshDay(dayWV);
            } else {
                //如果是闰年
                if (yearWV == firstYearWheelView) {
                    mFirstDayCount = 28;
                } else {
                    mSecondDayCount = 28;
                }
                refreshDay(dayWV);
            }
        } else {
            if (isSmallMonth(Integer.valueOf(monthWV.getSelectedText()))) {
                if (yearWV == firstYearWheelView) {
                    mFirstDayCount = 30;
                } else {
                    mSecondDayCount = 30;
                }
                refreshDay(dayWV);
            } else {
                if (yearWV == firstYearWheelView) {
                    mFirstDayCount = 31;
                } else {
                    mSecondDayCount = 31;
                }
                refreshDay(dayWV);
            }
        }
    }

    /**
     * 当滑动时动态配置日期选择器中的数据
     *
     * @param wheelView
     * @param newValue
     */
    private void changeItem(WheelView wheelView, WheelView dayWV, int newValue) {
        playSound();
        int newMonth = newValue + 1;
        if (wheelView == firstMonthWheelView) {
            if (newMonth == 2 && isLeapYear(Integer.valueOf(firstYearWheelView.getSelectedText()))) {
                mFirstDayCount = 29;
                refreshDay(dayWV);

            } else if (newMonth == 2 && !isLeapYear(Integer.valueOf(firstYearWheelView.getSelectedText()))) {
                mFirstDayCount = 28;
                refreshDay(dayWV);
            }
            if (newMonth != 2 && isSmallMonth(Integer.valueOf(firstMonthWheelView.getSelectedText()))) {
                mFirstDayCount = 30;
                refreshDay(dayWV);
            } else if (newMonth != 2 && !isSmallMonth(Integer.valueOf(firstMonthWheelView.getSelectedText()))) {
                mFirstDayCount = 31;
                refreshDay(dayWV);
            }
        } else if (wheelView == firstYearWheelView) {
            if (isLeapYear(Integer.valueOf(firstYearWheelView.getSelectedText()))) {
                if (Integer.valueOf(firstMonthWheelView.getSelectedText()) == 2) {
                    mFirstDayCount = 29;
                    refreshDay(dayWV);
                } else if (isSmallMonth(Integer.valueOf(firstMonthWheelView.getSelectedText()))) {
                    mFirstDayCount = 30;
                    refreshDay(dayWV);
                } else if (!isSmallMonth(Integer.valueOf(firstMonthWheelView.getSelectedText()))) {
                    mFirstDayCount = 31;
                    refreshDay(dayWV);
                }
            } else if (!isLeapYear(Integer.valueOf(firstYearWheelView.getSelectedText()))) {
                if (Integer.valueOf(firstMonthWheelView.getSelectedText()) == 2) {
                    mFirstDayCount = 28;
                    refreshDay(dayWV);
                } else if (isSmallMonth(Integer.valueOf(firstMonthWheelView.getSelectedText()))) {
                    mFirstDayCount = 30;
                    refreshDay(dayWV);
                } else if (!isSmallMonth(Integer.valueOf(firstMonthWheelView.getSelectedText()))) {
                    mFirstDayCount = 31;
                    refreshDay(dayWV);
                }
            }
        } else if (wheelView == firstDayWheelView) {
            mFirstDefaultDayIndex = firstDayWheelView.getSelected();
        } else if (wheelView == secondMonthWheelView) {
            if (newMonth == 2 && isLeapYear(Integer.valueOf(secondYearWheelView.getSelectedText()))) {
                mSecondDayCount = 29;
                refreshDay(dayWV);

            } else if (newMonth == 2 && !isLeapYear(Integer.valueOf(secondYearWheelView.getSelectedText()))) {
                mSecondDayCount = 28;
                refreshDay(dayWV);
            }
            if (newMonth != 2 && isSmallMonth(Integer.valueOf(secondMonthWheelView.getSelectedText()))) {
                mSecondDayCount = 30;
                refreshDay(dayWV);
            } else if (newMonth != 2 && !isSmallMonth(Integer.valueOf(secondMonthWheelView.getSelectedText()))) {
                mSecondDayCount = 31;
                refreshDay(dayWV);
            }
        } else if (wheelView == secondYearWheelView) {
            if (isLeapYear(Integer.valueOf(secondYearWheelView.getSelectedText()))) {
                if (Integer.valueOf(secondMonthWheelView.getSelectedText()) == 2) {
                    mSecondDayCount = 29;
                    refreshDay(dayWV);
                } else if (isSmallMonth(Integer.valueOf(secondMonthWheelView.getSelectedText()))) {
                    mSecondDayCount = 30;
                    refreshDay(dayWV);
                } else if (!isSmallMonth(Integer.valueOf(secondMonthWheelView.getSelectedText()))) {
                    mSecondDayCount = 31;
                    refreshDay(dayWV);
                }
            } else if (!isLeapYear(Integer.valueOf(secondYearWheelView.getSelectedText()))) {
                if (Integer.valueOf(secondMonthWheelView.getSelectedText()) == 2) {
                    mSecondDayCount = 28;
                    refreshDay(dayWV);
                } else if (isSmallMonth(Integer.valueOf(secondMonthWheelView.getSelectedText()))) {
                    mSecondDayCount = 30;
                    refreshDay(dayWV);
                } else if (!isSmallMonth(Integer.valueOf(secondMonthWheelView.getSelectedText()))) {
                    mSecondDayCount = 31;
                    refreshDay(dayWV);
                }
            }
        } else if (wheelView == secondDayWheelView) {
            mSecondDefaultDayIndex = secondDayWheelView.getSelected();
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
    private void refreshDay(WheelView dayWV) {
        if (dayWV == firstDayWheelView) {
            firstDayWheelView.refreshData(getDayData(mFirstDayCount));
            if (mFirstDefaultDayIndex <= mFirstDayCount - 1) {
                firstDayWheelView.setDefault(mFirstDefaultDayIndex);
            } else {
                firstDayWheelView.setDefault(mFirstDayCount - 1);
                mFirstDefaultDayIndex = mFirstDayCount - 1;
            }
        } else {
            secondDayWheelView.refreshData(getDayData(mSecondDayCount));
            if (mSecondDefaultDayIndex <= mSecondDayCount - 1) {
                secondDayWheelView.setDefault(mSecondDefaultDayIndex);
            } else {
                secondDayWheelView.setDefault(mSecondDayCount - 1);
                mSecondDefaultDayIndex = mSecondDayCount - 1;
            }
        }
    }

    /**
     * 设置选择器类型
     *
     * @param pickerType
     */
    private void setPickerType(int pickerType) {
        mPickerType = pickerType;
    }

    /**
     * 设置是否是选择日期区间
     *
     * @param doubleDate
     */
    private void setDoubleDate(boolean doubleDate) {
        isDoubleDate = doubleDate;
    }

    /**
     * 设置默认的日期
     *
     * @param defaultDate
     */
    private void setDefaultDate(String defaultDate) {
        this.defaultDate = defaultDate;
    }

    /**
     * 传入确认选择的回调
     *
     * @param callback
     */
    private void setEnsureCallback(OnSelectDateListener callback) {
        this.callback = callback;
    }

    /**
     * 配置月
     *
     * @return
     */
    private ArrayList<String> getMonthData() {
        ArrayList<String> monthData = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            monthData.add(String.valueOf(i));
        }
        return monthData;
    }

    /**
     * 配置年
     *
     * @return
     */
    private ArrayList<String> getYearData() {
        ArrayList<String> yearData = new ArrayList<>();
        for (int i = 1900; i < 2100; i++) {
            yearData.add(String.valueOf(i));
        }
        return yearData;
    }

    /**
     * 配置日
     *
     * @param dayCount
     * @return
     */
    private ArrayList<String> getDayData(int dayCount) {
        ArrayList<String> dayData = new ArrayList<>();
        for (int j = 1; j < dayCount + 1; j++) {
            dayData.add(String.valueOf(j));
        }
        return dayData;
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

    @Override
    public void onClick(View view) {
        int which = view.getId();
        if (which == R.id.cancel_text_view) {
            cancel();
        } else if (which == R.id.ensure_text_view) {
            String selectedDateString;
            if (isDoubleDate) {
                selectedDateString = String.format("%s%s%s%s%s", firstYearWheelView.getSelectedText()
                        , "/", firstMonthWheelView.getSelectedText(), "/", firstDayWheelView.getSelectedText() + "-" +
                                String.format("%s%s%s%s%s", secondYearWheelView.getSelectedText()
                                        , "/", secondMonthWheelView.getSelectedText(), "/", secondDayWheelView.getSelectedText()))
                ;
            } else {
                selectedDateString = String.format("%s%s%s%s%s", firstYearWheelView.getSelectedText()
                        , "-", firstMonthWheelView.getSelectedText(), "-", firstDayWheelView.getSelectedText());
            }
            callback.onDateSelected(selectedDateString);
            dismiss();
        } else {
            callback.onDateSelected(String.format("%s", "长期"));
            dismiss();
        }
    }

    public static final class Builder {
        private Context context;
        private int type;
        private String defaultDate;
        private OnSelectDateListener callback;
        private boolean isDoubleDate = false;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setType(int val) {
            type = val;
            return this;
        }

        public Builder isDoubleDate(boolean val) {
            isDoubleDate = val;
            return this;
        }

        public Builder setDefaultDate(String val) {
            defaultDate = val;
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
