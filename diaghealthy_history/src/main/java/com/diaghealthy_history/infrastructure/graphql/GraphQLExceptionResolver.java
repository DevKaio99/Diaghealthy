package com.diaghealthy_history.infrastructure.graphql;

import com.diaghealthy_history.application.exceptions.ResourceNotFoundException;
import com.diaghealthy_history.application.exceptions.UnauthorizedException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class GraphQLExceptionResolver extends DataFetcherExceptionResolverAdapter {

    private static final Logger log = LoggerFactory.getLogger(GraphQLExceptionResolver.class);

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment environment) {

        if (ex instanceof ResourceNotFoundException) {
            return buildError(ex, environment, ErrorType.NOT_FOUND);
        }

        if (ex instanceof UnauthorizedException) {
            return buildError(ex, environment, ErrorType.FORBIDDEN);
        }

        if (ex instanceof IllegalArgumentException) {
            return buildError(ex, environment, ErrorType.BAD_REQUEST);
        }

        log.error("Erro inesperado ao resolver campo {}", environment.getField().getName(), ex);

        return buildError(new RuntimeException("Ocorreu um erro inesperado. Tente novamente mais tarde."), environment, ErrorType.INTERNAL_ERROR);
    }

    private GraphQLError buildError(Throwable ex, DataFetchingEnvironment environment, ErrorType errorType) {
        return GraphqlErrorBuilder.newError(environment)
                .message(ex.getMessage())
                .errorType(errorType)
                .build();
    }
}
