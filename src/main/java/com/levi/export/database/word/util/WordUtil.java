package com.levi.export.database.word.util;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.TableStyle;
import com.levi.export.database.word.domain.TableStructureRenderData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * word文档工具
 */
public class WordUtil {
    private static final String TEMPLATE_DOSX_PATH = "/template.docx";

    /**
     * 导出数据库表结构为word
     *
     * @param tableStructureList
     * @param path
     */
    public static String exportDB2Word(List<TableStructureRenderData> tableStructureList, String path) {
        if (CommonUtil.isEmpty(tableStructureList)) {
            return "导出数据为空";
        }
        InputStream templateInputStream=WordUtil.class.getResourceAsStream(TEMPLATE_DOSX_PATH);
        if (templateInputStream==null){
            return "模板未找到";
        }
        Map<String, Object> data = new HashMap<>();
        data.put("tableStructureList", tableStructureList);
        XWPFTemplate template = XWPFTemplate.compile(templateInputStream).render(data);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            template.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                template.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    public static Style getHeaderStyle() {
        Style style = new Style();
        style.setBold(true);
        style.setFontSize(14);
        style.setColor("000000");
        style.setFontFamily("宋体");
        return style;
    }

    public static TableStyle getHeaderTableStyle() {
        TableStyle style = new TableStyle();
        style.setBackgroundColor("B7B7B7");
        return style;
    }

    public static Style getBodyStyle() {
        Style style = new Style();
        style.setBold(false);
        style.setFontSize(12);
        style.setColor("000000");
        style.setFontFamily("宋体");
        return style;
    }

    public static TableStyle getBodyTableStyle() {
        TableStyle style = new TableStyle();
        style.setBackgroundColor("DEDEDE");
        return style;
    }

    public static void main(String[] args) {
//        String path="D:\\javaworkspace\\export-database-word\\src\\main\\resources\\template.docx";
        String path = "template.docx";

        String s = ClassLoader.getSystemResource(path).getFile();
        System.out.println(s);
        File file = new File(s);
        System.out.println(file.exists());
//        String property = System.getProperties().getProperty("user.dir");
//        System.out.println(property);
    }
}
