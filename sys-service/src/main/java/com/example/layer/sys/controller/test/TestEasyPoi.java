package com.example.layer.sys.controller.test;

import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.example.layer.sys.engine.domain.core.SysUser;
import com.example.layer.sys.engine.domain.service.SysUserDomainService;
import org.springlayer.core.poi.EasyPoiUtil;
import org.springlayer.core.tool.api.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhaoyl
 * 测试easyPoi
 */
@RestController
@RequestMapping(value = "/test/poi")
public class TestEasyPoi {
    @Resource
    private SysUserDomainService sysUserDomainService;


    /**
     * 导出数据，使用map接收
     *
     * @param response
     * @throws IOException
     */
    @PostMapping("/exportExcel")
    public void exportExcel(@RequestBody Map<String, Object> param ,HttpServletResponse response) throws IOException {
        List<SysUser> list = sysUserDomainService.list();
        EasyPoiUtil.exportExcel(list, "导出用户2022", "用户" ,SysUser.class, "user", response);
    }

    /**
     * 导入
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/importExcel")
    public R imgImport(@RequestParam("file") MultipartFile file) throws IOException {
        List<SysUser> list = EasyPoiUtil.importExcel(file, SysUser.class);
        System.out.println(list);
        return R.data(list,"导入成功");
    }

    /**
     * 使用模板excel导出
     *
     * @param response
     * @throws Exception
     */
    @PostMapping("/excelTemplate")
    public void makeExcelTemplate(HttpServletResponse response, @RequestBody Map<String, Object> param) throws Exception {
        TemplateExportParams templatePath = new TemplateExportParams("d:/excel/用户信息文件模板.xls");
        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        map.put("date", sdf.format(new Date()));
        map.put("user", "admin");
        List<SysUser> list = sysUserDomainService.list();
        map.put("userList", list);
        EasyPoiUtil.exportExcel(templatePath, map, param.get("fileName").toString(), response);
    }

    /**
     * EXCEL转html预览
     */
    @GetMapping("previewExcel")
    public void excelToHtml(HttpServletResponse response) throws Exception {
        EasyPoiUtil.excelToHtml("C:\\Users\\Administrator\\Desktop\\user.xlsx",response);
    }

    /**
     * 使用模板word导出数据
     * @param param
     * @param response
     */
    @PostMapping("/wordTemplate")
    public void makeWordTemplate(@RequestBody Map<String, Object> param,HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("nativePlace", "湖北武汉");
        map.put("age", "20");
        map.put("nation", "汉族");
        map.put("phone", "15685654524");
        map.put("experience", "湖北武汉，工作三年，java工程师");
        map.put("evaluate", "优秀，善良，老实");
        //设置图片,如果无图片，不设置即可
//        WordImageEntity image = new WordImageEntity();
//        image.setHeight(200);
//        image.setWidth(150);
//        image.setUrl("E:/excel/pic.jpg");
//        image.setType(WordImageEntity.URL);
//        map.put("picture", image);
        try {
            EasyPoiUtil.WordTemplateExport(map,"C:\\Users\\Administrator\\Desktop\\user.docx",param.get("fileName").toString(),response);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
