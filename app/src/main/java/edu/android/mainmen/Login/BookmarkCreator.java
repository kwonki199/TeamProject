package edu.android.mainmen.Login;

public class BookmarkCreator {
    private String tv_store_name;
    private String tv_distance;
    private String tv_address;
    private int photoId;

    public BookmarkCreator(String tv_store_name, String tv_distance, String tv_address, int photoId) {
        this.tv_store_name = tv_store_name;
        this.tv_distance = tv_distance;
        this.tv_address = tv_address;
        this.photoId = photoId;
    }

    public String getTv_store_name() {
        return tv_store_name;
    }

    public void setTv_store_name(String tv_store_name) {
        this.tv_store_name = tv_store_name;
    }

    public String getTv_distance() {
        return tv_distance;
    }

    public void setTv_distance(String tv_distance) {
        this.tv_distance = tv_distance;
    }

    public String getTv_address() {
        return tv_address;
    }

    public void setTv_address(String tv_address) {
        this.tv_address = tv_address;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
