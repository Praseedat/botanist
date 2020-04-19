package com.example.botanist.tfLite;

import android.os.Parcel;
import android.os.Parcelable;

public class Recognition implements Parcelable {
    /**
     * A unique identifier for what has been recognized. Specific to the class, not the instance of
     * the object.
     */
    private final String id;

    /**
     * Display name for the recognition.
     */
    private final String title;

    /**
     * Whether or not the model features quantized or float weights.
     */
    private final boolean quant;

    /**
     * A sortable score for how good the recognition is relative to others. Higher should be better.
     */
    private final Float confidence;

    public Recognition(
            final String id, final String title, final Float confidence, final boolean quant) {
        this.id = id;
        this.title = title;
        this.confidence = confidence;
        this.quant = quant;
    }

    private Recognition(Parcel in) {
        id = in.readString();
        title = in.readString();
        quant = in.readByte() != 0;
        if (in.readByte() == 0) {
            confidence = null;
        } else {
            confidence = in.readFloat();
        }
    }

    public static final Creator<Recognition> CREATOR = new Creator<Recognition>() {
        @Override
        public Recognition createFromParcel(Parcel in) {
            return new Recognition(in);
        }

        @Override
        public Recognition[] newArray(int size) {
            return new Recognition[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Float getConfidence() {
        return confidence;
    }

    @Override
    public String toString() {
        String resultString = "";
        if (id != null) {
            resultString += "[" + id + "] ";
        }

        if (title != null) {
            resultString += title + " ";
        }

        if (confidence != null) {
            resultString += String.format("(%.1f%%) ", confidence * 100.0f);
        }

        return resultString.trim();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeByte((byte) (quant ? 1 : 0));
        if (confidence == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(confidence);
        }
    }
}
