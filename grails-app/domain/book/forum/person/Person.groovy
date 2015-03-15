package book.forum.person

import book.forum.auth.User
import book.forum.post.Post

class Person {
    String email;
    String nickname;
    Date birthday;
    ZSexType sexType;
    Collection<User> users = [];
    Collection<Post> posters = [];
    static hasMany = [
            users: User,
            posters: Post
    ];

    static constraints = {
        email(email: true, maxSize: 32, nullable: false, blank: false);
        nickname(minSize: 2, maxSize: 32, nullable: false, blank: false);
        birthday(nullable: true);
        sexType(nullable: true);
    }
}
