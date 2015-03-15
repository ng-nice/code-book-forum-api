package book.forum.utils

import grails.validation.ValidationException
import org.codehaus.groovy.grails.web.errors.GrailsExceptionResolver
import org.hibernate.ObjectNotFoundException
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.HttpRequestMethodNotSupportedException

class ErrorController {
    static responseFormats = ['json', 'xml']

    def utilsService;

    static class ErrorResult {
        CharSequence code;
        CharSequence message;
        def details;
    }

    def internal() {
        try {
            // 取得最内层的异常
            Throwable exception = GrailsExceptionResolver.getRootCause(request.exception);
            HttpStatus status;
            def error = new ErrorResult();
            error.code = exception.class.simpleName.replaceAll(/Exception$/, '');
            error.message = exception.localizedMessage;
            switch (exception) {
                case BadCredentialsException:
                    // 登录失败返回401
                    status = HttpStatus.UNAUTHORIZED;
                    break;
                case HttpRequestMethodNotSupportedException:
                    // 不支持的方法返回405
                    status = HttpStatus.METHOD_NOT_ALLOWED;
                    error.details = [methods: (exception as HttpRequestMethodNotSupportedException).supportedMethods];
                    break;
                case OptimisticLockingFailureException:
                    // 乐观锁冲突返回409
                    status = HttpStatus.CONFLICT;
                    break;
                case ValidationException:
                    // 校验错返回422
                    status = HttpStatus.UNPROCESSABLE_ENTITY;
                    def e = exception as ValidationException;
                    error.message = g.renderErrors(bean: e);
                    error.details = utilsService.marshallErrors(e.errors);
                    break;
                case ObjectNotFoundException:
                    status = HttpStatus.NOT_FOUND;
                    def e = exception as ObjectNotFoundException;
                    error.details = [id: e.identifier, entityName: e.entityName];
                    break;
                default:
                    // 其他返回500
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                    break;
            }
            respond(error, [status: status]);
        } catch (Exception e) {
            render(status: 500, text: e.localizedMessage);
        }
    }
}
