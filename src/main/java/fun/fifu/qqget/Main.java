package fun.fifu.qqget;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;

public class Main {
	public static Bot b;

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
		ContactList<Friend> friends = b.getFriends();
		StringBuilder sb = new StringBuilder();
		sb.append("friends: \n");
		System.out.println("正在导出好友列表：");
		for (Friend friend : friends) {
			long id = friend.getId();
			String nick = friend.getNick();
			nick.replaceAll("\n", "");
			System.out.println(id + ": " + nick);
			sb.append("    ").append(id).append(": ").append(nick).append("\n");
		}
		sb.append("\n\n");

		System.out.println("正在群聊列表(可能太长所以不显示)......");
		ContactList<Group> groups = b.getGroups();
		for (Group group : groups) {
			long gid = group.getId();
			String gname = group.getName();
			gname.replaceAll("\n", "");
			sb.append("#").append(gname);
			sb.append(gid).append(": \n");
			String onick = group.getOwner().getNick();
			onick.replaceAll("\n", "");
			long oid = group.getOwner().getId();
			sb.append("#").append(oid).append(": ").append(onick).append("\n");
			sb.append(gid).append(": \n");
			ContactList<Member> members = group.getMembers();
			for (Member member : members) {
				long mid = member.getId();
				String mnick = member.getNick();
				mnick.replaceAll("\n", "");
				sb.append("    ").append(mid).append(": ").append(mnick).append("\n");
			}
			sb.append("\n\n");
		}
		IOTools.writeTextFile(sb.toString(), "utf-8", b.getId() + ".txt");
		System.out.println("导出完毕！文件保存在" + b.getId() + ".txt");
		// System.out.println(sb);
	}
}
