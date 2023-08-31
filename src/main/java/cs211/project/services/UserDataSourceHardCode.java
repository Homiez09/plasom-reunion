package cs211.project.services;

import cs211.project.models.User;
import cs211.project.models.collections.UserList;

public class UserDataSourceHardCode implements Datasource<UserList>{
    @Override
    public UserList readData() {
        UserList users = new UserList();
        users.addUser("Homiez","HomieZ09","@Mingnaruk1234");
        users.addUser("Homiez1","HomieZ09_test","@Mingnaruk1234");
        users.addUser("PinkPPanther","mingmmie","@Ming1234");
        users.addUser("Owlvi","Owvil","@Manza1150");
        users.addUser("Ging","Ginglnwza09","@GlinglowTest5555");
        users.addUser("test","t","t");
        return users;
    }

    @Override
    public void writeData(UserList data) {

    }

    public void writeData(User data) {

    }
}
