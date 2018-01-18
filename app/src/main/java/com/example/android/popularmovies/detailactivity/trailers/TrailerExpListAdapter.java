package com.example.android.popularmovies.detailactivity.trailers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.android.popularmovies.R;

import java.util.ArrayList;


public class TrailerExpListAdapter extends BaseExpandableListAdapter {


    /**
     * This interface is used to inform the TrailerFragment that a click event
     * has occurred in the child list ( List of Trailers ) of the Expandable List.
     *
     * It consists of only one method, the onClick(Trailer trailer, String action);
     *
     * The trailer is the trailer that the user has selected and the action is either 'watch'
     * or 'share' depending on the section that the user clicked.
     */
    public interface ClickHandler {
        void onClick(Trailer trailer, String action);
    }

    private ClickHandler mClickHandler;

    /**
     * An ArrayList of Trailer Objects.
     */
    private ArrayList<Trailer> trailers = null;

    public TrailerExpListAdapter(ArrayList<Trailer> trailers, ClickHandler mClickHandler) {
        this.trailers = trailers;
        this.mClickHandler = mClickHandler;
    }


    /**
     *
     * In the Trailer Expandable List we have only one Group that is called 'Movie Trailers'
     * or 'No Trailers' if the specific movie has no Trailers.
     *
     * @return 1.
     */
    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int i) {
        if(trailers != null) {
            return trailers.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int i) {
        return trailers;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return trailers.get(childPosititon);
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

        String headerTitle = "Movie Trailers";

        if (trailers.size() == 0) {
            headerTitle = "No Trailers";
        }

        if (convertView == null) {
            Context context = viewGroup.getContext();

            int layoutIdForListItem = R.layout.trailer_group_item;

            LayoutInflater inflater = LayoutInflater.from(context);

            boolean shouldAttachToParentImmediately = false;

            convertView = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.trailer_list_header);
        textView.setText(headerTitle);

        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup viewGroup) {

        // Gets the Trailer from the Trailer List...
        Trailer trailer = trailers.get(childPosition);

        // Gets the Trailer Name...
        String trailerName = trailer.getName();

        // Gets the Trailer Type...
        String trailerType = trailer.getType();

        // Inflates a row in the list...
        if (convertView == null) {
            Context context = viewGroup.getContext();

            int layoutIdForListItem = R.layout.trailer_child_item;

            LayoutInflater inflater = LayoutInflater.from(context);

            boolean shouldAttachToParentImmediately = false;

            convertView = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        }

        /**
         * At this point we get the child view and attach a click listener so we can tell when
         * the user wants to watch a Youtube video.
         */
        convertView.findViewById(R.id.watch_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClickHandler.onClick(trailers.get(childPosition), "watch");

            }
        });

        /**
         * At this point we get the child view and attach a click listener so we can tell when
         * the user wants to share the Youtube Link of the specific trailer.
         */
        convertView.findViewById(R.id.share_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClickHandler.onClick(trailers.get(childPosition), "share");

            }
        });

        // Setting the Trailer Name to the Corresponding TextView...
        TextView trailerNameTv = (TextView) convertView.findViewById(R.id.trailer_name);
        trailerNameTv.setText(trailerName);

        // Setting the Trailer Type to the Corresponding TextView...
        TextView trailerTypeTv = (TextView) convertView.findViewById(R.id.trailer_type);
        trailerTypeTv.setText("( " + trailerType + " )");

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
