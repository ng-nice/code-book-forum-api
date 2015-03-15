package book.forum.auth

class LogoutController {
    static defaultAction = "index";
    static allowedMethods = [index: "POST"]

    def index() {
        request.logout();
        render('Ok');
    }
}
