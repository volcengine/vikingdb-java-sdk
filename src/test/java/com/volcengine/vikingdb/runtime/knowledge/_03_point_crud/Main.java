package com.volcengine.vikingdb.runtime.knowledge._03_point_crud;

import com.volcengine.vikingdb.runtime.core.RequestAddition;
import com.volcengine.vikingdb.runtime.knowledge.model.request.AddPointRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.request.DeletePointRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.request.ListPointsRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.request.UpdatePointRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.response.BaseResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.ListPointsResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.PointAddResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.PointInfo;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeCollectionClient;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeService;
import com.volcengine.vikingdb.runtime.knowledge.util.ExampleUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        KnowledgeService service = ExampleUtil.newKnowledgeService(ExampleUtil.preferAuth());
        KnowledgeCollectionClient kc = service.collection(ExampleUtil.defaultCollectionMeta());

        String docId = ExampleUtil.envOrDefault("VIKING_DOC_ID", "google-report-2025-q1");

        Map<String, Object> field1 = new HashMap<>();
        field1.put("field_name", "topic");
        field1.put("field_type", "string");
        field1.put("field_value", "revenue");

        Map<String, Object> field2 = new HashMap<>();
        field2.put("field_name", "year");
        field2.put("field_type", "int64");
        field2.put("field_value", 2025);

        Map<String, Object> field3 = new HashMap<>();
        field3.put("field_name", "quarter");
        field3.put("field_type", "string");
        field3.put("field_value", "Q1");

        AddPointRequest addPointReq = AddPointRequest.builder()
                .docId(docId)
                .chunkType("text")
                .chunkTitle("Revenue Highlights")
                .content("Revenue grew 12% YoY to $3.4B.")
                .fields(Arrays.asList(field1, field2, field3))
                .build();

        PointAddResponse addPointResp = kc.addPoint(addPointReq, new RequestAddition());
        ExampleUtil.printJson("add_point", addPointResp);

        String pointId = addPointResp != null && addPointResp.getData() != null ? addPointResp.getData().getPointId() : null;
        if (pointId == null || pointId.isEmpty()) {
            System.out.println("point_id_empty");
            return;
        }

        PointInfo pointInfo = kc.getPoint(pointId, true, new RequestAddition());
        ExampleUtil.printJson("get_point", pointInfo);

        UpdatePointRequest updatePointReq = UpdatePointRequest.builder()
                .chunkTitle("Revenue Highlights (Updated)")
                .content("Revenue grew 12% YoY to $3.4B. Updated.")
                .build();
        BaseResponse updatePointResp = kc.updatePoint(pointId, updatePointReq, new RequestAddition());
        ExampleUtil.printJson("update_point", updatePointResp);

        ListPointsRequest listReq = ListPointsRequest.builder().offset(0).limit(10).getAttachmentLink(true).build();
        ListPointsResponse listResp = kc.listPoints(listReq, new RequestAddition());
        ExampleUtil.printJson("list_points", listResp);

        BaseResponse deletePointResp = kc.deletePoint(DeletePointRequest.builder().pointId(pointId).build(),
                new RequestAddition());
        ExampleUtil.printJson("delete_point", deletePointResp);
    }
}

