package fun.fifu.qqget.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupPojo {
    Long GroupNumber;
    String GroupName;
    UserPojo Owner;
    List<UserPojo> Members;

    public GroupPojo(Long groupNumber, String groupName, UserPojo owner) {
        this.GroupNumber = groupNumber;
        this.GroupName = groupName;
        this.Owner = owner;
        this.Members = new ArrayList<>();
    }
}