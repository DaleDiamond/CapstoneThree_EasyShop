package org.yearup.data;

import org.yearup.models.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    User getUserById(int userId);

    int getIdByUsername(String username);

    User create(User user);

    boolean exists(String username);

    User getByUserName(String userName);
}
