package com.dmba.storage;

import com.dmba.dao.UsersOrder;

public interface Storage {
    void saveMessage(UsersOrder usersOrderEntity);
}
