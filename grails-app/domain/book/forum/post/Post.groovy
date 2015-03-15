package book.forum.post

import book.forum.person.Person

class Post {
    Person poster;
    static belongsTo = [poster: Person];
    Collection<File> files = [];
    static hasMany = [
            files: File
    ];
    static constraints = {
    }
}
