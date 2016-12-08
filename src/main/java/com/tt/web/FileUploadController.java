//package com.tt.web;
//
//import com.tt.ext.web.FileUploadProgressListener;
//import com.tt.ext.web.ProgressEntity;
//import com.tt.util.FileUtil;
//import com.tt.util.SessionUtil;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * Created by tt on 2016/11/28.
// */
//@Controller
//@RequestMapping("file")
//public class FileUploadController {
//    @RequestMapping(value = "/upload_index",method = RequestMethod.GET)
//    public String index(){
//        return "fileUpload";
//    }
//    @RequestMapping(value = "/upload",method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String,Object> upload(MultipartFile file){
//        String filePath = SessionUtil.get().getServletContext().getRealPath("/")+"WEB-INF/resources/upload/";
//        UUID id = null;
//        try {
//            id = FileUtil.saveFile(file,filePath, uuid->{
//                System.out.println(uuid.toString());
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        SessionUtil.removeAttribute(FileUploadProgressListener.SESSION_PROGRESS_KEY);
//        Map<String,Object> ret = new HashMap<>();
//        ret.put("uuid",id);
//        return ret;
//    }
//    @RequestMapping(value = "/upload",method = RequestMethod.GET)
//    @ResponseBody
//    public ProgressEntity progress(){
//        return SessionUtil.getAttribute(FileUploadProgressListener.SESSION_PROGRESS_KEY,ProgressEntity.class);
//    }
//
//    @RequestMapping(value = "upload2_index",method = RequestMethod.GET)
//    public String upload2(){
//        return "fileUpload2";
//    }
//}
