package book.forum.person


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PersonController {

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Person.list(params), [status: OK]
    }

    @Transactional
    def save(Person personInstance) {
        if (personInstance == null) {
            render status: NOT_FOUND
            return
        }

        personInstance.validate()
        if (personInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        personInstance.save flush: true
        respond personInstance, [status: CREATED]
    }

    @Transactional
    def update(Person personInstance) {
        if (personInstance == null) {
            render status: NOT_FOUND
            return
        }

        personInstance.validate()
        if (personInstance.hasErrors()) {
            render status: UNPROCESSABLE_ENTITY
            return
        }

        personInstance.save flush: true
        respond personInstance, [status: OK]
    }

    @Transactional
    def delete(Person personInstance) {

        if (personInstance == null) {
            render status: NOT_FOUND
            return
        }

        personInstance.delete flush: true
        render status: NO_CONTENT
    }
}
