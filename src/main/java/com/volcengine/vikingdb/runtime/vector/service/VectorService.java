// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.vikingdb.runtime.core.ApiClient;
import com.volcengine.vikingdb.runtime.core.RequestAddition;
import com.volcengine.vikingdb.runtime.core.auth.Auth;
import com.volcengine.vikingdb.runtime.core.ClientConfig;
import com.volcengine.vikingdb.runtime.core.ResponseContext;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.exception.ApiClientException;
import com.volcengine.vikingdb.runtime.exception.VectorApiException;
import com.volcengine.vikingdb.runtime.vector.api.ApiInfo;
import com.volcengine.vikingdb.runtime.vector.api.ApiRequestBinding;
import com.volcengine.vikingdb.runtime.vector.model.request.*;
import com.volcengine.vikingdb.runtime.vector.model.response.*;


/**
 * General-purpose service for interacting with the VikingDB vector database.
 * This service provides methods for managing and searching data across all collections and indexes.
 * It encapsulates the underlying API calls for operations like upsert, delete, search, etc.
 */
public class VectorService {

    protected final ApiClient apiClient;

    private static boolean isServiceConnectable = false;

    /**
     * Constructs a new VectorService with the specified host and authentication.
     *
     * @param host The hostname or IP address of the VikingDB service.
     * @param auth The authentication credentials.
     */
    public VectorService(String host, Auth auth) throws ApiClientException, VectorApiException {
        apiClient = new ApiClient(host, auth);
        checkServiceConnectivity();
    }

    /**
     * Constructs a new VectorService with the specified scheme, host, and authentication.
     *
     * @param scheme The HTTP scheme (HTTP or HTTPS).
     * @param host   The hostname or IP address of the VikingDB service.
     * @param auth   The authentication info.
     */
    public VectorService(Scheme scheme, String host, Auth auth) throws ApiClientException, VectorApiException {
        apiClient = new ApiClient(scheme, host, null, auth);
        checkServiceConnectivity();
    }

    /**
     * Constructs a new VectorService with the specified scheme, host, region, and authentication.
     *
     * @param scheme The HTTP scheme (HTTP or HTTPS).
     * @param host   The hostname or IP address of the VikingDB service.
     * @param region The service region.
     * @param auth   The authentication info.
     */
    public VectorService(Scheme scheme, String host, String region, Auth auth) throws ApiClientException, VectorApiException {
        apiClient = new ApiClient(scheme, host, region, auth);
        checkServiceConnectivity();
    }

    /**
     * Constructs a new VectorService with a custom HttpClient.
     *
     * @param scheme           The HTTP scheme (HTTP or HTTPS).
     * @param host             The hostname or IP address of the VikingDB service.
     * @param region           The service region.
     * @param auth             The authentication info.
     * @param clientConfig     A custom clientConfig.
     */
    public VectorService(Scheme scheme, String host, String region, Auth auth, ClientConfig clientConfig)
            throws ApiClientException, VectorApiException {
        apiClient = new ApiClient(scheme, host, region, auth, clientConfig);
        checkServiceConnectivity();
    }

    /**
     * Checks the connectivity to the VikingDB service.
     * Throws an exception if the service is unreachable or returns an error status.
     */
    private synchronized void checkServiceConnectivity() throws ApiClientException, VectorApiException {
        if (!isServiceConnectable) {
            this.ping();
            isServiceConnectable = true;
        }
    }

    public ResponseContext ping() throws ApiClientException, VectorApiException {
        return ping(null);
    }

    public ResponseContext ping(RequestAddition addition) throws ApiClientException, VectorApiException {
        return ApiRequestBinding.ping(apiClient, addition);
    }

    /**
     * Inserts or updates data in a collection.
     *
     * @param request The request object containing the data to be upserted.
     * @return An API response containing the result of the upsert operation.
     */
    public DataApiResponse<UpsertDataResult> upsertData(UpsertDataRequest request)
            throws ApiClientException, VectorApiException {
        return upsertData(request, null);
    }

