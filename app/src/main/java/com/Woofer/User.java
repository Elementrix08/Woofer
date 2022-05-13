package com.Woofer;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

// Class used to represent a single user
public class User implements Parcelable
{
    private final String Username;
    private final String Email;
    private final int UserID;

    public User(JSONObject user) throws JSONException
    {
        this.Username = user.getString("Username");
        this.Email = user.getString("UserEmail");
        this.UserID = user.getInt("UserID");
    }

    protected User(Parcel in)
    {
        Username = in.readString();
        Email = in.readString();
        UserID = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>()
    {
        @Override
        public User createFromParcel(Parcel in)
        {
            return new User(in);
        }

        @Override
        public User[] newArray(int size)
        {
            return new User[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(Username);
        dest.writeString(Email);
        dest.writeInt(UserID);
    }

    String getUsername()
    {
        return Username;
    }

    String getEmail()
    {
        return Email;
    }

    Integer getUserID()
    {
        return UserID;
    }
}
