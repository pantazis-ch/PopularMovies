package com.example.android.popularmovies.movies.favorites;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MoviesContract;
import com.example.android.popularmovies.movies.Movie;
import com.example.android.popularmovies.utilities.FileUtility;

/**
 * Created by My on 27/2/2017.
 */

/**
 * This MovieCursorAdapter creates and binds ViewHolders, that hold the description and priority of a task,
 * to a RecyclerView to efficiently display data.
 */
public class MovieCursorAdapter extends RecyclerView.Adapter<MovieCursorAdapter.MovieViewHolder> {

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    /**
     * An ocCickHandler handler that we have defined to make it easy for an Activity to interface with
     * our RecyclerView.
     */
    private final MovieCursorAdapter.MovieAdapterOnClickHandler mClickHandler;

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;


    /**
     * Constructor for the MovieCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     */
    public MovieCursorAdapter(Context mContext, MovieCursorAdapter.MovieAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.mClickHandler = clickHandler;
    }


    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new MovieViewHolder that holds the view for each task
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.movie_list_item, parent, false);

        return new MovieViewHolder(view);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        // Indices for the _id, description, and priority columns
        //int idIndex = mCursor.getColumnIndex(TaskContract.TaskEntry._ID);
        //int descriptionIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION);
        //int priorityIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        //final int id = mCursor.getInt(idIndex);
        //String description = mCursor.getString(descriptionIndex);
        //int priority = mCursor.getInt(priorityIndex);

        String title = mCursor.getString(2);
        String original_title = mCursor.getString(3);
        String movie_id = mCursor.getString(1);
        String moviePoster = mCursor.getString(4);
        String plot_synopsis = mCursor.getString(5);
        String rating = mCursor.getString(6);
        String release_date = mCursor.getString(7);

        Bitmap b = FileUtility.getImageFromInternalStorage(movie_id,mContext);
        if (b!=null) {
            holder.mImageView.setImageBitmap(b);
        }

        //Set values
        //holder.itemView.setTag(id);
        //holder.taskDescriptionView.setText(description);

        /*ContextWrapper cw = new ContextWrapper(mContext.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        String fileName = movie_id + ".jpg";

        try {
            File f=new File(directory, fileName);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            //ImageView img=(ImageView)findViewById(R.id.imgPicker);
            //ImageView img = holder.mImageView;
            //img.setImageBitmap(b);
            //int height = holder.mImageView.getHeight();
            //int width = holder.mImageView.getWidth();
            holder.mImageView.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }*/

        //URL posterUrl = NetworkUtilities.buildImageUrl(moviePoster);

        //Context ctx = holder.mImageView.getContext();

        //Picasso.with(ctx).load(String.valueOf(posterUrl)).into(holder.mImageView);


        // Programmatically set the text and color for the priority TextView
        //String priorityString = "" + priority; // converts int to String
        //holder.priorityView.setText(priorityString);

        //GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
        //int priorityColor = getPriorityColor(priority);
        //priorityCircle.setColor(priorityColor);

    }

    private void loadImageFromStorage(String path)
    {



    }


    /*
    Helper method for selecting the correct priority circle color.
    P1 = red, P2 = orange, P3 = yellow
    */
    /*private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch(priority) {
            case 1: priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2: priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3: priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default: break;
        }
        return priorityColor;
    }*/


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    // Inner class for creating ViewHolders
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mImageView;

        public MovieViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image_view_rv_main);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param view The View that was clicked
         */
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            //String movieStr = movies.get(adapterPosition).getOriginalTitle();

            System.out.println("Adapter --> ");

            mCursor.moveToPosition(adapterPosition);

            int id = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_ID);
            int tit = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE);
            int ortitle = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE);
            int rat = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE);
            int reldate = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE);
            int poster = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH);
            int over = mCursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_OVERVIEW);

            //int id = mCursor.getInt(idIndex);
            //String description = mCursor.getString(descriptionIndex);
            //int priority = mCursor.getInt(priorityIndex);


            Movie movieObject = null;



            String title = mCursor.getString(tit);
            String original_title = mCursor.getString(ortitle);
            String movie_id = mCursor.getString(id);
            String moviePoster = mCursor.getString(poster);
            String plot_synopsis = mCursor.getString(over);
            String rating = mCursor.getString(rat);
            String release_date = mCursor.getString(reldate);

            System.out.println("title --> " + title);

            movieObject = new Movie(title,original_title,movie_id,moviePoster,plot_synopsis,rating,release_date);

            mClickHandler.onClick(movieObject);
        }

        // Class variables for the task description and priority TextViews
        //TextView taskDescriptionView;
        //TextView priorityView;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        /*public MovieViewHolder(View itemView) {
            super(itemView);

            taskDescriptionView = (TextView) itemView.findViewById(R.id.taskDescription);
            priorityView = (TextView) itemView.findViewById(R.id.priorityTextView);
        }*/
    }
}
