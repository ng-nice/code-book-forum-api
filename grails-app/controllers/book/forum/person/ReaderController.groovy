package book.forum.person



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ReaderController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Reader.list(params), [status: OK]
    }

    @Transactional
    def save(Reader readerInstance) {
        if (readerInstance == null) {
            render status: NOT_FOUND
            return
        }

        readerInstance.validate()
        if (readerInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        readerInstance.save flush:true
        respond readerInstance, [status: CREATED]
    }

    @Transactional
    def update(Reader readerInstance) {
        if (readerInstance == null) {
            render status: NOT_FOUND
            return
        }

        readerInstance.validate()
        if (readerInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        readerInstance.save flush:true
        respond readerInstance, [status: OK]
    }

    @Transactional
    def delete(Reader readerInstance) {

        if (readerInstance == null) {
            render status: NOT_FOUND
            return
        }

        readerInstance.delete flush:true
        render status: NO_CONTENT
    }
}
