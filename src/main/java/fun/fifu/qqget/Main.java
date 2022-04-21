package fun.fifu.qqget;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fun.fifu.qqget.pojo.ExportPojo;
import fun.fifu.qqget.pojo.GroupPojo;
import fun.fifu.qqget.pojo.UserPojo;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Bot b;
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException {
        System.out.println("本程序用来导出QQ内的好友列表，包括但不限于QQ群，QQ列表\n注意:deviceInfo.json为设备信息，请勿泄露\n\n");
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你的QQ号：");
        long qq = scanner.nextLong();
        System.out.println("请输入你的QQ密码：");
        String password = scanner.next();
        System.out.println("正在登录，请留意任务栏处的验证码");
        scanner.close();
        b = BotFactoryJvm.newBot(qq, password, new BotConfiguration() {
            {
                setDeviceInfo(context -> SystemDeviceInfoKt.loadAsDeviceInfo(new File("deviceInfo.json"), context));
            }
        });
        b.login();

        System.out.println("登录成功！" + b.getNick() + "(" + b.getId() + ")" + "欢迎您！");
        System.out.println("正在导出好友列表......");
        ExportPojo exportPojo = new ExportPojo(new UserPojo(b.getId(), b.getNick()));
        List<UserPojo> friends = exportPojo.getFriends();
        b.getFriends().forEach(friend -> friends.add(new UserPojo(friend.getId(), friend.getNick())));

        System.out.println("正在群聊列表......");
        List<GroupPojo> groups = exportPojo.getGroups();
        b.getGroups().forEach(group -> {
            GroupPojo groupPojo = new GroupPojo(
                    group.getId(),
                    group.getName(),
                    new UserPojo(
                            group.getOwner().getId(),
                            group.getOwner().getNick()
                    )
            );
            groups.add(groupPojo);

            group.getMembers().forEach(member -> groupPojo.getMembers().add(new UserPojo(member.getId(), member.getNick())));
        });

        IOTools.writeTextFile(gson.toJson(exportPojo), "utf-8", b.getId() + ".json");
        System.out.println("导出完毕！文件保存在" + b.getId() + ".txt");
    }
}
