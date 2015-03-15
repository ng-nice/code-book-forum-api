package book.forum.post



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PostController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Post.list(params), [status: OK]
    }

    @Transactional
    def save(Post postInstance) {
        if (postInstance == null) {
            render status: NOT_FOUND
            return
        }

        postInstance.validate()
        if (postInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        postInstance.save flush:true
        respond postInstance, [status: CREATED]
    }

    @Transactional
    def update(Post postInstance) {
        if (postInstance == null) {
            render status: NOT_FOUND
            return
        }

        postInstance.validate()
        if (postInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        postInstance.save flush:true
        respond postInstance, [status: OK]
    }

    @Transactional
    def delete(Post postInstance) {

        if (postInstance == null) {
            render status: NOT_FOUND
            return
        }

        postInstance.delete flush:true
        render status: NO_CONTENT
    }
}
