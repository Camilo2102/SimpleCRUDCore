package cloud.webgen.web.crud.core.infrastructure.outbound.documentation.adapter;

import cloud.webgen.web.crud.core.domain.enums.SimpleCRUDMethods;
import cloud.webgen.web.crud.core.domain.ports.DocumentationService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.PathParameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class SwaggerDocumentImpl implements DocumentationService {

    private final OpenAPI openAPI;

    @Override
    public void generateDocumentation(SimpleCRUDMethods method, String path, Class<?> clazz) {
        String fullPath = "/api/v2/" + path.toLowerCase();
        switch (method) {
            case CREATE:
                createPathForPost(fullPath, clazz);
                break;
            case GET_ALL:
                createPathForGetAll(fullPath);
                break;
            case GET_BY_ID:
                createPathForGetOne(fullPath + "/{id}");
                break;
            case GET_ALL_PAGED:
                createPathForGetAllPaged("/api/v2/paged/" + path);
                break;
            case UPDATE:
                createPathForPut(fullPath + "/{id}", clazz);
                break;
            case DELETE:
                createPathForDelete(fullPath + "/{id}");
                break;
        }
    }

    @Override
    public void createPathForGetAll(String path) {
        PathItem pathItem = openAPI.getPaths().getOrDefault(path, new PathItem());
        Operation getOperation = new Operation()
                .summary("Retrieves all the items for " + path)
                .responses(standardApiResponse("Array of data of " + path, "array", "200"));
        pathItem.setGet(getOperation);
        addPathItemToOpenAPI(path, pathItem);
    }

    @Override
    public void createPathForGetOne(String path) {
        PathItem pathItem = openAPI.getPaths().getOrDefault(path, new PathItem());
        Operation getOperation = new Operation()
                .summary("Retrieves the information for one item by id for " + path)
                .responses(standardApiResponse("Single data of " + path, "object", "200"))
                .addParametersItem(new PathParameter()
                        .name("id")
                        .description("Id to search of " + path)
                        .required(true)
                        .schema(new Schema<>().type("string")));
        pathItem.setGet(getOperation);
        addPathItemToOpenAPI(path, pathItem);
    }

    @Override
    public void createPathForGetAllPaged(String path) {
        PathItem pathItem = openAPI.getPaths().getOrDefault(path, new PathItem());
        Operation getOperation = new Operation()
                .summary("Retrieves all the items paged for " + path)
                .responses(standardApiResponse("Array of data of " + path, "array", "200"))
                .addParametersItem(new QueryParameter()
                        .name("page")
                        .description("The page number")
                        .required(false)
                        .schema(new Schema<>().type("integer")))
                .addParametersItem(new QueryParameter()
                        .name("size")
                        .description("The page size")
                        .required(false)
                        .schema(new Schema<>().type("integer")));
        pathItem.setGet(getOperation);
        addPathItemToOpenAPI(path, pathItem);
    }

    @Override
    public void createPathForPost(String path, Class<?> clazz) {
        PathItem pathItem = openAPI.getPaths().getOrDefault(path, new PathItem());
        Operation postOperation = new Operation()
                .summary("Creates an item for " + path)
                .responses(standardApiResponse("Data created for " + path, "object", "201"))
                .requestBody(new RequestBody()
                        .description("Item to be created")
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(generateSchemaFromClass(clazz)))));
        pathItem.setPost(postOperation);
        addPathItemToOpenAPI(path, pathItem);
    }

    @Override
    public void createPathForPut(String path, Class<?> clazz) {
        PathItem pathItem = openAPI.getPaths().getOrDefault(path, new PathItem());
        Operation putOperation = new Operation()
                .summary("Updates an item for " + path)
                .responses(standardApiResponse("Data updated for " + path, "object", "200"))
                .requestBody(new RequestBody()
                        .description("Item to be updated")
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(generateSchemaFromClass(clazz)))));
        pathItem.setPut(putOperation);
        addPathItemToOpenAPI(path, pathItem);
    }

    @Override
    public void createPathForDelete(String path) {
        PathItem pathItem = openAPI.getPaths().getOrDefault(path, new PathItem());
        Operation deleteOperation = new Operation()
                .summary("Delete one item by id for " + path)
                .responses(standardApiResponse("Delete data of " + path, "object", "200"))
                .addParametersItem(new PathParameter()
                        .name("id")
                        .description("Id to delete of " + path)
                        .required(true)
                        .schema(new Schema<>().type("string")));
        pathItem.setDelete(deleteOperation);
        addPathItemToOpenAPI(path, pathItem);
    }

    private void addPathItemToOpenAPI(String path, PathItem pathItem) {
        Paths paths = openAPI.getPaths();
        paths.addPathItem(path, pathItem);
    }

    private ApiResponses standardApiResponse(String response, String type, String status) {
        ApiResponses apiResponses = new ApiResponses();
        ApiResponse apiResponse = new ApiResponse()
                .description(response)
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>().type(type))));
        apiResponses.addApiResponse(status, apiResponse);
        return apiResponses;
    }

    public static Schema<?> generateSchemaFromClass(Class<?> clazz) {
        ObjectSchema objectSchema = new ObjectSchema();

        Map<String, Schema<?>> properties = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            Schema<?> schema = createSchemaForField(field);
            properties.put(field.getName(), schema);
        }
        objectSchema.setProperties(Collections.unmodifiableMap(properties));

        return objectSchema;
    }

    private static Schema<?> createSchemaForField(Field field) {
        Schema<?> schema;

        // Infer the type of the field
        Class<?> fieldType = field.getType();
        if (fieldType.equals(String.class)) {
            schema = new StringSchema();
        } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
            schema = new IntegerSchema();
        } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
            schema = new BooleanSchema();
        } else if (fieldType.equals(double.class) || fieldType.equals(Double.class) ||
                fieldType.equals(float.class) || fieldType.equals(Float.class)) {
            schema = new NumberSchema();
        } else {
            schema = new Schema<>(); // Default schema if type is not recognized
        }

        return schema;
    }
}