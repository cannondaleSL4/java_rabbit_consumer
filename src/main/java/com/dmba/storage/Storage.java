package com.dmba.storage;

import com.dmba.dao.UsersOrder;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;

public interface Storage {

    void saveMessage(UsersOrder usersOrderEntity);
}
