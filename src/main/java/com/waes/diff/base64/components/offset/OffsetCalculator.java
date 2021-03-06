/*******************************************************************************
 * Copyright 2020 rantidev.singh1@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.waes.diff.base64.components.offset;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.waes.diff.entity.Base64Document;
import org.apache.commons.lang3.StringUtils;

/**
 * The Class OffsetCalculator.
 * 
 * @author Rantidev Singh
 * @version 1.0
 * @since 2020-10-01
 *
 */

@Component
public class OffsetCalculator implements IOffsetCalculator {

    /** The logger. */
    private static final Logger LOG = LoggerFactory.getLogger(OffsetCalculator.class);

    /**
     * Calculate offsets.
     *
     * @param base64Document the base 64 document
     * @return the list
     * @see com.waes.diff.base64.components.offset.IOffsetCalculator#calculateOffsets(
     *      com.waes.diff.entity.Base64Document)
     */
    @Override
    public List<Integer> calculateOffsets(Base64Document base64Document) {

        LOG.trace("Entering calculateOffsets(base64Document={})", base64Document.toString());

        var offsetList = new ArrayList<Integer>();

        if (StringUtils.isBlank(base64Document.getLeft()) || StringUtils.isBlank(base64Document.getRight())) {
            return offsetList;
        }

        byte[] bytesLeft = base64Document.getLeft().getBytes();
        byte[] bytesRight = base64Document.getRight().getBytes();

        byte different = 0;
        for (int index = 0; index < bytesLeft.length; index++) {
            different = (byte) (bytesLeft[index] ^ bytesRight[index]);
            if (different != 0) {
                offsetList.add(index);
            }
        }

        return offsetList;
    }

}
