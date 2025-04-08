package ioc

import com.jrymos.spring.ioc.bean.UserService

beans {
    userService(UserService) {
        users = [user1, user2, user3, user4, user5, user6]
    }
}