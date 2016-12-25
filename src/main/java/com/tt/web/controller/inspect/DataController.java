package com.tt.web.controller.inspect;

import com.alibaba.fastjson.JSONObject;
import com.tt.util.ExcelSheet;
import com.tt.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.tt.web.controller.BaseController;
import com.tt.web.exception.ParameterException;
import com.tt.model.InspectData;
import com.tt.service.InspectDataServiceI;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tt on 2016/10/2.
 */
@Controller
@RequestMapping("inspect/data")
public class DataController extends BaseController<InspectData> {
    @Autowired
    private InspectDataServiceI inspectDataService;

//    @RequestMapping("index")
//    public String index(Model model) {
//        return "module_data/index";
//    }

    /**
     * @param PRG  工程id
     * @param STZH 桩号id
     * @return
     */
    @RequestMapping(value = "query/{PRG}/{STZH}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject queryData(@PathVariable String PRG, @PathVariable String STZH) {
        List<InspectData> list_press = inspectDataService.list(PRG, STZH, true);
        List<InspectData> list_release = inspectDataService.list(PRG, STZH, false);
        Map<String, Object> data = sortData(list_press);
        JSONObject ret = new JSONObject();
        ret.put("source_press", listResponse(list_press));
        ret.put("source_release", listResponse(list_release));
        ret.put("statistic", "");
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            ret.put(entry.getKey(), entry.getValue());
        }
        return ret;
    }

    @RequestMapping(value = "download/{PRG}/{STZH}")
    public void download(@PathVariable String PRG, @PathVariable String STZH,HttpServletResponse response) {
        List<InspectData> list_press = inspectDataService.list(PRG, STZH, true);
        List<InspectData> list_release = inspectDataService.list(PRG, STZH, false);

        ExcelSheet sheet_press = createExcelSheet("加载数据", list_press);
        ExcelSheet sheet_release = createExcelSheet("卸载数据", list_release);
        LinkedList<ExcelSheet> list = new LinkedList<>();
        list.add(sheet_press);
        list.add(sheet_release);
        HSSFWorkbook excel = ExcelUtil.createExcel(list);


        String template = "%s-%s-%s.xls";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String filename = String.format(template,dateFormat.format(new Date()),PRG,STZH);

        String mimeType = URLConnection.guessContentTypeFromName(filename);

        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("afttachment; filename=\"%s\"", filename));
        try {
            excel.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ExcelSheet createExcelSheet(String name,List<InspectData> list){
        String[] columns = new String[]{"设备编号"
                , "压力"
                , "平均压力"
                , "荷载"
                , "平均荷载"
                , "位移"
                , "平均位移"
                , "GPS信息"
                , "设备代号"
                , "数据上传时间"
                , "时间间隔"
                , "倾角数据"
                , "挠度数据"};

        List<Object[]> data = new LinkedList<>();
        if(list!=null&&!list.isEmpty()){
            list.forEach(item->{
                data.add(new Object[]{
                        item.getDevnb(),
                        item.getPrs_str(),
                        item.getAvgPrs(),
                        item.getHzjc_str(),
                        item.getAvgHzjc(),
                        item.getWyjc_str(),
                        item.getAvgWyjc(),
                        item.getLat() + "," + item.getLng(),
                        item.getDevstr(),
                        item.getTime(),
                        item.getInterval(),
                        item.getQjx_str(),
                        item.getNdsj_str()
                });
            });
        }

        return new ExcelSheet(name, Arrays.asList(columns), data);
    }

    private Map<String, Object> sortData(List<InspectData> source) {
        Map<String, Object> chart0 = inspectDataService.calcChart0(source);
        ;
        Map<String, Object> chart1 = inspectDataService.calcChart1(source);
        Map<String, Object> chart2 = inspectDataService.calcChart2(source);
        Map<String, Object> ret = new HashMap<>();
        ret.put("chart0", chart0);
        ret.put("chart1", chart1);
        ret.put("chart2", chart2);
        return ret;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestParam(value = "ids[]") int[] ids) {
        List<Integer> list = new LinkedList<>();
        Arrays.stream(ids).forEach(id -> list.add(id));
        int ret = inspectDataService.del(list);
        return flagResponse(ret);
    }

//    @RequestMapping(value = "keys", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Map<String, Object>> listKeys() {
//        return inspectDataService.loadKeys(getDeptId());
//    }
//
    @RequestMapping(value = "unLinkedKeys", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listUnlinkedKeys() {
        return listResponse(inspectDataService.loadUnLinkedKeys());
    }

    @RequestMapping(value = "linkedKeys/{plan_id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> listLinkedKeys(@PathVariable Integer plan_id) {
        return inspectDataService.loadLinkedKeys(plan_id);
    }

    @RequestMapping(value = "linkData/{plan_id}", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject linkData(@PathVariable(value = "plan_id") Integer plan_id, @RequestBody Map<String, Object> data) {
        int count = inspectDataService.linkData(plan_id, data);
        return flagResponse(count > 0);
    }

    @RequestMapping("loadLatestData/{PRG}/{STZH}")
    @ResponseBody
    public InspectData loadLatestData(@PathVariable String PRG, @PathVariable String STZH) {
        if (PRG == null || STZH == null) {
            throw new ParameterException("参数异常,请设置工程号和桩号!");
        }
        return inspectDataService.loadLatestData(PRG, STZH);
    }
}