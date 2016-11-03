package com.cyc.core;
import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class ZJPFileListener implements FileAlterationListener{  
  
      
    ZJPFileMonitor monitor = null;  
    @Override  
    public void onStart(FileAlterationObserver observer) {  
        //System.out.println("onStart");  
    }  
    @Override  
    public void onDirectoryCreate(File directory) {  
        System.out.println("onDirectoryCreate:" +  directory.getName());  
    }  
  
    @Override  
    public void onDirectoryChange(File directory) {  
        System.out.println("onDirectoryChange:" + directory.getName());  
    }  
  
    @Override  
    public void onDirectoryDelete(File directory) {  
        System.out.println("onDirectoryDelete:" + directory.getName());  
    }  
  
    @Override  
    public void onFileCreate(File file) {  
        System.out.println("onFileCreate:" + file.getName());  
    }  
  
    @Override  
    public void onFileChange(File file) {  
        System.out.println("onFileCreate : " + file.getName());  
    }  
  
    @Override  
    public void onFileDelete(File file) {  
        System.out.println("onFileDelete :" + file.getName());  
    }  
  
    @Override  
    public void onStop(FileAlterationObserver observer) {  
        //System.out.println("onStop");  
    }  
  
}  