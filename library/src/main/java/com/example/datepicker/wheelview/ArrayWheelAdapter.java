package com.example.datepicker.wheelview;

import android.content.Context;

import com.example.datepicker.R;


/**
 * The simple Array wheel adapter
 *
 * @param <T> the element type
 */
public class ArrayWheelAdapter<T> extends AbstractWheelTextAdapter {

    // items
    private T items[];

    /**
     * Constructor
     *
     * @param context the current context
     * @param items   the items
     */
    public ArrayWheelAdapter(Context context, T items[]) {
        super(context);

        setItemResource(R.layout.dialog_item_dynamic_type);
        setItemTextResource(R.id.dialog_item_dynamic_typetextview);
        this.items = items;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.length) {
            T item = items[index];
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.length;
    }
}
