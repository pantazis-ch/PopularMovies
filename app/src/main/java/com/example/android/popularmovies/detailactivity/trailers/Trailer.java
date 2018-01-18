package com.example.android.popularmovies.detailactivity.trailers;

import android.os.Parcel;
import android.os.Parcelable;


public class Trailer implements Parcelable {

    private String name;
    private String size;
    private String source;
    private String type;

    public Trailer(String name, String size,String source, String type) {
        this.name = name;
        this.size = size;
        this.source = source;
        this.type = type;
    }

    private Trailer(Parcel in) {
        name = in.readString();
        size = in.readString();
        source = in.readString();
        type = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(size);
        parcel.writeString(source);
        parcel.writeString(type);
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }

}
