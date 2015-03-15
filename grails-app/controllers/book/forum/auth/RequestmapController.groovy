package book.forum.auth


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RequestmapController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Requestmap.list(params), [status: OK]
    }

    @Transactional
    def save(Requestmap requestmapInstance) {
        if (requestmapInstance == null) {
            render status: NOT_FOUND
            return
        }

        requestmapInstance.validate()
        if (requestmapInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        requestmapInstance.save flush: true
        respond requestmapInstance, [status: CREATED]
    }

    @Transactional
    def update(Requestmap requestmapInstance) {
        if (requestmapInstance == null) {
            render status: NOT_FOUND
            return
        }

        requestmapInstance.validate()
        if (requestmapInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        requestmapInstance.save flush: true
        respond requestmapInstance, [status: OK]
    }

    @Transactional
    def delete(Requestmap requestmapInstance) {

        if (requestmapInstance == null) {
            render status: NOT_FOUND
            return
        }

        requestmapInstance.delete flush: true
        render status: NO_CONTENT
    }
}
