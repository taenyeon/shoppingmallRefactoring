package com.shop.myapp.service;

import com.shop.myapp.dto.Payment;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@Transactional(rollbackFor = {Exception.class})
public class IamPortService {

    public JSONObject parsingRestAttribute(ResponseEntity<String> restResponse) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject responseBody = (JSONObject) parser.parse(restResponse.getBody());
        validRefundCode(restResponse);
        // ResponseEntity 에 저장되어 있는 body(json 형태로 된 String)을 JSONObject 로 파싱
        // body 안에 json 형태의 String 으로 저장되어 있는 response(실제 저장 값들)를 JSONObject 로 다시 파싱
        return (JSONObject) responseBody.get("response");
    }

    public void validRefundCode(ResponseEntity<String> restResponse) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject responseBody = (JSONObject) parser.parse(restResponse.getBody());
        long code = (long) responseBody.get("code");
        String message = (String) responseBody.get("message");
        if (code == -1){
            throw new IllegalStateException(message);
        }
    }

    public String getAccessToken() throws ParseException {

        // map에 저장된 결제 정보를 꺼내 db에 저장되어있는 주문(order)과 비교하여
        // 민약 값이 맞으면 status 200 으로 보내고 값이 틀리면 status 400으로 보냄.

        // Spring에서 외부 api로 http 요청을 보내기 위해 RestTemplate 사용
        RestTemplate template = new RestTemplate();

        // 호출할 url
        String url = "https://api.iamport.kr/users/getToken";

        // http body 에 담을 값을 Map 에 저장
        // linked 를 사용하면 값들의 순서를 보장할 수 있음
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("imp_key", "{키 번호}");
        // import secret key
        map.add("imp_secret", "{시크릿 번호}");
        // RestTemplate 를 사용하여 url 에 request 요청 -> 이후 response 를 ResponseEntity 에 저장

        ResponseEntity<String> response = template.postForEntity(url, map, String.class);
        JSONObject responseAttributes = parsingRestAttribute(response);

        // key 와 secret key 를 통해 발급받은 access token 을 꺼내 String 으로 저장
        // 이후, import 에 요청할떄, http header 에 삽입하여 같이 보냄
        // access token 이 있어야 import 에 값을 요청할 수 있으며
        // access token 의 유효 시간이 30분이기때문에 일단 결제 요청시마다 계속 받는 형태로 구현했다.
        String accessToken = (String) responseAttributes.get("access_token");
        log.info("access_token : {}", accessToken);
        return accessToken;
    }

    public Payment getImpAttributes(String impUid) throws ParseException {
        // access token 을 전달할 httpHeaders 생성
        HttpHeaders headers = new HttpHeaders();
        // IamPort 서버에서 access token 응답 받아, 해당 요청의 http header 에 추가
        headers.add("Authorization", getAccessToken());
        // httpEntity 에 headers 추가
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        // 결제 코드로 결제 정보 불러옴
        String url = "https://api.iamport.kr/payments/" + impUid;
        // exchange()를 사용하면 postForEntity()에 비해 더 많은 정보를 전달할 수 있음
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, entity, String.class);
        JSONObject responseAttributes = parsingRestAttribute(response);
        // ResponseEntity 에 저장된 응답 메세지에 담긴 http body 를 JSONObject 형태로 파싱

        // 해당 결제 정보를 builder 를 통해 전달.
        return Payment.builder()
                .impUid((String) responseAttributes.get("imp_uid"))
                .amount((Long) responseAttributes.get("amount"))
                .buyerName((String) responseAttributes.get("buyer_name"))
                .buyerTel((String) responseAttributes.get("buyer_tel"))
                .buyerPostCode((String) responseAttributes.get("buyer_postcode"))
                .buyerAddr((String) responseAttributes.get("buyer_addr"))
                .buyerEmail((String) responseAttributes.get("buyer_email"))
                .name((String) responseAttributes.get("name"))
                .paidAt((long) responseAttributes.get("paid_at"))
                .build();
    }

    public long cancel(String refundDetail) throws ParseException {
        HttpHeaders headers = new HttpHeaders();
        // json 타입으로 보내기 위해서 contentType 선언.
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 응답으로 json 타입으로 받기 위해 accept 선언.
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // 헤더에 응답으로 받아놓은 accessToken 을 삽입.
        headers.add("Authorization", getAccessToken());
        // 환불 금액을 요청에 담음.
        HttpEntity<String> entity = new HttpEntity<>(refundDetail, headers);
        // 요청을 보낼 url 지정.
        String url = "https://api.iamport.kr/payments/cancel";
        // 요청을 보내기위해 RestTemplate 사용.
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, entity, String.class);

        JSONObject responseAttributes = parsingRestAttribute(response);
//        if (statusCode.is4xxClientError()){
//            throw new IllegalStateException("");
//        }
        // ResponseEntity 에 담겨있는 Http Body 를 JsonObject 에 저장.
        // jsonObject 에 저장되어 있는 환불 금액 리턴.
        return (long) responseAttributes.get("cancel_amount");
    }
}
