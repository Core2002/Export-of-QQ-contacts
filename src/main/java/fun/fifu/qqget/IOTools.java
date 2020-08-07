package fun.fifu.qqget;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author FiFuServer
 */
public class IOTools {
    /**
     * 把字符数组写入文件
     *
     * @param c
     * @param path
     */
    public static void writeFile(byte[] c, String path) throws IOException {
        FileOutputStream i = new FileOutputStream(path);
        i.write(c, 0, c.length);
        i.flush();
    }

    /**
     * 把字符串写入文件
     *
     * @param text
     * @param encode
     * @param path
     */
    public static void writeTextFile(String text, String encode, String path) throws IOException {
        writeFile(text.getBytes(encode), path);
    }

    /**
     * .
     * 写入规范的json文件到指定目录
     *
     * @param jsonObject
     * @param path
     */
    public static void writeJsonFile(JSONObject jsonObject, String path) throws IOException {
        writeTextFile(toPrettyFormat(jsonObject.toString()), "utf8", path);
    }

    /**
     * 把文件都成字符数组
     *
     * @param path
     * @return
     */
    public static byte[] readFile(String path) throws IOException {
        FileInputStream i = new FileInputStream(path);
        byte[] buf = new byte[i.available()];
        i.read(buf, 0, buf.length);
        return buf;
    }

    /**
     * 从目录使用指定编码读文件
     *
     * @param path
     * @param encode
     * @return
     */
    public static String readTextFile(String path, String encode) throws IOException {
        return new String(readFile(path), encode);
    }


    /**
     * 格式化输出JSON字符串
     *
     * @return 格式化后的JSON字符串
     */
    public static String toPrettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    /**
     * 从文件得到JSONObject
     *
     * @param path
     * @return
     * @throws ParseException
     */
    public static JSONObject getJSONObject(String path) throws IOException {
        try {
            return (JSONObject) (new JSONParser().parse(IOTools.readTextFile(path, "utf8")));
        } catch (ParseException e) {
            throw new RuntimeException(path + ":文件异常！请检查！");
        }
    }

    public static void zhengliJsonFile(String path) throws IOException, ParseException {
        IOTools.writeTextFile(toPrettyFormat(new JSONParser().parse(IOTools.readTextFile(path, "utf8")).toString()), "utf8", path);
    }

    /**
     * @param arlList 列表去重
     */
    public static void removeDuplicate(ArrayList arlList) {
        HashSet h = new HashSet(arlList);
        arlList.clear();
        arlList.addAll(h);
    }

}
