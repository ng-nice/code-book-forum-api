package book.forum.post



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FileController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond File.list(params), [status: OK]
    }

    @Transactional
    def save(File fileInstance) {
        if (fileInstance == null) {
            render status: NOT_FOUND
            return
        }

        fileInstance.validate()
        if (fileInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        fileInstance.save flush:true
        respond fileInstance, [status: CREATED]
    }

    @Transactional
    def update(File fileInstance) {
        if (fileInstance == null) {
            render status: NOT_FOUND
            return
        }

        fileInstance.validate()
        if (fileInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        fileInstance.save flush:true
        respond fileInstance, [status: OK]
    }

    @Transactional
    def delete(File fileInstance) {

        if (fileInstance == null) {
            render status: NOT_FOUND
            return
        }

        fileInstance.delete flush:true
        render status: NO_CONTENT
    }
}
