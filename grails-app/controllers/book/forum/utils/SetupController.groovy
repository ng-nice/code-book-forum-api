package book.forum.utils

/** 初始化或打patch */
class SetupController {
    def setupService;
    def index() {
        setupService.init();
        render('ok');
    }
}
