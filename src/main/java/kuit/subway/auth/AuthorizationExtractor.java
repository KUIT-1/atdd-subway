package kuit.subway.auth;

import jakarta.servlet.http.HttpServletRequest;
import kuit.subway.global.exception.SubwayException;
import lombok.NoArgsConstructor;

import java.util.Enumeration;

import static kuit.subway.global.exception.CustomExceptionStatus.INVALID_TOKEN_HEADER;
import static kuit.subway.global.exception.CustomExceptionStatus.INVALID_TOKEN_TYPE;

@NoArgsConstructor
public class AuthorizationExtractor {

    private static final String AUTHENTICATION_TYPE = "Bearer";
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final int START_TOKEN_INDEX = 6;

    public static String extractAccessToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION_HEADER_KEY);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.toLowerCase().startsWith(AUTHENTICATION_TYPE.toLowerCase())) {
                String extractToken = value.trim().substring(START_TOKEN_INDEX);
                validateExtractToken(extractToken);
                return extractToken;
            }
        }
        throw new SubwayException(INVALID_TOKEN_TYPE);
    }

    private static void validateExtractToken(String extractToken) {
        if (extractToken.isBlank()) {
            throw new SubwayException(INVALID_TOKEN_HEADER);
        }
    }
}
