package com.muzakki.ahmad.material.form;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

/**
 * Created by jeki on 5/30/16.
 */
public class Field implements Parcelable {
    private Type type;
    private final String name;

    private String title;
    private boolean required;
    private String kode; // using picker
    private String value;
    private int drawable;
    private int background;
    private ArrayList<Item> items;

    private int pickerCode;

    public int getPickerCode() {
        return pickerCode;
    }

    public void setPickerCode(int pickerCode) {
        this.pickerCode = pickerCode;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public enum Type{
        TEXT,TEXTAREA, NUMBER, RADIO,DATE,SPINNER,IMAGE,PICKER, PASSWORD, SEPARATOR,CHECKBOX
    }

    public Field(String name,Type type){
        if(type==Type.IMAGE){
            throw new IllegalArgumentException("You should create object with Field.Image");
        }
        this.name = name;
        this.type = type;
    }

    private Field(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(){
        String t = title==null?name:title;
        if(isRequired()) t+=" *";
        t = t.replace("_"," ");
        t = title==null?WordUtils.capitalizeFully(t):title;
        return t;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int getDrawable() {
        return drawable;
    }

    protected Field(Parcel in) {
        type = (Type) in.readValue(Type.class.getClassLoader());
        name = in.readString();
        title = in.readString();
        required = in.readByte() != 0x00;
        value = in.readString();
        drawable = in.readInt();
        background = in.readInt();

        if (in.readByte() == 0x01) {
            items = new ArrayList<>();
            in.readList(items, Item.class.getClassLoader());
        } else {
            items = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeString(name);
        dest.writeString(title);
        dest.writeByte((byte) (required ? 0x01 : 0x00));
        dest.writeString(value);
        dest.writeInt(drawable);
        dest.writeInt(background);

        if (items == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(items);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Field> CREATOR = new Creator<Field>() {
        @Override
        public Field createFromParcel(Parcel in) {
            return new Field(in);
        }

        @Override
        public Field[] newArray(int size) {
            return new Field[size];
        }
    };

    public static class Image extends Field{

        private int cameraCode;
        private int galleryCode;
        private ImageView imageView;
        private Orientation orientation = Orientation.SQUARE;

        public int getCameraCode() {
            return cameraCode;
        }

        public void setCameraCode(int cameraCode) {
            this.cameraCode = cameraCode;
        }

        public enum Orientation{
            POTRAIT,LANDSCAPE,SQUARE
        }

        public Image(String name){
            super(name);
            super.type = Type.IMAGE;
        }

        protected Image(Parcel in){
            super(in);
            orientation = (Orientation) in.readSerializable();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeSerializable(orientation);
        }

        public static final Creator<Field> CREATOR = new Creator<Field>() {
            @Override
            public Field createFromParcel(Parcel in) {
                return new Image(in);
            }

            @Override
            public Field[] newArray(int size) {
                return new Image[size];
            }
        };

        public Orientation getOrientation() {
            return orientation;
        }

        public void setOrientation(Orientation orientation) {
            this.orientation = orientation;
        }


        public int getGalleryCode() {
            return galleryCode;
        }

        public void setGalleryCode(int galleryCode) {
            this.galleryCode = galleryCode;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public void showImage() {
            Bitmap bm = BitmapFactory.decodeFile(getValue());
            imageView.setImageBitmap(bm);
        }
    }
}