package book.forum.auth


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RoleController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Role.list(params), [status: OK]
    }

    @Transactional
    def save(Role roleInstance) {
        if (roleInstance == null) {
            render status: NOT_FOUND
            return
        }

        roleInstance.validate()
        if (roleInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        roleInstance.save flush: true
        respond roleInstance, [status: CREATED]
    }

    @Transactional
    def update(Role roleInstance) {
        if (roleInstance == null) {
            render status: NOT_FOUND
            return
        }

        roleInstance.validate()
        if (roleInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        roleInstance.save flush: true
        respond roleInstance, [status: OK]
    }

    @Transactional
    def delete(Role roleInstance) {

        if (roleInstance == null) {
            render status: NOT_FOUND
            return
        }

        roleInstance.delete flush: true
        render status: NO_CONTENT
    }
}
