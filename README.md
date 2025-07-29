# VikingDB Java SDK

The VikingDB Java SDK provides a convenient and easy-to-use interface for Java developers to interact with the ByteDance VikingDB (Vector Database) service. It encapsulates the underlying HTTP API calls, simplifying operations on collections, indexes, and vector data.

## 1. Project Introduction

-   **What is VikingDB?** VikingDB is a high-performance, distributed vector database service designed for large-scale similarity search.
-   **What can this SDK do?** This SDK allows you to programmatically manage your VikingDB resources and perform data operations, including creating collections, managing indexes, upserting vector data, and executing various types of search queries.
-   **Key Features:**
    -   Fluent, intuitive API for all VikingDB operations.
    -   Support for multiple authentication methods (AK/SK, API Key).
    -   Built-in handling of HTTP requests, responses, and error conditions.
    -   Typed request and response models for improved development experience.

## 2. Requirements

-   Java Development Kit (JDK) 1.8 or later.
-   Maven 3.x or compatible build tools.

## 3. Installation

To use the SDK in your Maven project, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.volcengine</groupId>
    <artifactId>vikingdb-java-sdk</artifactId>
    <version>0.0.1</version> <!-- Please check for the latest version -->
</dependency>
```

## 4. Quick Start

Here is a simple example demonstrating the main workflow:

```java
import com.volcengine.vikingdb.runtime.core.auth.Auth;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.vector.service.VectorService;
import com.volcengine.vikingdb.runtime.vector.model.request.*;
import com.volcengine.vikingdb.runtime.vector.model.response.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class QuickStartExample {
    public static void main(String[] args) throws Exception {
        // 1. Initialize the service
        Auth auth = new AuthWithAkSk("YOUR_AK", "YOUR_SK");
        VectorService service = new VectorService("your-vikingdb-host", auth);

        // 2. Create a collection (This is an example, actual collection creation is done via other APIs)
        String collectionName = "my_collection";
        // Assume collection is created.

        // 3. Upsert data into the collection
        UpsertDataRequest request = UpsertDataRequest.builder().collectionName(collectionName).data(Collections.singletonList(new HashMap<String, Object>() {{
            put("f_id", 1);
            put("f_vector", Arrays.asList(0.1, 0.2, 0.3, 0.4));
        }})).build();
        DataApiResponse<UpsertDataResult> upsertResponse = service.upsertData(request);
        System.out.println("Upsert Result: " + upsertResponse.getResult());

        // 4. Perform a vector fetch
        FetchDataInCollectionRequest fetchDataInCollection = FetchDataInCollectionRequest.builder().collectionName(collectionName).ids(Collections.singletonList(1)).build();
        DataApiResponse<FetchDataInCollectionResult> fetchResult = service.fetchDataInCollection(fetchDataInCollection);
        System.out.println("FetchDataInCollection Result: " + fetchResult.getResult());
    }
}
```

## 5. Authentication

Initialize the `VectorService` with your credentials.

### AK/SK Authentication

```java

import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.vector.service.VectorService;

com.volcengine.vikingdb.runtime.core.auth.Auth auth = new AuthWithAkSk("YOUR_ACCESS_KEY_ID", "YOUR_SECRET_ACCESS_KEY");
VectorService service = new VectorService("your-vikingdb-host", auth);
```

### API Key Authentication

(Note: The SDK structure suggests API Key auth is possible, but a concrete `AuthWithApiKey` class might be needed.)

## 6. API Reference

### Collection Data Operations

-   `upsertData()`
-   `updateData()`
-   `deleteData()`
-   `fetchDataInCollection()`

### Index Data Operations

-   `searchByVector()`
-   `searchByMultiModal()`
-   `searchById()`
-   `searchByScalar()`
-   `searchByKeywords()`
-   `searchByRandom()`
-   `fetchDataInIndex()`
-   `aggregate()`

### Embedding Operations

-   `embedding()`

### Error Handling

The SDK throws exceptions for network issues or API errors. It's recommended to wrap SDK calls in a try-catch block.


## 7. Contributing

We welcome contributions! Please follow standard practices like creating pull requests for new features or bug fixes.

## 8. License

This SDK is licensed under the Apache 2.0 License.