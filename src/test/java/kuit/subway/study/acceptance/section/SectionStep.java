package kuit.subway.study.acceptance.section;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import kuit.subway.dto.request.CreateSectionRequest;

import static kuit.subway.study.acceptance.AcceptanceUtils.post;

public class SectionStep {
    private static final String LINE_PATH = "/lines";
    private static final String SECTION_PATH = "/sections";

    public static ExtractableResponse<Response> 지하철_구간_생성하기(CreateSectionRequest body, Long id) {
        return post(LINE_PATH + "/" + id + SECTION_PATH, body);
    }
}