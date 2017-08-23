package com.vn.hcmute.team.cortana.mymoney.tools.galleryloader.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 8/23/17.
 */

public class ReturnGalleryValue {
    
    private List<Folder> folders;
    private List<ImageGallery> images;
    
    public ReturnGalleryValue(){
        folders=new ArrayList<>();
        images=new ArrayList<>();
    }
    
    public List<Folder> getFolders() {
        return folders;
    }
    
    public List<ImageGallery> getImages() {
        return images;
    }
    
    public void setFolders(
              List<Folder> folders) {
        this.folders = folders;
    }
    
    public void setImages(
              List<ImageGallery> images) {
        this.images = images;
    }
}
