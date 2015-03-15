package book.forum.person


import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.test.mixin.*
import spock.lang.*

@TestFor(AuthorController)
@Mock(Author)
class AuthorControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params['name'] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when: "The index action is executed"
        controller.index()

        then: "The response is correct"
        response.status == OK.value
        response.text == ([] as JSON).toString()
    }

    void "Test the save action correctly persists an instance"() {

        when: "The save action is executed with an invalid instance"
        // Make sure the domain class has at least one non-null property
        // or this test will fail.
        def author = new Author()
        controller.save(author)

        then: "The response status is NOT_ACCEPTABLE"
        response.status == NOT_ACCEPTABLE.value

        when: "The save action is executed with a valid instance"
        response.reset()
        populateValidParams(params)
        author = new Author(params)

        controller.save(author)

        then: "The response status is CREATED and the instance is returned"
        response.status == CREATED.value
        response.text == (author as JSON).toString()
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when: "Update is called for a domain instance that doesn't exist"
        controller.update(null)

        then: "The response status is NOT_FOUND"
        response.status == NOT_FOUND.value

        when: "An invalid domain instance is passed to the update action"
        response.reset()
        def author = new Author()
        controller.update(author)

        then: "The response status is NOT_ACCEPTABLE"
        response.status == NOT_ACCEPTABLE.value

        when: "A valid domain instance is passed to the update action"
        response.reset()
        populateValidParams(params)
        author = new Author(params).save(flush: true)
        controller.update(author)

        then: "The response status is OK and the updated instance is returned"
        response.status == OK.value
        response.text == (author as JSON).toString()
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when: "The delete action is called for a null instance"
        controller.delete(null)

        then: "A NOT_FOUND is returned"
        response.status == NOT_FOUND.value

        when: "A domain instance is created"
        response.reset()
        populateValidParams(params)
        def author = new Author(params).save(flush: true)

        then: "It exists"
        Author.count() == 1

        when: "The domain instance is passed to the delete action"
        controller.delete(author)

        then: "The instance is deleted"
        Author.count() == 0
        response.status == NO_CONTENT.value
    }
}
