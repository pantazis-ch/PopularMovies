package com.example.android.popularmovies.utilities;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ExpListUtility {


    /**
     * This method calculates the height that is needed for an ExpandableListView to show all its content
     * when a group is expanded.
     * We use it because we have an ExpandableListView inside a ScrollView.
     *
     * This method was taken from 'Stack Overflow'.
     *
     * @param listView An ExpandableListView.
     * @param group The group that we need to measure.
     */
    public static void setListViewHeight(ExpandableListView listView, int group) {

        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group)) || ((!listView.isGroupExpanded(i)) && (i == group)))
            {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++)
                {
                    View listItem = listAdapter.getChildView(i, j, false, null,listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        int height = totalHeight + (listView.getDividerHeight() * (listAdapter.getChildrenCount(0) - 1));

        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
