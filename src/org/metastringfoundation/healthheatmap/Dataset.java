package org.metastringfoundation.healthheatmap;

import java.util.List;

public interface Dataset {
    public List<String> getIndicators();
    public List<String> getEntities();
    public List<DataPoint> getDataGroupedByIndicators();
    public List<DataPoint> getDataGroupedByEntities();
}
