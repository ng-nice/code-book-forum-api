package book.forum.post

class File {
    Post post;
    static belongsTo = [post: Post];

    String contentType;
    String hash;
    static constraints = {
    }
}
