package chatBot;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class GPTService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "";

    public String callGPT(String prompt) {
        String responseStr = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // HTTP POST 요청 생성
            HttpPost request = new HttpPost(API_URL);
            request.setHeader("Authorization", "Bearer " + API_KEY);
            request.setHeader("Content-Type", "application/json");

            // JSON 요청 본문 생성
            JSONObject json = new JSONObject();
            json.put("model", "gpt-4o-mini");
            json.put("temperature", 0.2);
            json.put("max_tokens", 300);
            json.put("top_p", 1.0);

            // 메시지 구성
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", "You are a helpful assistant for course registration. Respond in Korean. Only use the provided information to answer questions. If the query is unrelated or beyond the provided details, respond with: '명지대학교 교학팀 02-300-1700으로 문의주세요.'");

            JSONObject courseInfoMessage = new JSONObject();
            courseInfoMessage.put("role", "system");
            courseInfoMessage.put("content", "안녕하세요! 수강신청을 도와드리겠습니다. 다음은 수강신청 절차와 관련된 상세한 설명입니다:\n\n"
                + "1. 수강신청 기간은 2024년 12월 11일 09시부터 17시까지입니다. 이 기간 동안만 신청이 가능합니다.\n"
                + "2. 수강신청을 시작하려면 먼저 로그인을 해야 합니다.\n"
                + "3. 만약 회원가입이 되어 있지 않다면 회원가입을 먼저 진행해주세요.\n"
                + "4. 회원가입 시 비밀번호는 8자리 이상으로 설정해야 합니다.\n"
                + "5. 회원가입 후 로그인을 완료하면 수강신청 페이지에 접근할 수 있습니다.\n"
                + "6. 원하는 과목을 선택한 뒤, 과목 저장 버튼을 누르면 해당 과목이 미리담기에 추가됩니다.\n"
                + "7. 미리담기된 과목을 최종적으로 신청하려면 과목 저장 버튼을 눌러 수강신청을 완료할 수 있습니다.\n"
                + "8. 과목을 삭제하고 싶다면 과목 삭제 버튼을 눌러주세요.\n"
                + "9. 로그인 화면에서는 '미리담기 내역'과 '수강신청 내역'을 조회할 수 있습니다.\n");

            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);

            json.put("messages", new org.json.JSONArray().put(systemMessage).put(courseInfoMessage).put(userMessage));

            // 요청 본문 설정
            StringEntity entity = new StringEntity(json.toString(), "UTF-8");
            request.setEntity(entity);

            // 요청 실행 및 응답 처리
            org.apache.http.HttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                responseStr = EntityUtils.toString(responseEntity);
                System.out.println("응답 내용: " + responseStr);

                // JSON 파싱
                JSONObject jsonResponse = new JSONObject(responseStr);
                if (jsonResponse.has("choices")) {
                    return jsonResponse.getJSONArray("choices")
                                       .getJSONObject(0)
                                       .getJSONObject("message")
                                       .getString("content");
                } else {
                    return "Error: 'choices' key not found in the response. 응답: " + responseStr;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Exception occurred. " + e.getMessage();
        }
        return responseStr;
    }
}