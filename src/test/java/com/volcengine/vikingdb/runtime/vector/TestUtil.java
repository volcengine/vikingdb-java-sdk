package com.volcengine.vikingdb.runtime.vector;

import com.volcengine.vikingdb.runtime.Version;
import com.volcengine.vikingdb.runtime.vector.model.response.*;
import org.junit.jupiter.api.TestInfo;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.IntStream;

public class TestUtil {
    public static void printResponse(DataApiResponse response) {
        System.out.printf("RequestId: %s\n", response.getRequestId());
        System.out.printf("Code: %s\n", response.getCode());
        System.out.printf("Message: %s\n", response.getMessage());
        System.out.printf("Result: %s\n", response.getResult());
    }

    public static void printFetchCollectionResult(DataApiResponse<FetchDataInCollectionResult> response) {
        printResponse(response);
        FetchDataInCollectionResult result = response.getResult();
        System.out.println("FetchResult:");
        System.out.println("Fetch:");
        IntStream.range(0, Objects.requireNonNull(result).getFetch().size())
                .forEach(i -> {
                    FetchInCollectionItem item = result.getFetch().get(i);
                    System.out.printf("[%d] Id: %s, Fields: %s\n", i + 1, item.getId(), item.getFields());
                });
        System.out.println("IdsNotExist:");
        IntStream.range(0, Objects.requireNonNull(result).getIdsNotExist().size())
                .forEach(i -> {
                    System.out.printf("[%d] Id: %s\n", i + 1, result.getIdsNotExist().get(i));
                });
    }

    public static void printFetchIndexResult(DataApiResponse<FetchDataInIndexResult> response) {
        printResponse(response);
        FetchDataInIndexResult result = response.getResult();
        System.out.println("FetchResult:");
        System.out.println("Fetch:");
        IntStream.range(0, Objects.requireNonNull(result).getFetch().size())
                .forEach(i -> {
                    FetchInIndexItem item = result.getFetch().get(i);
                    System.out.printf("[%d] Id: %s, Fields: %s, DenseDim: %d, DenseVector: %s\n",
                            i + 1, item.getId(), item.getFields(), item.getDenseDim(), item.getDenseVector());
                });
        System.out.println("IdsNotExist:");
        IntStream.range(0, Objects.requireNonNull(result).getIdsNotExist().size())
                .forEach(i -> System.out.printf("[%d] Id: %s\n", i + 1, result.getIdsNotExist().get(i)));
    }

    public static void printSearchResult(DataApiResponse<SearchResult> response) {
        printResponse(response);
        SearchResult result = response.getResult();
        System.out.println("SearchResult:");
        System.out.println("Data:");
        IntStream.range(0, Objects.requireNonNull(result).getData().size())
                .forEach(i -> {
                    SearchItem item = result.getData().get(i);
                    System.out.printf("[%d] Id: %s, Score: %f, AnnScore: %f, Fields: %s\n",
                            i + 1, item.getId(), item.getScore(), item.getAnnScore(), item.getFields());
                });
        System.out.printf("FilterMatchedCount: %d\n", result.getFilterMatchedCount());
        System.out.printf("TotalReturnCount: %d\n", result.getTotalReturnCount());
        System.out.printf("RealTextQuery: %s\n", result.getRealTextQuery());
        System.out.printf("TokenUsage: %s\n", result.getTokenUsage());
    }

    public static String genUtRequestId(TestInfo testInfo) {
        return "vikingdb-java-sdk/" + Version.getVersion() + "__" +
                testInfo.getTestMethod().map(Method::getName).orElse("UnknownMethod") + "__" +
                System.currentTimeMillis();
    }
}
