package com.example.routersever;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/12 18:08.
 * Function :
 */
public class DataAIDLMessage implements Parcelable {

    private boolean isString;

    private int messageType = -1;

    private int anInt = -1;
    private long aLong;
    private boolean aBoolean;
    private float aFloat;
    private double aDouble;
    private String aString;

    public boolean isString() {
        return isString;
    }

    public void setInt(int anInt) {
        this.anInt = anInt;
        this.messageType = 0;
    }

    public void setLong(long aLong) {
        this.aLong = aLong;
        this.messageType = 1;
    }

    public void setBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
        this.messageType = 2;
    }

    public void setFloat(float aFloat) {
        this.aFloat = aFloat;
        this.messageType = 3;
    }

    public void setDouble(double aDouble) {
        this.aDouble = aDouble;
        this.messageType = 4;
    }

    public void setString(String aString) {
        this.aString = aString;
        this.messageType = 5;
        this.isString = true;
    }

    public String getString() {
        return aString;
    }

    public Object getObject() {
        Object o;
        switch (messageType) {
            case 0:
                o = anInt;
                break;
            case 1:
                o = aLong;
                break;
            case 2:
                o = aBoolean;
                break;
            case 3:
                o = aFloat;
                break;
            case 4:
                o = aDouble;
                break;
            case 5:
                o = aString;
                break;
            default:
                o = null;
                break;
        }
        return o;
    }

    public DataAIDLMessage() {
    }

    private DataAIDLMessage(Parcel in) {
        messageType = in.readInt();
        anInt = in.readInt();
        aLong = in.readLong();
        boolean[] booleans = new boolean[2];
        in.readBooleanArray(booleans);
        isString = booleans[0];
        aBoolean = booleans[1];
        aFloat = in.readFloat();
        aDouble = in.readDouble();
        aString = in.readString();
    }

    public static final Creator<DataAIDLMessage> CREATOR = new Creator<DataAIDLMessage>() {
        @Override
        public DataAIDLMessage createFromParcel(Parcel in) {
            return new DataAIDLMessage(in);
        }

        @Override
        public DataAIDLMessage[] newArray(int size) {
            return new DataAIDLMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(messageType);
        dest.writeInt(anInt);
        dest.writeLong(aLong);
        dest.writeBooleanArray(new boolean[]{isString, aBoolean});
        dest.writeFloat(aFloat);
        dest.writeDouble(aDouble);
        dest.writeString(aString);
    }
}
