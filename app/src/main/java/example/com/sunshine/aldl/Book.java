package example.com.sunshine.aldl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by qianxiangsen on 2017/3/31.
 */

public class Book implements Parcelable {

    public int bookId;
    public String bookNmae;
    public Book(int bookId,String bookNmae){
        this.bookId = bookId;
        this.bookNmae = bookNmae;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(bookId);
        out.writeString(bookNmae);
    }
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>(){
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    private Book(Parcel in){
        bookId = in.readInt();
        bookNmae = in.readString();
    }
}
