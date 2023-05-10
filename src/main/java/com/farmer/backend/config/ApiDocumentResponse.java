package com.farmer.backend.config;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.*;

import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.web.client.HttpClientErrorException.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ApiResponses(value = {

        @ApiResponse(responseCode = "200", description = "API 호출 성공"),

        @ApiResponse(
                responseCode = "404",
                description = "존재하지 않는 API",
                content = @Content(schema = @Schema(implementation = NotFound.class))),

        @ApiResponse(
                responseCode = "400",
                description = "올바르지 않는 요청",
                content = @Content(schema = @Schema(implementation = BadRequest.class))),

        @ApiResponse(
                responseCode = "405",
                description = "잘못된 Method 요청",
                content = @Content(schema = @Schema(implementation = MethodNotAllowed.class))),

        @ApiResponse(
                responseCode = "401",
                description = "인증 실패",
                content = @Content(schema = @Schema(implementation = Unauthorized.class))),


        @ApiResponse(
                responseCode = "403",
                description = "인가 실패(권한 없음)",
                content = @Content(schema = @Schema(implementation = Forbidden.class))),

        @ApiResponse(
                responseCode = "409",
                description = "데이터 등록 실패",
                content = @Content(schema = @Schema(implementation = Conflict.class))),

})

public @interface ApiDocumentResponse {
}
