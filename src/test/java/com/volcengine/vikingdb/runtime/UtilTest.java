// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime;

import org.junit.jupiter.api.Test;

import java.util.List;

public class UtilTest {

    @Test
    public void testDistance() {
        int dim = 2048;
        List<Float> vector1 = Util.generateRandomVector(dim);
        List<Float> vector2 = Util.generateRandomVector(dim);

        float cosineDistance = Util.getCosineDistance(vector1, vector2);
        float ipDistance = Util.getInnerProductDistance(vector1, vector2);
        float l2Distance = Util.getL2Distance(vector1, vector2);

        System.out.println(cosineDistance);
        System.out.println(ipDistance);
        System.out.println(l2Distance);
    }
}
