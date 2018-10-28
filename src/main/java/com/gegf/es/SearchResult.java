package com.gegf.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult implements Serializable {

    private String hits;
    private long took;
    private boolean timeOut;
    private long totalHits;
    private long totalShards;
    private long successfulShards;
    private long failedShards;

    private String scrollId;

}
