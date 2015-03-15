package book.forum.auth


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserRoleController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond UserRole.list(params), [status: OK]
    }

    @Transactional
    def save(UserRole userRoleInstance) {
        if (userRoleInstance == null) {
            render status: NOT_FOUND
            return
        }

        userRoleInstance.validate()
        if (userRoleInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        userRoleInstance.save flush: true
        respond userRoleInstance, [status: CREATED]
    }

    @Transactional
    def update(UserRole userRoleInstance) {
        if (userRoleInstance == null) {
            render status: NOT_FOUND
            return
        }

        userRoleInstance.validate()
        if (userRoleInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        userRoleInstance.save flush: true
        respond userRoleInstance, [status: OK]
    }

    @Transactional
    def delete(UserRole userRoleInstance) {

        if (userRoleInstance == null) {
            render status: NOT_FOUND
            return
        }

        userRoleInstance.delete flush: true
        render status: NO_CONTENT
    }
}
