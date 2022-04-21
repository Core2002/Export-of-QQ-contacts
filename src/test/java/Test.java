import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fun.fifu.qqget.pojo.ExportPojo;
import fun.fifu.qqget.pojo.GroupPojo;
import fun.fifu.qqget.pojo.UserPojo;

public class Test {
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @org.junit.Test
    public void testPojo(){
        ExportPojo po = new ExportPojo(new UserPojo(0L,"NekoCore"));
        po.getGroups().add(new GroupPojo(1L,"group1",new UserPojo(2L,"user1")));
        po.getFriends().add(new UserPojo(2L,"qwq"));
        System.out.println(gson.toJson(po));
    }
}
