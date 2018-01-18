package com.example.android.popularmovies.utilities;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.example.android.popularmovies.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtility {

    /**
     * Saves the content of an ImageView to the Internal Storage.
     * Each image that is saved is named after the movie_id of the corresponding movie.
     * In this way we can easily read or delete a poster associated with a specific movie.
     *
     * @param movieId The unique movie_id of the movie.
     * @param imageView The ImageView that displays the movie poster in the detail view.
     * @param context The context of the application.
     * @return
     */
    public static String saveImageToInternalStorage(String movieId, ImageView imageView, Context context){

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        String fileName = movieId + ".jpg";

        File fullPath = new File(directory, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fullPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            //bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    /** Gets the poster image for a specific movie from the internal storage.
     *
     * @param movieId The movie_id of the poster we want to find.
     * @param context The context of the application.
     * @return
     */
    public static Bitmap getImageFromInternalStorage(String movieId, Context context) {

        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        String fileName = movieId + ".jpg";

        Bitmap b = null;

        try {
            File f = new File(directory, fileName);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return b;
    }


    /**
     * This method deletes a poster from the internal storage.
     * It is used when the user unfavorites a specific movie.
     *
     * @param movieId The movie_id of the poster we want to delete.
     * @param context The context of the application.
     * @return
     */
    public static boolean deleteImageFromMemory(String movieId, Context context) {

        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        String fileName = movieId + ".jpg";

        File image = new File(directory, fileName);

        boolean deleted = image.delete();

        return deleted;
    }
}