    public DataApiResponse<UpsertDataResult> upsertData(UpsertDataRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.UPSERT_DATA,
                request,
                new TypeReference<DataApiResponse<UpsertDataResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Updates existing data in a collection.
     *
     * @param request The request object containing the data to be updated.
     * @return An API response containing the result of the update operation.
     */
    public DataApiResponse<UpdateDataResult> updateData(UpdateDataRequest request)
            throws ApiClientException, VectorApiException {
        return updateData(request, null);
    }

    public DataApiResponse<UpdateDataResult> updateData(UpdateDataRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.UPDATE_DATA,
                request,
                new TypeReference<DataApiResponse<UpdateDataResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Fetches data from a collection by primary key.
     *
     * @param request The request object containing the IDs of the data to fetch.
     * @return An API response containing the fetched data.
     */
    public DataApiResponse<FetchDataInCollectionResult> fetchDataInCollection(FetchDataInCollectionRequest request)
            throws ApiClientException, VectorApiException {
        return fetchDataInCollection(request, null);
    }

    public DataApiResponse<FetchDataInCollectionResult> fetchDataInCollection(FetchDataInCollectionRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.FETCH_DATA_IN_COLLECTION,
                request,
                new TypeReference<DataApiResponse<FetchDataInCollectionResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Deletes data from a collection by primary key.
     *
     * @param request The request object containing the IDs of the data to delete.
     * @return An API response containing the result of the delete operation.
     */
    public DataApiResponse<DeleteDataResult> deleteData(DeleteDataRequest request)
            throws ApiClientException, VectorApiException {
        return deleteData(request, null);
    }

    public DataApiResponse<DeleteDataResult> deleteData(DeleteDataRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.DELETE_DATA,
                request,
                new TypeReference<DataApiResponse<DeleteDataResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Fetches data from an index by primary key.
     *
     * @param request The request object containing the IDs of the data to fetch from the index.
     * @return An API response containing the fetched data.
     */
    public DataApiResponse<FetchDataInIndexResult> fetchDataInIndex(FetchDataInIndexRequest request)
            throws ApiClientException, VectorApiException {
        return fetchDataInIndex(request, null);
    }

    public DataApiResponse<FetchDataInIndexResult> fetchDataInIndex(FetchDataInIndexRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.FETCH_DATA_IN_INDEX,
                request,
                new TypeReference<DataApiResponse<FetchDataInIndexResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Performs a vector-based similarity search.
     *
     * @param request The request object containing the query vectors and search parameters.
     * @return An API response containing the search results.
     */
    public DataApiResponse<SearchResult> searchByVector(SearchByVectorRequest request)
            throws ApiClientException, VectorApiException {
        return searchByVector(request, null);
    }

    public DataApiResponse<SearchResult> searchByVector(SearchByVectorRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.SEARCH_BY_VECTOR,
                request,
                new TypeReference<DataApiResponse<SearchResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Performs a multi-modal search.
     *
     * @param request The request object for the multi-modal search.
     * @return An API response containing the search results.
     */
    public DataApiResponse<SearchResult> searchByMultiModal(SearchByMultiModalRequest request)
            throws ApiClientException, VectorApiException {
        return searchByMultiModal(request, null);
    }

    public DataApiResponse<SearchResult> searchByMultiModal(SearchByMultiModalRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.SEARCH_BY_MULTIMODAL,
                request,
                new TypeReference<DataApiResponse<SearchResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Searches for data by primary key.
     *
     * @param request The request object containing the IDs to search for.
     * @return An API response containing the search results.
     */
    public DataApiResponse<SearchResult> searchById(SearchByIdRequest request)
            throws ApiClientException, VectorApiException {
        return searchById(request, null);
    }

    public DataApiResponse<SearchResult> searchById(SearchByIdRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.SEARCH_BY_ID,
                request,
                new TypeReference<DataApiResponse<SearchResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Performs a search based on scalar field ordering.
     *
     * @param request The request object containing the scalar ordering settings.
     * @return An API response containing the search results.
     */
    public DataApiResponse<SearchResult> searchByScalar(SearchByScalarRequest request)
            throws ApiClientException, VectorApiException {
        return searchByScalar(request, null);
    }

    public DataApiResponse<SearchResult> searchByScalar(SearchByScalarRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.SEARCH_BY_SCALAR,
                request,
                new TypeReference<DataApiResponse<SearchResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Performs a keywords-based search.
     *
     * @param request The request object containing the keywords and search parameters.
     * @return An API response containing the search results.
     */
    public DataApiResponse<SearchResult> searchByKeywords(SearchByKeywordsRequest request)
            throws ApiClientException, VectorApiException {
        return searchByKeywords(request, null);
    }

    public DataApiResponse<SearchResult> searchByKeywords(SearchByKeywordsRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.SEARCH_BY_KEYWORDS,
                request,
                new TypeReference<DataApiResponse<SearchResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Retrieves random data from the index.
     *
     * @param request The request object specifying random items to retrieve.
     * @return An API response containing the randomly selected data.
     */
    public DataApiResponse<SearchResult> searchByRandom(SearchByRandomRequest request)
            throws ApiClientException, VectorApiException {
        return searchByRandom(request, null);
    }

    public DataApiResponse<SearchResult> searchByRandom(SearchByRandomRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.SEARCH_BY_RANDOM,
                request,
                new TypeReference<DataApiResponse<SearchResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Performs an aggregation query on the data.
     *
     * @param request The request object specifying the aggregation query.
     * @return An API response containing the aggregation results.
     */
    public DataApiResponse<AggregateResult> aggregate(AggregateRequest request)
            throws ApiClientException, VectorApiException {
        return aggregate(request, null);
    }

    public DataApiResponse<AggregateResult> aggregate(AggregateRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.AGGREGATE,
                request,
                new TypeReference<DataApiResponse<AggregateResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Sorts data based on a specified query vector.
     *
     * @param request The request object specifying the sorting criteria.
     * @return An API response containing the sorted results.
     */
    public DataApiResponse<SortResult> sort(SortRequest request)
            throws ApiClientException, VectorApiException {
        return sort(request, null);
    }

    public DataApiResponse<SortResult> sort(SortRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.SORT,
                request,
                new TypeReference<DataApiResponse<SortResult>>() {}
        ).callApi(apiClient, addition);
    }

    /**
     * Generates embeddings for the given data inputs.
     *
     * @param request The request object containing the data to be embedded.
     * @return An API response containing the generated embeddings.
     */
    public DataApiResponse<EmbeddingResult> embedding(EmbeddingRequest request)
            throws ApiClientException, VectorApiException {
        return embedding(request, null);
    }

    public DataApiResponse<EmbeddingResult> embedding(EmbeddingRequest request, RequestAddition addition)
            throws ApiClientException, VectorApiException {
        return new ApiRequestBinding<>(
                ApiInfo.Embedding,
                request,
                new TypeReference<DataApiResponse<EmbeddingResult>>() {}
        ).callApi(apiClient, addition);
    }

}
