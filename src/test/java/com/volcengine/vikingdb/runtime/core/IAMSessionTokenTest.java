package com.volcengine.vikingdb.runtime.core;

import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.enums.Method;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.HttpVersion;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class IAMSessionTokenTest {
    @Test
    public void signsRegularAndStreamingRequestsWithOptionalToken() throws Exception {
        for (String token : new String[]{null, "", "temporary-session-token"}) {
            for (boolean streaming : new boolean[]{false, true}) {
                HttpClient httpClient = mock(HttpClient.class);
                HttpResponse response = new BasicHttpResponse(
                        new BasicStatusLine(HttpVersion.HTTP_1_1, 200, "OK"));
                response.setEntity(new StringEntity(streaming ? "data: [DONE]\n\n" : "{}"));
                when(httpClient.execute(any(HttpUriRequest.class))).thenReturn(response);
                ClientConfig config = ClientConfig.builder().httpClient(httpClient).build();
                AuthWithAkSk auth = token == null
                        ? new AuthWithAkSk("ak", "sk")
                        : new AuthWithAkSk("ak", "sk", token);

                if (streaming) {
                    KnowledgeService service = new KnowledgeService(
                            Scheme.HTTPS, "example.test", "cn-beijing", auth, config);
                    service.chatCompletionStream(null).close();
                } else {
                    ApiClient client = new ApiClient(
                            Scheme.HTTPS, "example.test", "cn-beijing", auth, config);
                    client.doRequest(Method.POST, "/test", "{}");
                }

                ArgumentCaptor<HttpUriRequest> captor = ArgumentCaptor.forClass(HttpUriRequest.class);
                verify(httpClient).execute(captor.capture());
                HttpUriRequest request = captor.getValue();
                String authorization = request.getFirstHeader("Authorization").getValue();
                assertTrue(authorization.contains(
                        "/cn-beijing/" + (streaming ? "air" : "vikingdb") + "/request"));
                boolean hasToken = token != null && !token.isEmpty();
                assertEquals(hasToken, authorization.toLowerCase().contains("x-security-token"));
                if (hasToken) {
                    assertEquals(token, request.getFirstHeader("X-Security-Token").getValue());
                } else {
                    assertNull(request.getFirstHeader("X-Security-Token"));
                }
            }
        }
    }
}
