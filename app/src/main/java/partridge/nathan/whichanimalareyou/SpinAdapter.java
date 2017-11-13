package partridge.nathan.whichanimalareyou;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import partridge.nathan.whichanimalareyou.R;

public class SpinAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context mContext;
    private List<String> mItems;


    public SpinAdapter(Context context, List<String> items) {
        mContext = context;
        mItems = items;
    }

    public void setItems(List<String> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (mItems == null) ? 0 : mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return (mItems == null) ? "" : mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.spinner_question, null);
        }

        ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dropdown_ic_arrow_pressed_holo_dark, 0);
        ((TextView) view).setText((String) getItem(i));

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_question, null);
        }
        ((TextView) convertView).setText((String) getItem(position));
        return convertView;
    }
}
