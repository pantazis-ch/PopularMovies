package com.example.android.popularmovies.detailactivity.reviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.android.popularmovies.R;

import java.util.ArrayList;


public class ReviewExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<Review> reviews = null;

    public ReviewExpListAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int i) {
        if(reviews != null) {
            return reviews.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int i) {
        return reviews;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return reviews.get(childPosititon);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        String headerTitle = "Movie Reviews";

        if (reviews.size() == 0) {
            headerTitle = "No Reviews";
        }

        if (convertView == null) {
            Context context = viewGroup.getContext();

            int layoutIdForListItem = R.layout.review_group_item;

            LayoutInflater inflater = LayoutInflater.from(context);

            boolean shouldAttachToParentImmediately = false;

            convertView = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.review_list_header);
        txt.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup viewGroup) {

        Review review = reviews.get(childPosition);

        String reviewAuthor = review.getAuthor();

        String reviewContent = review.getContent();

        if (convertView == null) {
            Context context = viewGroup.getContext();

            int layoutIdForListItem = R.layout.review_child_item;

            LayoutInflater inflater = LayoutInflater.from(context);

            boolean shouldAttachToParentImmediately = false;

            convertView = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        }

        TextView name = (TextView) convertView.findViewById(R.id.review_author);
        name.setText("by " + reviewAuthor);

        TextView type = (TextView) convertView.findViewById(R.id.review_content);
        type.setText(reviewContent);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
