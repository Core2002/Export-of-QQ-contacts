package fun.fifu.qqget.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ExportPojo {
    UserPojo ExportQQ;
    Date ExportDate;
    List<GroupPojo> Groups ;
    List<UserPojo> Friends;

    public ExportPojo(UserPojo exportQQ) {
        this.ExportQQ = exportQQ;
        this.ExportDate = new Date();
        this.Groups= new ArrayList<>();
        this.Friends = new ArrayList<>();
    }
}